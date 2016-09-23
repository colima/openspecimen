package com.krishagni.catissueplus.core.auth.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.krishagni.catissueplus.core.administrative.domain.Password;
import com.krishagni.catissueplus.core.administrative.domain.ScheduledJobRun;
import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.services.ScheduledTask;
import com.krishagni.catissueplus.core.auth.domain.LoginAuditLog;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.service.EmailService;
import com.krishagni.catissueplus.core.common.util.ConfigUtil;
import com.krishagni.catissueplus.core.common.util.Utility;

@Configurable
public class PasswordExpiringNotification implements ScheduledTask {
	private static final String PASSWORD_EXPIRING_NOTIFICATION_TMPL = "password_expiring_notification";
	
	@Autowired
	private DaoFactory daoFactory;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	@PlusTransactional
	public void doJob(ScheduledJobRun jobRun) throws Exception {
		int notificationDays = getNotificationDays();
		List<User> users = daoFactory.getUserDao().getUsers();
		for(User user : users) {
			List<Password> passwords = new ArrayList<Password>(user.getPasswords());
			Collections.sort(passwords);         // To get last updated password.
			if (passwords.size() > 0) {
				Date lastDateToExpire = getLastDateToExpire(passwords.get(0).getUpdationDate(), notificationDays);
				long daysLeftToExpire = daysBetween(lastDateToExpire);
				if (daysLeftToExpire <= 0) {
					user.setActivityStatus("Expired");
					sendExpiryReminder(user, daysLeftToExpire, new Date(), notificationDays);
				} else if (daysLeftToExpire <= 5 && daysLeftToExpire >= 0) {
					sendExpiryReminder(user, daysLeftToExpire, lastDateToExpire, notificationDays);
				}
			}
		}
	}
	
	private Date getLastDateToExpire(Date updationDate, int notificationDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(updationDate);
		cal.add(Calendar.DATE, notificationDays);
		return cal.getTime();
	}
	
	private int getNotificationDays() {
		return ConfigUtil.getInstance().getIntSetting("auth", "password_expiry_rem_notif", 0);
	}
	
	private long daysBetween(Date expiryDate) {
		Date today = Utility.chopTime(new Date());
		return TimeUnit.DAYS.convert(Utility.chopTime(expiryDate).getTime() - today.getTime(), TimeUnit.MILLISECONDS);
	}
	
	private void sendExpiryReminder(User user, long daysLeft, Date lastDateToExpire, int notificationDays) {
		Map<String, Object> emailProps = new HashMap<String, Object>();
		emailProps.put("daysLeft", daysLeft);
		emailProps.put("lastDateToExpire", lastDateToExpire);
		emailProps.put("user", user);
		String[] rcpts = {user.getEmailAddress()};
		emailService.sendEmail(PASSWORD_EXPIRING_NOTIFICATION_TMPL, rcpts, emailProps);
	}

}

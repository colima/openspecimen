
package com.krishagni.catissueplus.core.administrative.domain.factory.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.Site;
import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.domain.factory.SiteErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.UserErrorCode;
import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.biospecimen.domain.factory.CpErrorCode;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.errors.ActivityStatusErrorCode;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.UserSummary;
import com.krishagni.catissueplus.core.common.util.Status;

public class PrintRuleFactoryImpl implements PrintRuleFactory {

	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public PrintRule createPrintRule(PrintRuleDetail detail) {
		PrintRule rule = new PrintRule();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		setSite(detail, rule, ose);
		setCollectionProtocol(detail, rule, ose);
		setUser(detail, rule, ose);

		rule.setIpRange(detail.getIpRange());
		rule.setLabelType(detail.getLabelType());
		rule.setLabelDesign(detail.getLabelDesign());
		rule.setPrinterName(detail.getPrinterName());
		rule.setCmdFileFormat(detail.getCmdFileFormat());

		setCmdFileDirectory(detail, rule, ose);
		setTokens(detail, rule, ose);
		setActivityStatus(detail, rule, ose);
		ose.checkAndThrow();
		return rule;
	}

	@Override
	public SpecimenPrintRule createSpecimenPrintRule(SpecimenPrintRuleDetail detail) {
		SpecimenPrintRule rule =new SpecimenPrintRule();
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		setSite(detail, rule, ose);
		setCollectionProtocol(detail, rule, ose);
		setUser(detail, rule, ose);

		rule.setIpRange(detail.getIpRange());
		rule.setLabelType(detail.getLabelType());
		rule.setLabelDesign(detail.getLabelDesign());
		rule.setPrinterName(detail.getPrinterName());
		rule.setCmdFileFormat(detail.getCmdFileFormat());

		setCmdFileDirectory(detail, rule, ose);
		setTokens(detail, rule, ose);
		setActivityStatus(detail, rule, ose);

		setSpecimenClass(detail, rule);
		setSpecimenType(detail, rule);

		ose.checkAndThrow();
		return rule;
	}

	private void setSpecimenClass(SpecimenPrintRuleDetail detail, SpecimenPrintRule spmnPrintRule) {
		spmnPrintRule.setSpecimenClass(detail.getSpecimenClass());
	}


	private void setSpecimenType(SpecimenPrintRuleDetail detail, SpecimenPrintRule spmnPrintRule) {
		spmnPrintRule.setSpecimenType(detail.getSpecimenType());
	}

	private void setSite(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		if (StringUtils.isBlank(detail.getSiteName())) {
			return;
		}

		Site site = daoFactory.getSiteDao().getSiteByName(detail.getSiteName());
		if (site == null) {
			ose.addError(SiteErrorCode.NOT_FOUND);
			return;
		}

		rule.setSite(site);
	}

	private void setCollectionProtocol(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		if (StringUtils.isBlank(detail.getCpShortTitle())) {
			return;
		}

		CollectionProtocol cp = daoFactory.getCollectionProtocolDao().getCpByShortTitle(detail.getCpShortTitle());
		if (cp == null) {
			ose.addError(CpErrorCode.NOT_FOUND);
			return;
		}

		rule.setCollectionProtocol(cp);
	}

	private void setUser(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		UserSummary userdetail = detail.getUser();
		if(userdetail==null)
			return;

		User user = getUser(userdetail);
		if (user == null) {
			ose.addError(UserErrorCode.NOT_FOUND);
			return;
		}

		rule.setUser(user);
	}

	private User getUser(UserSummary userDetail) {
		if (userDetail == null) {
			return null;
		}

		User user = null;
		if (userDetail.getId() != null) {
			user = daoFactory.getUserDao().getById(userDetail.getId());
		} else if (StringUtils.isNotBlank(userDetail.getLoginName()) && StringUtils.isNotBlank(userDetail.getDomain())) {
			user = daoFactory.getUserDao().getUser(userDetail.getLoginName(), userDetail.getDomain());
		} else if (StringUtils.isNotBlank(userDetail.getEmailAddress())) {
			user = daoFactory.getUserDao().getUserByEmailAddress(userDetail.getEmailAddress());
		}

		return user;
	}

	private void setCmdFileDirectory(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		String cmdFileDir = detail.getCmdFileDirectory();

		if(StringUtils.isBlank(cmdFileDir)) {
			ose.addError(PrintRuleErrorCode.CMD_FILE_DIR_REQ);
		}
		rule.setCmdFileDirectory(cmdFileDir);
	}

	private void setTokens(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		if(CollectionUtils.isEmpty(detail.getTokens())) {
			ose.addError(PrintRuleErrorCode.TOKEN_REQ);
		}
		rule.setTokens(detail.getTokens());
	}

	private void setActivityStatus(PrintRuleDetail detail, PrintRule rule, OpenSpecimenException ose) {
		String activityStatus = detail.getActivityStatus();
		if (StringUtils.isBlank(activityStatus)) {
			rule.setActivityStatus(Status.ACTIVITY_STATUS_ACTIVE.getStatus());
			return;
		}

		if (!Status.isValidActivityStatus(activityStatus)) {
			ose.addError(ActivityStatusErrorCode.INVALID);
			return;
		}

		rule.setActivityStatus(activityStatus);
	}
}

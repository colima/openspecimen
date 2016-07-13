
package com.krishagni.catissueplus.core.administrative.domain.factory.impl;

import org.apache.commons.lang.StringUtils;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.Site;
import com.krishagni.catissueplus.core.administrative.domain.User;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.domain.factory.SiteErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.UserErrorCode;
import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.biospecimen.domain.factory.CpErrorCode;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.errors.ErrorType;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.UserSummary;

public class PrintRuleFactoryImpl implements PrintRuleFactory {

	private DaoFactory daoFactory;

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public PrintRule createPrintRule(PrintRuledetail detail) {
		PrintRule rule = new PrintRule(); // PrintRule, rule
		OpenSpecimenException ose = new OpenSpecimenException(ErrorType.USER_ERROR);

		setSite(detail,rule,ose);
		setCollectionProtocol(detail,rule,ose);
		setUser(detail,rule, ose);
		setIpRange(detail,rule, ose);
		setLableType(detail,rule, ose);
		setLabelDesign(detail,rule, ose);
		setPrinterName(detail,rule, ose);
		setCmdFileFmt(detail,rule, ose);
		setCmdFileDirectory(detail,rule, ose);
		setTokens(detail,rule,ose);
		ose.checkAndThrow();
		return rule;
	}

	private void setSite(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		Site site = null;
		site = daoFactory.getSiteDao().getSiteByName(detail.getSiteName());

		if (site == null) {
			ose.addError(SiteErrorCode.NOT_FOUND);
			return;
		}
		else {
			rule.setSite(site);
		}
	}

	private void setCollectionProtocol(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		CollectionProtocol cp = null;
		cp = daoFactory.getCollectionProtocolDao().getCpByShortTitle(detail.getCpShortTitle());
		if (cp == null) {
			ose.addError(CpErrorCode.NOT_FOUND);
			return;
		}
		else {
			rule.setCp(cp);
		}
	}

	private void setUser(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		UserSummary user = detail.getUser();
		User pi = getUser(user);
		if (pi == null) {
			ose.addError(UserErrorCode.NOT_FOUND);
			return;
		} else {
			rule.setUser(pi);
		}
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

	private void setIpRange(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String ipRange = detail.getIpRange();
		rule.setIpRange(ipRange);
	}

	private void setLableType(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String labelType = detail.getLabelType();
		rule.setLabelType(labelType);
	}

	private void setLabelDesign(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String labelDesign = detail.getLabelDesign();
		rule.setLabelDesign(labelDesign);
	}

	private void setPrinterName(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String printerName = detail.getPrinterName();
		rule.setPrinterName(printerName);
	}

	private void setCmdFileFmt(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String cmdFileFmt = detail.getCmdFileFormat();
		rule.setCmdFileFormat(cmdFileFmt);
	}

	private void setCmdFileDirectory(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		String cmdFileDir = detail.getCmdFileDirectory();
		rule.setCmdFileDirectory(cmdFileDir);
	}

	private void setTokens(PrintRuledetail detail, PrintRule rule, OpenSpecimenException ose) {
		rule.setTokens(detail.getTokens());
	}

}

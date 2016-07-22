
package com.krishagni.catissueplus.core.administrative.events;

import java.util.HashSet;

import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.common.events.UserSummary;


public class SpecimenPrintRuleDetail extends PrintRuleDetail {

	public String specimenClass;

	public String specimenType;

	public String getSpecimenClass() {
		return specimenClass;
	}

	public void setSpecimenClass(String specimenClass) {
		this.specimenClass = specimenClass;
	}

	public String getSpecimenType() {
		return specimenType;
	}

	public void setSpecimenType(String specimenType) {
		this.specimenType = specimenType;
	}

	public static SpecimenPrintRuleDetail from(SpecimenPrintRule rule) {
		SpecimenPrintRuleDetail detail = new SpecimenPrintRuleDetail();
		detail.setId(rule.getId());
		detail.setId(rule.getId());

		if(rule.getSite() != null) {
			detail.setSiteName(rule.getSite().getName());
		}

		if(rule.getCollectionProtocol() != null) {
			detail.setCpShortTitle(rule.getCollectionProtocol().getShortTitle());
		}

		if(rule.getUser()!= null) {
			detail.setUser(UserSummary.from(rule.getUser()));
		}

		detail.setIpRange(rule.getIpRange());
		detail.setLabelDesign(rule.getLabelDesign());
		detail.setLabelType(rule.getLabelType());
		detail.setPrinterName(rule.getPrinterName());
		detail.setCmdFileFormat(rule.getCmdFileFormat());
		detail.setCmdFileDirectory(rule.getCmdFileDirectory());
		detail.setTokens(new HashSet<>(rule.getTokens()));
		detail.setActivityStatus(rule.getActivityStatus());
		detail.setSpecimenClass(rule.getSpecimenClass());
		detail.setSpecimenType(rule.getSpecimenType());

		return detail;
	}

}

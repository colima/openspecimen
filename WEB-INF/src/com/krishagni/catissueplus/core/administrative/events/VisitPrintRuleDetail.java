
package com.krishagni.catissueplus.core.administrative.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.VisitPrintRule;
import com.krishagni.catissueplus.core.common.events.UserSummary;


public class VisitPrintRuleDetail extends PrintRuleDetail {

	public static VisitPrintRuleDetail from(PrintRule rule) {
		VisitPrintRuleDetail detail = new VisitPrintRuleDetail();
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

		return detail;
	}

	public static List<VisitPrintRuleDetail> from(Collection<VisitPrintRule> rules) {
		List<VisitPrintRuleDetail> result = new ArrayList<VisitPrintRuleDetail>();
		for (VisitPrintRule rule: rules) {
			result.add(from(rule));
		}

		return result;
	}
}

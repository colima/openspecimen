
package com.krishagni.catissueplus.core.administrative.services;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.VisitPrintRuleDetail;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public interface PrintRuleService {
	ResponseEvent<VisitPrintRuleDetail> createVisitPrintRule(RequestEvent<VisitPrintRuleDetail> req);

	ResponseEvent<VisitPrintRuleDetail> updateVisitPrintRule(RequestEvent<VisitPrintRuleDetail> req);

	ResponseEvent<VisitPrintRuleDetail> deleteVisitPrintRule(RequestEvent<Long> req);

	ResponseEvent<SpecimenPrintRuleDetail> createSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req);

	ResponseEvent<SpecimenPrintRuleDetail> updateSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req);

	ResponseEvent<SpecimenPrintRuleDetail> deleteSpecimenPrintRule(RequestEvent<Long> req);

	ResponseEvent<VisitPrintRuleDetail> getVisitRule(RequestEvent<Long> req);

	ResponseEvent<List<VisitPrintRuleDetail>> getVisitPrintRules();

	ResponseEvent<List<SpecimenPrintRuleDetail>> getSpecimenPrintRules();

	ResponseEvent<SpecimenPrintRuleDetail> getSpecimenRule(RequestEvent<Long> req);
}

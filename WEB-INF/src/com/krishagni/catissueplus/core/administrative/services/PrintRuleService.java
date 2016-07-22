
package com.krishagni.catissueplus.core.administrative.services;

import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public interface PrintRuleService {
	ResponseEvent<PrintRuleDetail> createRule(RequestEvent<PrintRuleDetail> req);

	ResponseEvent<PrintRuleDetail> updatePrintRule(RequestEvent<PrintRuleDetail> req);

	ResponseEvent<PrintRuleDetail> deletePrintRule(RequestEvent<Long> req);

	@PlusTransactional
	ResponseEvent<SpecimenPrintRuleDetail> createSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req);

	@PlusTransactional
	ResponseEvent<SpecimenPrintRuleDetail> updateSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req);

	@PlusTransactional
	ResponseEvent<SpecimenPrintRuleDetail> deleteSpecimenPrintRule(RequestEvent<Long> req);
}

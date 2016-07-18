
package com.krishagni.catissueplus.core.administrative.services;

import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public interface PrintRuleService {
	ResponseEvent<PrintRuleDetail> createRule(RequestEvent<PrintRuleDetail> req);

	ResponseEvent<PrintRuleDetail> updatePrintRule(RequestEvent<PrintRuleDetail> request);

	ResponseEvent<PrintRuleDetail> deletePrintRule(RequestEvent<Long> req);
}

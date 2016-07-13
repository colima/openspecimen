
package com.krishagni.catissueplus.core.administrative.services;

import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public interface PrintRuleService {
	ResponseEvent<PrintRuledetail> createRule(RequestEvent<PrintRuledetail> req);
}

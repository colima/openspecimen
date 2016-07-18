
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;

public interface PrintRuleFactory {
	public PrintRule createPrintRule(PrintRuleDetail detail);
}

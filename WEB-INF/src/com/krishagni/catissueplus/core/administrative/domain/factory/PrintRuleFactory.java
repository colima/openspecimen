
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;

public interface PrintRuleFactory {
	public PrintRule createPrintRule(PrintRuledetail detail);
}

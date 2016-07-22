
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;

public interface PrintRuleFactory {
	public PrintRule createPrintRule(PrintRuleDetail detail);

	public SpecimenPrintRule createSpecimenPrintRule(SpecimenPrintRuleDetail detail);
}

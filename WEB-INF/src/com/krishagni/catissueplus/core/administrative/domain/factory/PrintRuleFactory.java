
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.domain.VisitPrintRule;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.VisitPrintRuleDetail;

public interface PrintRuleFactory {
	public VisitPrintRule createPrintRule(VisitPrintRuleDetail detail);

	public SpecimenPrintRule createSpecimenPrintRule(SpecimenPrintRuleDetail detail);
}

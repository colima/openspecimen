
package com.krishagni.catissueplus.core.administrative.repository.impl;

import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.repository.PrintRuleSpecimenDao;
import com.krishagni.catissueplus.core.common.repository.AbstractDao;

public class PrintRuleSpecimenDaoImpl extends AbstractDao<SpecimenPrintRule> implements PrintRuleSpecimenDao {

	@Override
	public Class<?> getType() {
		return SpecimenPrintRule.class;
	}
}

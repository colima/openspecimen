
package com.krishagni.catissueplus.core.administrative.repository.impl;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.repository.PrintRuleSpecimenDao;
import com.krishagni.catissueplus.core.common.repository.AbstractDao;

public class PrintRuleSpecimenDaoImpl extends AbstractDao<SpecimenPrintRule> implements PrintRuleSpecimenDao {

	@Override
	public Class<?> getType() {
		return SpecimenPrintRule.class;
	}

	@Override
	public List<SpecimenPrintRule> getRules() {
		return getSessionFactory().getCurrentSession()
			.getNamedQuery(GET_SPECIMEN_PRINT_RULES)
			.list();
	}


	private static final String FQN = SpecimenPrintRule.class.getName();

	private static final String GET_SPECIMEN_PRINT_RULES = FQN + ".getSpecimenPrintRules";
}

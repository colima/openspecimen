
package com.krishagni.catissueplus.core.administrative.repository.impl;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.repository.PrintRuleDao;
import com.krishagni.catissueplus.core.common.repository.AbstractDao;

public class PrintRuleDaoImpl extends AbstractDao<PrintRule> implements PrintRuleDao {

	public Class<?> getType() {
		return PrintRule.class;
	}

	public List<PrintRule> getRules() {
		return getSessionFactory().getCurrentSession()
				.getNamedQuery(GET_VISIT_PRINT_RULES)
				.list();
	}

	private static final String FQN = PrintRule.class.getName();

	private static final String GET_VISIT_PRINT_RULES = FQN + ".getVisitPrintRules";
}

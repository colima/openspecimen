
package com.krishagni.catissueplus.core.administrative.repository.impl;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.VisitPrintRule;
import com.krishagni.catissueplus.core.administrative.repository.VisitPrintRuleDao;
import com.krishagni.catissueplus.core.common.repository.AbstractDao;

public class VisitPrintRuleDaoImpl extends AbstractDao<VisitPrintRule> implements VisitPrintRuleDao {

	public Class<?> getType() {
		return PrintRule.class;
	}

	public List<VisitPrintRule> getRules() {
		return getSessionFactory().getCurrentSession()
			.getNamedQuery(GET_VISIT_PRINT_RULES)
			.list();
	}

	private static final String FQN = VisitPrintRule.class.getName();

	private static final String GET_VISIT_PRINT_RULES = FQN + ".getVisitPrintRules";
}


package com.krishagni.catissueplus.core.administrative.repository.impl;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.repository.PrintRuleDao;
import com.krishagni.catissueplus.core.common.repository.AbstractDao;

public class PrintRuleDaoImpl extends AbstractDao<PrintRule> implements PrintRuleDao {

	@Override
	public Class<?> getType() {
		return PrintRule.class;
	}
}

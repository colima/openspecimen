
package com.krishagni.catissueplus.core.administrative.repository;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.common.repository.Dao;

public interface PrintRuleDao extends Dao<PrintRule> {

	public List<PrintRule> getRules();
}

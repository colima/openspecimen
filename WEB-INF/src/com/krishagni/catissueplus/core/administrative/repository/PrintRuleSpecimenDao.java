
package com.krishagni.catissueplus.core.administrative.repository;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.common.repository.Dao;

public interface PrintRuleSpecimenDao extends Dao<SpecimenPrintRule> {

	List<SpecimenPrintRule> getRules();
}

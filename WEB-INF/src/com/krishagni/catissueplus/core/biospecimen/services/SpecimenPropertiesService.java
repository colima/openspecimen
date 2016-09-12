package com.krishagni.catissueplus.core.biospecimen.services;

import java.util.List;

import com.krishagni.catissueplus.core.biospecimen.events.SpecimenIconDetail;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenUnitDetail;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public interface SpecimenPropertiesService {
	public ResponseEvent<List<SpecimenUnitDetail>> getUnits();
	
	public ResponseEvent<List<SpecimenIconDetail>> getIcons();
}

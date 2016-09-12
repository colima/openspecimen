
package com.krishagni.catissueplus.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.krishagni.catissueplus.core.biospecimen.events.SpecimenIconDetail;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenUnitDetail;
import com.krishagni.catissueplus.core.biospecimen.services.SpecimenPropertiesService;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

@Controller
@RequestMapping("/specimen-props")
public class SpecimenPropertiesController {

	@Autowired
	private SpecimenPropertiesService specPropsSvc;

	@RequestMapping(method = RequestMethod.GET, value="/units")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SpecimenUnitDetail> getUnits() {
		ResponseEvent<List<SpecimenUnitDetail>> resp = specPropsSvc.getUnits();
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/icons")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SpecimenIconDetail> getIcons() {
		ResponseEvent<List<SpecimenIconDetail>> resp = specPropsSvc.getIcons();
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}
}

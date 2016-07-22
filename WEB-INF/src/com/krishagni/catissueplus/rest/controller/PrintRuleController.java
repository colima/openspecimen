package com.krishagni.catissueplus.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.services.PrintRuleService;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

@Controller
@RequestMapping("/print-rules")
public class PrintRuleController {

	@Autowired
	private PrintRuleService printRuleSvc;

	@RequestMapping(method = RequestMethod.POST, value = "/visit")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PrintRuleDetail createVisitPrintRule (@RequestBody PrintRuleDetail printRule) {
		RequestEvent<PrintRuleDetail> req = new RequestEvent<PrintRuleDetail>(printRule);
		ResponseEvent<PrintRuleDetail> resp = printRuleSvc.createRule(req);
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/visit/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public PrintRuleDetail updateVisitPrintRule(@PathVariable Long id, @RequestBody PrintRuleDetail detail) {
		detail.setId(id);
		ResponseEvent<PrintRuleDetail> resp = printRuleSvc.updatePrintRule(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}


	@RequestMapping(method = RequestMethod.DELETE, value = "/visit/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public PrintRuleDetail deleteVisitPrintRule(@PathVariable Long id) {
		RequestEvent<Long> req = new RequestEvent<>(id);
		ResponseEvent<PrintRuleDetail> resp  = printRuleSvc.deletePrintRule(req);
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/specimen")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SpecimenPrintRuleDetail specimenPrintRule (@RequestBody SpecimenPrintRuleDetail detail) {
		RequestEvent<SpecimenPrintRuleDetail> req = new RequestEvent<SpecimenPrintRuleDetail>(detail);
		ResponseEvent<SpecimenPrintRuleDetail> resp = printRuleSvc.createSpecimenPrintRule(req);
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/specimen/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public SpecimenPrintRuleDetail updateSpecimenPrintRule (@PathVariable Long id, @RequestBody SpecimenPrintRuleDetail detail) {
		detail.setId(id);
		RequestEvent<SpecimenPrintRuleDetail> req = new RequestEvent<SpecimenPrintRuleDetail>(detail);
		ResponseEvent<SpecimenPrintRuleDetail> resp = printRuleSvc.updateSpecimenPrintRule(req);
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/specimen/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public SpecimenPrintRuleDetail deleteSpecimenPrintRule (@PathVariable Long id) {
		RequestEvent<Long> req = new RequestEvent<>(id);
		ResponseEvent<SpecimenPrintRuleDetail> resp  = printRuleSvc.deleteSpecimenPrintRule(req);
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();
	}

	private <T> RequestEvent<T> getRequest(T payload) {
		return new RequestEvent<T>(payload);
	}
}
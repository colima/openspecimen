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

import com.krishagni.catissueplus.core.administrative.events.DistributionProtocolDetail;
import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;
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
	public PrintRuledetail specimenPrintRule (@RequestBody PrintRuledetail printRule) {

		RequestEvent<PrintRuledetail> req = new RequestEvent<PrintRuledetail>(printRule);
		ResponseEvent<PrintRuledetail> resp = printRuleSvc.createRule(req);
		resp.throwErrorIfUnsuccessful();

		return resp.getPayload();

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public PrintRuledetail updatePrintRule(@PathVariable Long id, @RequestBody PrintRuledetail detail) {
		detail.setId(id);
		ResponseEvent<PrintRuledetail> resp = printRuleSvc.updatePrintRule(getRequest(detail));
		resp.throwErrorIfUnsuccessful();
		return resp.getPayload();

	}

	private <T> RequestEvent<T> getRequest(T payload) {
		return new RequestEvent<T>(payload);
	}
}
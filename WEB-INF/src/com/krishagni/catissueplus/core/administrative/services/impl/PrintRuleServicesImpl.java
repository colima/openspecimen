
package com.krishagni.catissueplus.core.administrative.services.impl;


import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;
import com.krishagni.catissueplus.core.administrative.services.PrintRuleService;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.events.RequestEvent;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

class PrintRuleServiceImpl implements PrintRuleService {

	private DaoFactory daoFactory;

	private PrintRuleFactory printRuleFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setPrintRuleFactory(PrintRuleFactory printRulesFactory) {
		this.printRuleFactory = printRulesFactory;
	}

	@Override
	@PlusTransactional
	public ResponseEvent<PrintRuledetail> createRule(RequestEvent<PrintRuledetail> req) {

		PrintRuledetail detail = req.getPayload();
		PrintRule rule = printRuleFactory.createPrintRule(detail);
		daoFactory.getPrintRuleDao().saveOrUpdate(rule);

		return ResponseEvent.response(PrintRuledetail.from(rule));
	}

}
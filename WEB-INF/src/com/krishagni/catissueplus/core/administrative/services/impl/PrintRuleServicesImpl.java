
package com.krishagni.catissueplus.core.administrative.services.impl;


import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.events.PrintRuledetail;
import com.krishagni.catissueplus.core.administrative.services.PrintRuleService;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.access.AccessCtrlMgr;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
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

	@Override
	@PlusTransactional
	public ResponseEvent<PrintRuledetail> updatePrintRule(RequestEvent<PrintRuledetail> req) {
		try {
			PrintRuledetail input = req.getPayload();
			PrintRule existingRule = daoFactory.getPrintRuleDao().getById(req.getPayload().getId());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.RULE_NOT_FOUND);
			}

			PrintRule newRule = printRuleFactory.createPrintRule(input);

			existingRule.update(newRule);
			daoFactory.getPrintRuleDao().saveOrUpdate(existingRule);
			existingRule.addOrUpdateExtension();
			return ResponseEvent.response(PrintRuledetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

}
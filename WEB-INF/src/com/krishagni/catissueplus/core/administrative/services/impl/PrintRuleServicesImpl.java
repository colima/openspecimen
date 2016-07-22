
package com.krishagni.catissueplus.core.administrative.services.impl;


import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.events.PrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.services.PrintRuleService;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.common.PlusTransactional;
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
	public ResponseEvent<PrintRuleDetail> createRule(RequestEvent<PrintRuleDetail> req) {
		PrintRuleDetail detail = req.getPayload();
		PrintRule rule = printRuleFactory.createPrintRule(detail);
		daoFactory.getPrintRuleDao().saveOrUpdate(rule);

		return ResponseEvent.response(PrintRuleDetail.from(rule));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<PrintRuleDetail> updatePrintRule(RequestEvent<PrintRuleDetail> req) {
		try {
			PrintRuleDetail detail = req.getPayload();
			PrintRule existingRule = daoFactory.getPrintRuleDao().getById(detail.getId());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,detail.getId());
			}

			PrintRule rule = printRuleFactory.createPrintRule(detail);

			existingRule.update(rule);
			daoFactory.getPrintRuleDao().saveOrUpdate(existingRule);
			return ResponseEvent.response(PrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<PrintRuleDetail> deletePrintRule(RequestEvent<Long> req) {
		try {
			PrintRule existingRule = daoFactory.getPrintRuleDao().getById(req.getPayload());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,req.getPayload());
			}

			existingRule.delete();
			return ResponseEvent.response(PrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}
}
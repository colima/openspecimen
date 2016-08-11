
package com.krishagni.catissueplus.core.administrative.services.impl;

import java.util.List;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.administrative.domain.SpecimenPrintRule;
import com.krishagni.catissueplus.core.administrative.domain.VisitPrintRule;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleErrorCode;
import com.krishagni.catissueplus.core.administrative.domain.factory.PrintRuleFactory;
import com.krishagni.catissueplus.core.administrative.events.SpecimenPrintRuleDetail;
import com.krishagni.catissueplus.core.administrative.events.VisitPrintRuleDetail;
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
	public ResponseEvent<List<VisitPrintRuleDetail>> getVisitPrintRules() {
		List<VisitPrintRule> rules = daoFactory.getVisitPrintRuleDao().getRules();

		return ResponseEvent.response(VisitPrintRuleDetail.from(rules));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<VisitPrintRuleDetail> getVisitRule(RequestEvent<Long> req) {
		VisitPrintRule rule = daoFactory.getVisitPrintRuleDao().getById(req.getPayload());

		return ResponseEvent.response(VisitPrintRuleDetail.from(rule));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<VisitPrintRuleDetail> createVisitPrintRule(RequestEvent<VisitPrintRuleDetail> req) {
		VisitPrintRuleDetail detail = req.getPayload();
		VisitPrintRule rule = printRuleFactory.createPrintRule(detail);
		daoFactory.getVisitPrintRuleDao().saveOrUpdate(rule);

		return ResponseEvent.response(VisitPrintRuleDetail.from(rule));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<VisitPrintRuleDetail> updateVisitPrintRule(RequestEvent<VisitPrintRuleDetail> req) {
		try {
			VisitPrintRuleDetail detail = req.getPayload();
			VisitPrintRule existingRule = daoFactory.getVisitPrintRuleDao().getById(detail.getId());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,detail.getId());
			}

			VisitPrintRule rule = printRuleFactory.createPrintRule(detail);

			existingRule.update(rule);
			daoFactory.getVisitPrintRuleDao().saveOrUpdate(existingRule);
			return ResponseEvent.response(VisitPrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<VisitPrintRuleDetail> deleteVisitPrintRule(RequestEvent<Long> req) {
		try {
			PrintRule existingRule = daoFactory.getVisitPrintRuleDao().getById(req.getPayload());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,req.getPayload());
			}

			existingRule.delete();
			return ResponseEvent.response(VisitPrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<SpecimenPrintRuleDetail>> getSpecimenPrintRules() {
		List<SpecimenPrintRule> rules = daoFactory.getPrintSpecimenRuleDao().getRules();

		return ResponseEvent.response(SpecimenPrintRuleDetail.from(rules));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<SpecimenPrintRuleDetail> getSpecimenRule(RequestEvent<Long> req) {
		SpecimenPrintRule rule = daoFactory.getPrintSpecimenRuleDao().getById(req.getPayload());

		return ResponseEvent.response(SpecimenPrintRuleDetail.from(rule));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<SpecimenPrintRuleDetail> createSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req) {
		SpecimenPrintRuleDetail detail = req.getPayload();
		SpecimenPrintRule rule = printRuleFactory.createSpecimenPrintRule(detail);
		daoFactory.getPrintSpecimenRuleDao().saveOrUpdate(rule);

		return ResponseEvent.response(SpecimenPrintRuleDetail.from(rule));
	}

	@Override
	@PlusTransactional
	public ResponseEvent<SpecimenPrintRuleDetail> updateSpecimenPrintRule(RequestEvent<SpecimenPrintRuleDetail> req) {
		try {
			SpecimenPrintRuleDetail detail = req.getPayload();
			SpecimenPrintRule existingRule = daoFactory.getPrintSpecimenRuleDao().getById(req.getPayload().getId());

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,req.getPayload().getId());
			}

			SpecimenPrintRule newRule = printRuleFactory.createSpecimenPrintRule(detail);

			existingRule.update(newRule);
			daoFactory.getPrintSpecimenRuleDao().saveOrUpdate(existingRule);
			return ResponseEvent.response(SpecimenPrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception ex) {
			return ResponseEvent.serverError(ex);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<SpecimenPrintRuleDetail> deleteSpecimenPrintRule(RequestEvent<Long> req) {
		try {
			Long id = req.getPayload();
			SpecimenPrintRule existingRule = daoFactory.getPrintSpecimenRuleDao().getById(id);

			if (existingRule == null) {
				return ResponseEvent.userError(PrintRuleErrorCode.NOT_FOUND,id);
			}

			existingRule.delete();
			return ResponseEvent.response(SpecimenPrintRuleDetail.from(existingRule));
		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}
}
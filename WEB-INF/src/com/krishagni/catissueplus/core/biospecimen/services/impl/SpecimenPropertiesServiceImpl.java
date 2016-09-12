package com.krishagni.catissueplus.core.biospecimen.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.krishagni.catissueplus.core.administrative.domain.PermissibleValue;
import com.krishagni.catissueplus.core.administrative.events.ListPvCriteria;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenIconDetail;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenUnitDetail;
import com.krishagni.catissueplus.core.biospecimen.repository.DaoFactory;
import com.krishagni.catissueplus.core.biospecimen.services.SpecimenPropertiesService;
import com.krishagni.catissueplus.core.common.PlusTransactional;
import com.krishagni.catissueplus.core.common.PvAttributes;
import com.krishagni.catissueplus.core.common.errors.OpenSpecimenException;
import com.krishagni.catissueplus.core.common.events.ResponseEvent;

public class SpecimenPropertiesServiceImpl implements SpecimenPropertiesService {
	
	private DaoFactory daoFactory;
	
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<SpecimenUnitDetail>> getUnits() {
		try {
			List<PermissibleValue> specimenClasses = getSpecimenClasses();

			List<SpecimenUnitDetail> units = new ArrayList<>();
			for (PermissibleValue specimenClass : specimenClasses) {
				for (PermissibleValue type : specimenClass.getChildren()) {
					units.add(getSpecimenUnitDetail(type));
				}
			}

			return ResponseEvent.response(units);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	@Override
	@PlusTransactional
	public ResponseEvent<List<SpecimenIconDetail>> getIcons() {
		try {
			List<PermissibleValue> specimenClasses = getSpecimenClasses();

			List<SpecimenIconDetail> icons = new ArrayList<>();
			for (PermissibleValue specimenClass : specimenClasses) {
				for (PermissibleValue type : specimenClass.getChildren()) {
					icons.add(getSpecimenIconDetail(type));
				}
			}

			return ResponseEvent.response(icons);

		} catch (OpenSpecimenException ose) {
			return ResponseEvent.error(ose);
		} catch (Exception e) {
			return ResponseEvent.serverError(e);
		}
	}

	private List<PermissibleValue> getSpecimenClasses() {
		ListPvCriteria crit = new ListPvCriteria().attribute(PvAttributes.SPECIMEN_CLASS);
		return daoFactory.getPermissibleValueDao().getPvs(crit);
	}

	private SpecimenUnitDetail getSpecimenUnitDetail(PermissibleValue pv) {
		SpecimenUnitDetail detail = new SpecimenUnitDetail();
		detail.setSpecimenClass(pv.getParent().getValue());
		detail.setType(pv.getValue());
		detail.setQtyUnit(getProperty(pv, "quantity_unit"));
		detail.setQtyHtmlDisplayCode(getProperty(pv, "quantity_display_unit"));
		detail.setConcUnit(getProperty(pv, "concentration_unit"));
		detail.setConcHtmlDisplayCode(getProperty(pv, "concentration_display_unit"));
		detail.setActivityStatus("Active");
		return detail;
	}

	private SpecimenIconDetail getSpecimenIconDetail(PermissibleValue pv) {
		SpecimenIconDetail detail = new SpecimenIconDetail();
		detail.setSpecimenClass(pv.getParent().getValue());
		detail.setType(pv.getValue());
		detail.setIconClass(getProperty(pv, "icon"));
		detail.setAbbreviation(getProperty(pv, "abbreviation"));
		return detail;
	}

	private String getProperty(PermissibleValue pv, String prop) {
		String value = pv.getProps().get(prop);
		if (StringUtils.isBlank(value)) {
			value = pv.getParent().getProps().get(prop);
		}

		return value;
	}
}

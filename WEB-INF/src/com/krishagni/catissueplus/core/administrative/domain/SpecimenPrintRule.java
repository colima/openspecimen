
package com.krishagni.catissueplus.core.administrative.domain;

import com.krishagni.catissueplus.core.common.CollectionUpdater;

public class SpecimenPrintRule extends PrintRule {

	public String specimenClass;

	public String specimenType;

	public String getSpecimenClass() {
		return specimenClass;
	}

	public void setSpecimenClass(String specimenClass) {
		this.specimenClass = specimenClass;
	}

	public String getSpecimenType() {
		return specimenType;
	}

	public void setSpecimenType(String specimenType) {
		this.specimenType = specimenType;
	}

	public void update(SpecimenPrintRule newRule) {
		setSpecimenType(newRule.getSpecimenType());
		setSpecimenClass(newRule.getSpecimenClass());
		setSite(newRule.getSite());
		setCollectionProtocol(newRule.getCollectionProtocol());
		setUser(newRule.getUser());
		setIpRange(newRule.getIpRange());
		setLabelType(newRule.getLabelType());
		setLabelDesign(newRule.getLabelDesign());
		setPrinterName(newRule.getPrinterName());
		setCmdFileFormat(newRule.getCmdFileFormat());
		setCmdFileDirectory(newRule.getCmdFileDirectory());
		CollectionUpdater.update(getTokens(), newRule.getTokens());
	}
}

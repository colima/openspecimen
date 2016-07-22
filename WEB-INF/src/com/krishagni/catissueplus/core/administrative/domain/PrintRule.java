
package com.krishagni.catissueplus.core.administrative.domain;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.common.CollectionUpdater;
import com.krishagni.catissueplus.core.common.util.Status;

public class PrintRule extends BaseEntity {

	public Site site;

	public CollectionProtocol collectionProtocol;

	public User user;

	public String ipRange;

	public String labelType;

	public String labelDesign;

	public String printerName;

	public String cmdFileFormat;

	public String cmdFileDirectory;

	public Set<String> tokens = new HashSet<>();

	private String activityStatus;

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public CollectionProtocol getCollectionProtocol() {
		return collectionProtocol;
	}

	public void setCollectionProtocol(CollectionProtocol collectionProtocol) {
		this.collectionProtocol = collectionProtocol;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabelDesign() {
		return labelDesign;
	}

	public void setLabelDesign(String labelDesign) {
		this.labelDesign = labelDesign;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printedName) {
		this.printerName = printedName;
	}

	public String getCmdFileFormat() {
		return cmdFileFormat;
	}

	public void setCmdFileFormat(String cmdFileFormat) {
		this.cmdFileFormat = cmdFileFormat;
	}

	public String getCmdFileDirectory() {
		return cmdFileDirectory;
	}

	public void setCmdFileDirectory(String cmdFileDirectory) {
		this.cmdFileDirectory = cmdFileDirectory;
	}

	public Set<String> getTokens() {
		return tokens;
	}

	public void setTokens(Set<String> tokens) {
		this.tokens = tokens;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void update(PrintRule rule) {
		setSite(rule.getSite());
		setCollectionProtocol(rule.getCollectionProtocol());
		setUser(rule.getUser());
		setIpRange(rule.getIpRange());
		setLabelType(rule.getLabelType());
		setLabelDesign(rule.getLabelDesign());
		setPrinterName(rule.getPrinterName());
		setCmdFileFormat(rule.getCmdFileFormat());
		setCmdFileDirectory(rule.getCmdFileDirectory());
		CollectionUpdater.update(getTokens(), rule.getTokens());
	}

	public void delete() {
		setActivityStatus(Status.ACTIVITY_STATUS_DISABLED.getStatus());
	}
}

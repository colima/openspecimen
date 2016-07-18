
package com.krishagni.catissueplus.core.administrative.domain;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseExtensionEntity;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.common.CollectionUpdater;
import com.krishagni.catissueplus.core.common.util.Status;

public class PrintRule extends BaseExtensionEntity {

	public Site site;

	public CollectionProtocol cp;

	public User user;

	public String ipRange;

	public String labelType;

	public String labelDesign;

	public String printerName;

	public String cmdFileFormat;

	public String cmdFileDirectory;

	private String activityStatus;

	public Set<String> tokens = new HashSet<>();

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public CollectionProtocol getCp() {
		return cp;
	}

	public void setCp(CollectionProtocol cp) {
		this.cp = cp;
	}

	public String getIpRange() {
		return ipRange;
	}

	public void setIpRange(String ipRange) {
		this.ipRange = ipRange;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getEntityType() {return null;	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Set<String> getTokens() {
		return tokens;
	}

	public void setTokens(Set<String> tokens) {
		this.tokens = tokens;
	}

	public void update(PrintRule newRule) {
		setSite(newRule.getSite());
		setCp(newRule.getCp());
		setUser(newRule.getUser());
		setIpRange(newRule.getIpRange());
		setLabelType(newRule.getLabelType());
		setLabelDesign(newRule.getLabelDesign());
		setPrinterName(newRule.getPrinterName());
		setCmdFileFormat(newRule.getCmdFileFormat());
		setCmdFileDirectory(newRule.getCmdFileDirectory());
		CollectionUpdater.update(getTokens(), newRule.getTokens());
	}

	public void delete() {
		setActivityStatus(Status.ACTIVITY_STATUS_DISABLED.getStatus());
	}
}

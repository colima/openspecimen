
package com.krishagni.catissueplus.core.administrative.domain;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseExtensionEntity;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;
import com.krishagni.catissueplus.core.common.CollectionUpdater;

public class PrintRule extends BaseExtensionEntity {

	public User user;

	public Site site;

	public CollectionProtocol cp;

	public String labelType;

	public String labelDesign;

	public String ipRange;

	public String printerName;

	public String cmdFileFormat;

	public Set<String> tokens = new HashSet<String>();

	public Set<String> getTokens() {
		return tokens;
	}

	public void setTokens(Set<String> tokens) {
		this.tokens = tokens;
	}

	public String cmdFileDirectory;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public void update(PrintRule newRule) {
		setUser(newRule.getUser());
		setSite(newRule.getSite());
		setCp(newRule.getCp());
		setLabelType(newRule.getLabelType());
		setLabelDesign(newRule.getLabelDesign());
		setIpRange(newRule.getIpRange());
		setPrinterName(newRule.getPrinterName());
		setCmdFileFormat(newRule.getCmdFileFormat());
		setCmdFileDirectory(newRule.getCmdFileDirectory());
		CollectionUpdater.update(getTokens(), newRule.getTokens());
	}

}

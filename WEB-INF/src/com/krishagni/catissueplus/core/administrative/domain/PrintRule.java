
package com.krishagni.catissueplus.core.administrative.domain;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.biospecimen.domain.BaseEntity;
import com.krishagni.catissueplus.core.biospecimen.domain.CollectionProtocol;

public class PrintRule extends BaseEntity {

	public User user;

	public Site site;

	public CollectionProtocol cp;

	public Long userId;

	public Long siteId;

	public Long cpId;

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

	public long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
	}

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

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
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

	public Long getCpId() {
		return cpId;
	}

	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

}


package com.krishagni.catissueplus.core.administrative.events;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.common.events.UserSummary;

public class PrintRuleDetail {

	private Long id;

	public String siteName;

	public String cpShortTitle;

	private UserSummary user;

	public String ipRange;

	public String labelType;

	public String labelDesign;

	public String printerName;

	public String cmdFileFormat;

	public String cmdFileDirectory;

	private String activityStatus;

	public Set<String> tokens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getCpShortTitle() {
		return cpShortTitle;
	}

	public void setCpShortTitle(String cpShortTitle) {
		this.cpShortTitle = cpShortTitle;
	}

	public UserSummary getUser() {
		return user;
	}

	public void setUser(UserSummary user) {
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

	public String getLabelDesign() { return labelDesign;	}

	public void setLabelDesign(String labelDesign) {
		this.labelDesign = labelDesign;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getCmdFileFormat() {
		return cmdFileFormat;
	}

	public void setCmdFileFormat(String cmdFileFormat) {
		this.cmdFileFormat = cmdFileFormat;
	}

	public String getCmdFileDirectory() { return cmdFileDirectory;	}

	public void setCmdFileDirectory(String cmdFileDirectory) {
		this.cmdFileDirectory = cmdFileDirectory;
	}

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

	public static PrintRuleDetail from(PrintRule rule) {
		PrintRuleDetail detail = new PrintRuleDetail();
		detail.setId(rule.getId());

		if(rule.getSite() != null) {
			detail.setSiteName(rule.getSite().getName());
		}

		if(rule.getCollectionProtocol() != null) {
			detail.setCpShortTitle(rule.getCollectionProtocol().getShortTitle());
		}

		if(rule.getUser()!= null) {
			detail.setUser(UserSummary.from(rule.getUser()));
		}

		detail.setIpRange(rule.getIpRange());
		detail.setLabelDesign(rule.getLabelDesign());
		detail.setLabelType(rule.getLabelType());
		detail.setPrinterName(rule.getPrinterName());
		detail.setCmdFileFormat(rule.getCmdFileFormat());
		detail.setCmdFileDirectory(rule.getCmdFileDirectory());
		detail.setTokens(new HashSet<>(rule.getTokens()));
		detail.setActivityStatus(rule.getActivityStatus());

		return detail;
	}
}

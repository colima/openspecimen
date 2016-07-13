
package com.krishagni.catissueplus.core.administrative.events;

import java.util.HashSet;
import java.util.Set;

import com.krishagni.catissueplus.core.administrative.domain.PrintRule;
import com.krishagni.catissueplus.core.common.ListenAttributeChanges;
import com.krishagni.catissueplus.core.common.events.UserSummary;


@ListenAttributeChanges
public class PrintRuledetail {

	private UserSummary user;

	public String siteName;

	public String cpShortTitle;

	public String labelType;

	public String labelDesign;

	public String ipRange;

	public String printerName;

	public String cmdFileFormat;

	public String cmdFileDirectory;

	public Set<String> tokens = new HashSet<String>();

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

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getCmdFileFormat() {
		return cmdFileFormat;
	}

	public void setCmdFileFormat(String cmdFileFormat) {
		this.cmdFileFormat = cmdFileFormat;
	}

	public String getCmdFileDirectory() { return cmdFileDirectory;
	}

	public void setCmdFileDirectory(String cmdFileDirectory) {
		this.cmdFileDirectory = cmdFileDirectory;
	}

	public UserSummary getUser() {
		return user;
	}

	public void setUser(UserSummary user) {
		this.user = user;
	}

	public Set<String> getTokens() {
		return tokens;
	}

	public void setTokens(Set<String> tokens) {
		this.tokens = tokens;
	}

	public static PrintRuledetail from(PrintRule rule) {
		PrintRuledetail detail = new PrintRuledetail();
		detail.setIpRange(rule.getIpRange());
		detail.setLabelDesign(rule.getLabelDesign());
		detail.setLabelType(rule.getLabelType());
		detail.setPrinterName(rule.getPrinterName());
		detail.setCmdFileFormat(rule.getCmdFileFormat());
		detail.setCmdFileDirectory(rule.getCmdFileDirectory());
		detail.setUser(UserSummary.from(rule.getUser()));
		detail.setCpShortTitle(rule.getCp().getShortTitle());
		detail.setSiteName(rule.getSite().getName());
		detail.setTokens(rule.getTokens());

		return detail;
	}
}


package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum PrintRuleErrorCode implements ErrorCode{
	RULE_NOT_FOUND;

	public String code() {
		return "PRINT_RULE_" + this.name();
	}
}

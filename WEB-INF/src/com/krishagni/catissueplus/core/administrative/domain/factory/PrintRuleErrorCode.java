
package com.krishagni.catissueplus.core.administrative.domain.factory;

import com.krishagni.catissueplus.core.common.errors.ErrorCode;

public enum PrintRuleErrorCode implements ErrorCode{
	NOT_FOUND,CMD_FILE_DIR_REQ, TOKEN_REQ;
	public String code() {
		return "PRINT_RULE_" + this.name();
	}
}

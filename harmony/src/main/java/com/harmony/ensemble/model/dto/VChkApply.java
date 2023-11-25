package com.harmony.ensemble.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VChkApply {

	private String ensBoardNo;
	private String instName;
	private String memInfoEmail;
	private String ensApproval;
	private String ensPartIndex;
	
}

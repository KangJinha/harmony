package com.harmony.ensemble.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberEns {

	private String memNo;
	private String memInfoEmail;
	private String memInfoGender;
	
}

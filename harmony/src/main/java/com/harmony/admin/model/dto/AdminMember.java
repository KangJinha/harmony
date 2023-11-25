package com.harmony.admin.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminMember {
	private String adminNo;
	private String adminId;
	private String adminPw;
	private String adminName;
}

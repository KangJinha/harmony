package com.harmony.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Member {
	private String memNo;
	private String memAuthority;
	private String memKakaoNum;
}

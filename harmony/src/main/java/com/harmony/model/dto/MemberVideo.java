package com.harmony.model.dto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberVideo {
	private String memNo;
	private String videoType;
	private String videoLink;
}

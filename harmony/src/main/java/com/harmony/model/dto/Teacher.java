package com.harmony.model.dto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Teacher {
	private String memNo;
	private String teacherNumber;
	private String document;
	private String bank;
	private String account;
	private String workingArea;
}

package com.harmony.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberInfo {
	private String teacherNo;
	
	private String memNo;
	private String activityArea;
	private String introduce;
	private String profilPhoto;
	private String school;
	private String department;
	private String schoolState;
	private String name;
	private String gender;
	private int age;
	private String email;
	private String[] genre;
	private String[] interest;
	private List<MemberVideo> memberVideo;
	private List<MemberMusic> memberMusic;
	private String memCareer;
}

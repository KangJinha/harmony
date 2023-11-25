package com.harmony.ensemble.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberProfile {

	private String ensInstCode;
	private String ensTeamNo;
	private String ensMemNo;
	private Date ensMemJoinDate;
	private Date ensMemDropDate;
	private String ensMemPosition;
	private String memInfoEmail;
	private String memInfoGender;
	private String memInfoAge;
	private String memInfoIntroduce;
	private String instName;
}

package com.harmony.ensemble.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnsembleMember {

	private String ensTeamMemberNo;
	private String ensTeamNo;
	private String ensInstCode;
	private String ensMemNo;
	private Date ensMemJoinDate;
	private Date ensMemDropDate;
	private String ensNonMemGen;
	private int ensNonMemAge;
	private String ensMemPosition;
	
}

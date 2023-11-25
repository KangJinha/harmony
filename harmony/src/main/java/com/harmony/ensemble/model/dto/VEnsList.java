package com.harmony.ensemble.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VEnsList {

	private String ensBoardNo;
	private String ensBoardTitle;
	private String ensTeamName;
	private String genreName;
	private String ensLocation;
	private String ensTeamType;
	private Timestamp ensBoardDate;
	private String instrument;
	
}

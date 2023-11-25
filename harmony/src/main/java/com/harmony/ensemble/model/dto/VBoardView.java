package com.harmony.ensemble.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VBoardView {

	private String ensBoardNo;
	private String ensWriter;
	private String ensLocation;
	private String ensPlace;
	private String ensDetail;
	private Timestamp ensBoardDate;
	private String ensBoardTitle;
	private String ensTeamNo;
	private String ensTeamName;
	private String ensTeamType;
	private String genreName;
	private String instrument;
	
}

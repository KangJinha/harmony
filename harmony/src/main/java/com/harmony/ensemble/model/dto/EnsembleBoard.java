package com.harmony.ensemble.model.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnsembleBoard {

	private String ensBoardNo; 
	private String ensTeamNo;
	private String ensWriter;
	private String ensLocation;
	private String ensPlace;
	private String ensDetail;
	private Timestamp ensBoardDate;
	private int ensBoardView;
	private String ensBoardTitle;
	
	private EnsembleTeam teamList;
	private List<EnsembleBoardWantPart> wantPartList;
	
	}

package com.harmony.ensemble.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnsembleTeam {

	private String ensTeamNo;
	private String ensTeamName;
	private String ensTeamInfo;
	private String ensGenreNo;
	private String ensTeamType;
	
	private List<EnsembleTeamVideo> videoList;
	private List<EnsembleTeamMusic> musicList;
	private List<EnsembleTeamTime> timeList;
	private List<EnsembleTeamComment> commentList;
	private List<EnsembleMember> ensMemberList;
	
	
	
}

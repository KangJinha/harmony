package com.harmony.ensemble.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnsembleTeamTime {

	private String ensTeamNo;
	private String ensDayOfWeek;
	private Timestamp ensStartTime;
	private Timestamp ensEndTime;
	
}

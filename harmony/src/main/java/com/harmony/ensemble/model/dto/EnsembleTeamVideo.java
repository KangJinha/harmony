package com.harmony.ensemble.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnsembleTeamVideo {
	
	private String vNo;
	private String teamNo;
	private String vOriName;
	private String vReName;
}

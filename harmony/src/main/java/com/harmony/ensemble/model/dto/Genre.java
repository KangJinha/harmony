package com.harmony.ensemble.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genre {

	private String genreCode;
	private String genreName;
	
}

package com.harmony.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenreAll {	
	private String genreCode;
	private String genreName;
	private String memNo;
}

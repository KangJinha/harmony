package com.harmony.lesson.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveLesson {
	private int BoardNo;
	private String memNo;
}

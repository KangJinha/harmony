package com.harmony.lesson.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class LessonComment {
	private int commentNo;
	private int reviewNo;
	private String commentContent;
	private Date commentDate;
}

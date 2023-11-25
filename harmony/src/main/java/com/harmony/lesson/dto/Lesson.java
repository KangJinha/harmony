package com.harmony.lesson.dto;

import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lesson {
	private int boardNo;
	private String teacherNo;
	private String instNo;
	private String boardTitle;
	private String boardContent;
	private Timestamp boardDate;
	private int boardView;
	private String boardPlace;
	private String boardPrice;
	private String boardImg;
	private char boardDeadline;
	private Timestamp lessonStartTime;
	private Timestamp lessonEndTime;
	private String[] day;
	
	private int viewCnt;
	private double reviewPoint;
	private String memNo;
}

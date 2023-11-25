package com.harmony.lesson.dto;
import java.sql.Timestamp;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonApply {
	//레슨신청정보
	private int applyNo;
	private int boardNo;
	private String memNo;
	private String applyPlace;
	private int applyNumberOfTimes;
	private Date applyDate;
	private char applyAccept;
	
	//레슨신청일시
	private Timestamp lessonStartTime;
	private Timestamp lessonEndTime;
	private String[] lessonDay;
	
	//레슨후기
	private int reviewNo;
	private int reviewPoint;
	private String review;
	private Date reviewEnrollDate;
}

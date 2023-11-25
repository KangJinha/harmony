package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Qna {
	private int qstNo;
	private String qstWriter;
	private String catNo;
	private String catName;
	private String processCode;
	private String content;
	private Date writeDate;
	private String answer;
	private String answerContent;
	private Date ansDate;
	private String answerName;
}
 
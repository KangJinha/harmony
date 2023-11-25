package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class QNAList {
	private int qstNo;
	private String qstCategory;
	private String qstCode;
	private String qstWriter;
	private Date writeDate;
	private String qstCategoryName;
	private Date answerDate;
}

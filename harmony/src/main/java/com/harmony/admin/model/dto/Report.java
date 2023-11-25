package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Report {
	private int reportNo;
	private String catCode;
	private String catName;
	private String proCode;
	private String reporter;
	private String reportee;
	private String content;
	private Date reportDate;
	private String result;
	private Date resultDate;
}

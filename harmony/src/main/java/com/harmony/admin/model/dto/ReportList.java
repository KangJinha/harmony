package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportList {
	private int reportNo;
	private String catCode;
	private String proCode;
	private String reporter;
	private String reportee;
	private Date reportDate;
}

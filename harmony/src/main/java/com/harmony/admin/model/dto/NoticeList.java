package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeList {
	private int noticeNo;
	private String writer;
	private String title;
	private Date writeDate;
	private int viewCount;
	private boolean hasFile;
}

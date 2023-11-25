package com.harmony.admin.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notice {
	private int noticeNo;
	private String noticeWriter;
	private String title;
	private String content;
	private Date writeDate;
	private int viewCount;
	@Builder.Default
	private List<NoticeAttachFile> attachFileList = new ArrayList<>();
	
}

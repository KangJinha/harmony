package com.harmony.admin.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeAttachFile {
	private int noticeFileNo;
	private int noticeNo;
	private String oriName;
	private String reName;
}

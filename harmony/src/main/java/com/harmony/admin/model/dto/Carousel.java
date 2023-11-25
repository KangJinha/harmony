package com.harmony.admin.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Carousel {
	private int crslNo;
	private String crslName;
	private String crslImgLink;
	private String crslPageLink;
	private Date crslWriteDate;
	private Date crslEndDate;
	private int crslIntervalMs;
	private int crslViewRank;
	private String status;
}

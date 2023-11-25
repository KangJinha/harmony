package com.harmony.board.info.model.dto;

import java.sql.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoBoard {
	private int infBrdNo;
	private String infBrdWriter;
	private String infBrdTitle;
	private String infBrdContent;
	private String infBrdTitleImg;
	private String infBrdRegion;
	private String infBrdCatNo;
	private String infBrdTagNo;
	private Date infBrdRegDate;
}












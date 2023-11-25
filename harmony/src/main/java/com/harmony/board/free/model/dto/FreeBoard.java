package com.harmony.board.free.model.dto;

import java.sql.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeBoard {
	private int freBrdNo;
	private String freBrdWriter;
	private String freBrdTitle;
	private String freBrdContent;
	private String freBrdTitleImg;
	private Date freBrdDate;
	private int freBrdViews;
}

package com.harmony.board.info.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoCommentBoard {
	private int infComNo;
	private int infBrdNo;
	private String infComWriter;
	private String infComContent;
	private Timestamp infComDate;
	private Integer infComNoRef;
	private int infComLevel;

}

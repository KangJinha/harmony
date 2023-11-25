package com.harmony.board.free.model.dto;

import java.sql.Timestamp;

import com.harmony.board.info.model.dto.InfoCommentBoard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreeCommentBoard {
	private int freComNo;
	private int freBrdNo;
	private String freComWriter;
	private String freComContent;
	private Timestamp freComDate;
	private Integer freComRefNo; 
	private int freComLevel;

}

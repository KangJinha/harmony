package com.harmony.payment.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundList {
	private int refundHisNo;
	private String payHisNo;
	private Date refundReqDate;
	private String refundReason;
	private String refundStateCode;
	private String refundState;
	private int applyNo;
	private String impUid;
	private int price;
	private Date payReqDate;
}

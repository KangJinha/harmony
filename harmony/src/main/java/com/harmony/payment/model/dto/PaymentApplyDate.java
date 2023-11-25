package com.harmony.payment.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentApplyDate {
	private Timestamp startTime;
	private Timestamp endTime;
	private String dayOfWeek;
}

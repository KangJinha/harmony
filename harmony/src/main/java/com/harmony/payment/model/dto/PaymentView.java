package com.harmony.payment.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentView {
	private int applyNo;
	private String studentMemNo;
	private String instName;
	private int lessonTimes;
	private Date applyDate;
	private String status;
	private int price;
	private String place;
	private String teacherMemNo;
	private String teacherName;
	private String teacherGender;
	private String teacherEmail;
	private String teacherIntroduce;
	private String paymentStatus;
	private String payHisNo;
	@Builder.Default
	private List<PaymentApplyDate> dates = new ArrayList<>();
}

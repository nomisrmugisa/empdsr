package org.pdsr.pojos;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RedcapExtraction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date from;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date to;

	private Integer processingStage = 0;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Integer getProcessingStage() {
		return processingStage;
	}

	public void setProcessingStage(Integer processingStage) {
		this.processingStage = processingStage;
	}

}

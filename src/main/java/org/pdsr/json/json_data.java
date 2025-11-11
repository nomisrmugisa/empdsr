package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String question;
	private String answer;
	private boolean canreview;
	private boolean attachment;

	public json_data() {
		super();
	}

	public json_data(String question, String answer, boolean canreview) {
		super();
		this.question = question;
		this.answer = answer;
		this.canreview = canreview;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isCanreview() {
		return canreview;
	}

	public void setCanreview(boolean canreview) {
		this.canreview = canreview;
	}

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

}

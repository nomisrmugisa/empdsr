package org.pdsr.pojos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataWrapper<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}



}

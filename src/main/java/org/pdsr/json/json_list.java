package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_list implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<json_data> dataset;
	
	

	public json_list() {
		super();
	}

	public List<json_data> getDataset() {
		return dataset;
	}

	public void setDataset(List<json_data> dataset) {
		this.dataset = dataset;
	}
	
	
}

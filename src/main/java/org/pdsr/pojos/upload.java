package org.pdsr.pojos;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class upload implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sheetnumber;
	private Integer datamonth;
	private Integer datayear;
	private String filelocation;
	
	private MultipartFile file;

	
	public String getFilelocation() {
		return filelocation;
	}

	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}


	public Integer getDatamonth() {
		return datamonth;
	}

	public void setDatamonth(Integer datamonth) {
		this.datamonth = datamonth;
	}

	public Integer getDatayear() {
		return datayear;
	}

	public void setDatayear(Integer datayear) {
		this.datayear = datayear;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getSheetnumber() {
		return sheetnumber;
	}

	public void setSheetnumber(Integer sheetnumber) {
		this.sheetnumber = sheetnumber;
	}	
	

}

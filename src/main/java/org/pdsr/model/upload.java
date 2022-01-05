package org.pdsr.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class upload implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer filename;
	private String filelocation;
	private boolean override;
	private boolean checked;
	private MultipartFile file;

	
	public Integer getFilename() {
		return filename;
	}

	public void setFilename(Integer filename) {
		this.filename = filename;
	}

	public String getFilelocation() {
		return filelocation;
	}

	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}	
	

}

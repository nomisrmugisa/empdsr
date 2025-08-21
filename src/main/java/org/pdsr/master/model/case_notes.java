package org.pdsr.master.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class case_notes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String notes_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@Lob
	@Column
	private String notes_text;
	
	@Column
	private String notes_filetype;
	
	@Lob
	@Column
	private byte[] notes_file;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private String base64image;
	
	@Lob
	@Column
	private String notes_json;

	public String getNotes_json() {
		return notes_json;
	}

	public void setNotes_json(String notes_json) {
		this.notes_json = notes_json;
	}

	public String getNotes_uuid() {
		return notes_uuid;
	}

	public void setNotes_uuid(String notes_uuid) {
		this.notes_uuid = notes_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

	public String getNotes_text() {
		return notes_text;
	}

	public void setNotes_text(String notes_text) {
		this.notes_text = notes_text;
	}

	public byte[] getNotes_file() {
		return notes_file;
	}

	public void setNotes_file(byte[] notes_file) {
		this.notes_file = notes_file;
	}

	public String getNotes_filetype() {
		return notes_filetype;
	}

	public void setNotes_filetype(String notes_filetype) {
		this.notes_filetype = notes_filetype;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getBase64image() {
		return base64image;
	}

	public void setBase64image(String base64image) {
		this.base64image = base64image;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notes_uuid == null) ? 0 : notes_uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_notes other = (case_notes) obj;
		if (notes_uuid == null) {
			if (other.notes_uuid != null)
				return false;
		} else if (!notes_uuid.equals(other.notes_uuid))
			return false;
		return true;
	}
	
	
}

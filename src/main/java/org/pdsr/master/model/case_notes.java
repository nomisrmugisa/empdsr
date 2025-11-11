package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
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

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date notes_dispdate;
	
	@Column
	private String notes_dlvby; 
	
	@Column
	private String notes_dlvcontact; 
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date notes_dlvdate; 
	
	@Column
	private String notes_rcvby;  
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date notes_rcvdate;  
	
	@Column
	private String notes_ntfby;  
	
	@Column
	private String notes_ntfcontact;
	
	@Lob
	@Column
	private String notes_text;
	
	@Column
	private String notes_filetype;
	
	@Lob
	@Column
	private byte[] notes_file;
	
	@Column
	private Integer data_complete;
	
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

	public Date getNotes_dispdate() {
		return notes_dispdate;
	}

	public void setNotes_dispdate(Date notes_dispdate) {
		this.notes_dispdate = notes_dispdate;
	}

	public String getNotes_dlvby() {
		return notes_dlvby;
	}

	public void setNotes_dlvby(String notes_dlvby) {
		this.notes_dlvby = notes_dlvby;
	}

	public String getNotes_dlvcontact() {
		return notes_dlvcontact;
	}

	public void setNotes_dlvcontact(String notes_dlvcontact) {
		this.notes_dlvcontact = notes_dlvcontact;
	}

	public Date getNotes_dlvdate() {
		return notes_dlvdate;
	}

	public void setNotes_dlvdate(Date notes_dlvdate) {
		this.notes_dlvdate = notes_dlvdate;
	}

	public String getNotes_rcvby() {
		return notes_rcvby;
	}

	public void setNotes_rcvby(String notes_rcvby) {
		this.notes_rcvby = notes_rcvby;
	}

	public Date getNotes_rcvdate() {
		return notes_rcvdate;
	}

	public void setNotes_rcvdate(Date notes_rcvdate) {
		this.notes_rcvdate = notes_rcvdate;
	}

	public String getNotes_ntfby() {
		return notes_ntfby;
	}

	public void setNotes_ntfby(String notes_ntfby) {
		this.notes_ntfby = notes_ntfby;
	}

	public String getNotes_ntfcontact() {
		return notes_ntfcontact;
	}

	public void setNotes_ntfcontact(String notes_ntfcontact) {
		this.notes_ntfcontact = notes_ntfcontact;
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

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
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

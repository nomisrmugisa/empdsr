package org.pdsr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class sync_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String sync_id;
	
	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 80)
	private String sync_uuid;
	
	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 80)
	private String sync_code;
	
	@Column
	@NotNull
	@Size(min = 1, max = 80)	
	private String sync_name;
	
	@Column
	@Size(min = 1, max = 80)	
	private String sync_email;
	
	
	@Lob
	@Column
	private String sync_json;

	public String getSync_id() {
		return sync_id;
	}

	public void setSync_id(String sync_id) {
		this.sync_id = sync_id;
	}

	public String getSync_uuid() {
		return sync_uuid;
	}

	public void setSync_uuid(String sync_uuid) {
		this.sync_uuid = sync_uuid;
	}

	public String getSync_code() {
		return sync_code;
	}

	public void setSync_code(String sync_code) {
		this.sync_code = sync_code;
	}

	public String getSync_name() {
		return sync_name;
	}

	public void setSync_name(String sync_name) {
		this.sync_name = sync_name;
	}

	public String getSync_email() {
		return sync_email;
	}

	public void setSync_email(String sync_email) {
		this.sync_email = sync_email;
	}

	public String getSync_json() {
		return sync_json;
	}

	public void setSync_json(String sync_json) {
		this.sync_json = sync_json;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sync_id == null) ? 0 : sync_id.hashCode());
		result = prime * result + ((sync_uuid == null) ? 0 : sync_uuid.hashCode());
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
		sync_table other = (sync_table) obj;
		if (sync_id == null) {
			if (other.sync_id != null)
				return false;
		} else if (!sync_id.equals(other.sync_id))
			return false;
		if (sync_uuid == null) {
			if (other.sync_uuid != null)
				return false;
		} else if (!sync_uuid.equals(other.sync_uuid))
			return false;
		return true;
	}


}

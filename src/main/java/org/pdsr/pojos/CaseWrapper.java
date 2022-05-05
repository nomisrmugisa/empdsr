package org.pdsr.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.pdsr.master.model.case_identifiers;

public class CaseWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	private List<case_identifiers> rcases = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<case_identifiers> getRcases() {
		return rcases;
	}

	public void setRcases(List<case_identifiers> rcases) {
		this.rcases = rcases;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaseWrapper other = (CaseWrapper) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return id;
	}

}

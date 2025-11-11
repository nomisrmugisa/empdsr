package org.pdsr.pojos;

import java.io.Serializable;
import java.util.Objects;

public class icdpm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pm_code;
	private String pm_desc;
	private String pm_tsum;

	
	
	public icdpm() {
		super();
	}



	public icdpm(String pm_code, String pm_desc) {
		super();
		this.pm_code = pm_code;
		this.pm_desc = pm_desc;
	}
	
	

	public icdpm(String pm_code, String pm_desc, String pm_tsum) {
		super();
		this.pm_code = pm_code;
		this.pm_desc = pm_desc;
		this.pm_tsum = pm_tsum;
	}



	public String getPm_code() {
		return pm_code;
	}

	public void setPm_code(String pm_code) {
		this.pm_code = pm_code;
	}

	public String getPm_desc() {
		return pm_desc;
	}

	public void setPm_desc(String pm_desc) {
		this.pm_desc = pm_desc;
	}

	public String getPm_tsum() {
		return pm_tsum;
	}

	public void setPm_tsum(String pm_tsum) {
		this.pm_tsum = pm_tsum;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pm_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		icdpm other = (icdpm) obj;
		return Objects.equals(pm_code, other.pm_code);
	}

}

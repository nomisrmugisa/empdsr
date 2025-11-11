package org.pdsr.pojos;

import java.io.Serializable;

public class datamerger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean merge_location;
	private boolean merge_weekly;
	private boolean merge_cases;
	private boolean merge_audit;
	
	
	public boolean isMerge_location() {
		return merge_location;
	}
	public void setMerge_location(boolean merge_location) {
		this.merge_location = merge_location;
	}
	
	public boolean isMerge_weekly() {
		return merge_weekly;
	}
	public void setMerge_weekly(boolean merge_weekly) {
		this.merge_weekly = merge_weekly;
	}
	public boolean isMerge_cases() {
		return merge_cases;
	}
	public void setMerge_cases(boolean merge_cases) {
		this.merge_cases = merge_cases;
	}
	public boolean isMerge_audit() {
		return merge_audit;
	}
	public void setMerge_audit(boolean merge_audit) {
		this.merge_audit = merge_audit;
	}

}

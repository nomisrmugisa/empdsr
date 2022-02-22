package org.pdsr.pojos;

import java.io.Serializable;

public class datamerger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean merge_country;
	private boolean merge_region;
	private boolean merge_district;
	private boolean merge_facility;
	
	
	public boolean isMerge_country() {
		return merge_country;
	}
	public void setMerge_country(boolean merge_country) {
		this.merge_country = merge_country;
	}
	public boolean isMerge_region() {
		return merge_region;
	}
	public void setMerge_region(boolean merge_region) {
		this.merge_region = merge_region;
	}
	public boolean isMerge_district() {
		return merge_district;
	}
	public void setMerge_district(boolean merge_district) {
		this.merge_district = merge_district;
	}
	public boolean isMerge_facility() {
		return merge_facility;
	}
	public void setMerge_facility(boolean merge_facility) {
		this.merge_facility = merge_facility;
	}
	
	

}

package com.gcpweb.controller;

public class PreferenceSpec {
	
	private Long specId;
	
	private String prefkey ;
	private String prefvalue;
	private String descdetails;
	public long getSpecId() {
		return specId;
	}
	public void setSpecId(Long specId) {
		this.specId = specId;
	}
	public String getPrefkey() {
		return prefkey;
	}
	public void setPrefkey(String prefkey) {
		this.prefkey = prefkey;
	}
	public String getPrefvalue() {
		return prefvalue;
	}
	public void setPrefvalue(String prefvalue) {
		this.prefvalue = prefvalue;
	}
	public String getDescdetails() {
		return descdetails;
	}
	public void setDescdetails(String descdetails) {
		this.descdetails = descdetails;
	}
	@Override
	public String toString() {
		return "PreferenceSpec [specId=" + specId + ", prefkey=" + prefkey + ", prefvalue=" + prefvalue
				+ ", descdetails=" + descdetails + "]";
	}
	
}

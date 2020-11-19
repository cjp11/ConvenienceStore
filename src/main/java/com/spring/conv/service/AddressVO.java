package com.spring.conv.service;

public class AddressVO {
	
	private String placeName;
	private String addr;

	@Override
	public String toString() {
		return "AddressVO [placeName=" + placeName + ", addr=" + addr + "]";
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	

	

}

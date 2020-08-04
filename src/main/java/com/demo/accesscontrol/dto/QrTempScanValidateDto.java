package com.demo.accesscontrol.dto;

import java.io.Serializable;

public class QrTempScanValidateDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String accessCode;

    private String temperature;

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}	
	@Override
	public String toString() {
		return "QrTempScanValidateDto [accessCode=" + accessCode + ", temperature=" + temperature + "]";
	}
}

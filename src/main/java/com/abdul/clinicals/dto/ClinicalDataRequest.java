package com.abdul.clinicals.dto;

import lombok.Data;

@Data
public class ClinicalDataRequest {
	private String componentName;
	private String componentValue;
	private int patientId; 
}

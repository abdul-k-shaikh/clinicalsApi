package com.abdul.clinicals.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.clinicals.dto.ClinicalDataRequest;
import com.abdul.clinicals.model.ClinicalData;
import com.abdul.clinicals.model.Patient;
import com.abdul.clinicals.repos.ClinicalDataRepository;
import com.abdul.clinicals.repos.PatientRespository;
import com.abdul.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {

	private ClinicalDataRepository clinicalDataRepository;
	private PatientRespository patientRespository;

	public ClinicalDataController(ClinicalDataRepository clinicalDataRepository,
			PatientRespository patientRespository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRespository = patientRespository;
	}

	@PostMapping("/clinicals")
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRespository.findById(request.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalDataRepository.save(clinicalData);
	}

	@GetMapping("/clinicals/{patientId}/{componentName}")
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		
		if(componentName.equals("bmi")) {
			componentName="hw";
		}
		
		List<ClinicalData> clinicalData = clinicalDataRepository
				.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId, componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for (ClinicalData eachEntry : duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		return clinicalData;

	}
}

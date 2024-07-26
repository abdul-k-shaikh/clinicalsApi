package com.abdul.clinicals.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdul.clinicals.model.ClinicalData;
import com.abdul.clinicals.model.Patient;
import com.abdul.clinicals.repos.PatientRespository;
import com.abdul.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	Map<String, String> filters = new HashMap<>();
	
	//constructor injection
	@Autowired
	private PatientRespository respository;
	
	
	public PatientController(PatientRespository respository) {
		this.respository = respository;
	}
	
	//@RequestMapping(value="/patients", method=RequestMethod.GET)
	@GetMapping("/patients")
	public List<Patient>getPatients(){
		return respository.findAll();		
		
	}
	
	
	@GetMapping("/patients/{id}")
	public Patient getPatient(@PathVariable("id") int id ) {
		return respository.findById(id).get();
	}
	
	@PostMapping("/patients")
	public Patient savePatient(@RequestBody Patient patient) {
		return respository.save(patient);
	}
	
	@GetMapping("/patients/analyze/{id}")
	public Patient analyze(@PathVariable("id") int id) {
		Patient patient = respository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for(ClinicalData eachEntry :duplicateClinicalData) {
			
			
			
			if(filters.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
				continue;
			}else {
				filters.put(eachEntry.getComponentValue(), null);
			}
				
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		filters.clear();
		return patient;
	}

	
}

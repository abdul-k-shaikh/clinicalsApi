package com.abdul.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abdul.clinicals.model.Patient;

public interface PatientRespository extends JpaRepository<Patient, Integer> {

}

package com.abdul.clinicals.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="clinicaldata")
public class ClinicalData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String componentName;
	private String componentValue;
	private Timestamp measuredDateTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="patient_id", nullable = false)
	@JsonIgnore
	private Patient patient;
	
}

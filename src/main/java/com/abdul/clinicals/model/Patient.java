package com.abdul.clinicals.model;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String lastName;
	private String firstName;
	private int age;
	
	/*by default fetch type is lazy, wheneve application needs it will fetch,
	 * but below we are trying to use Fetchtype as eager so it will fetch all data while loading other 
	 * fileds data
	 * 
	 * */
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "patient" ) //if we do anything in patient table that will affect on Clinical data table and if delete patient then it will delete all records related to that patient from clinicalsData table
	private List<ClinicalData> clinicalData;
}

package com.employeeservice.employeeappnew.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

	private Integer employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy")
	private Date employeeJoiningDate;
	private double employeeSalary;
	private String employeeEmail;
	private String employeeContactNumber;
	private String employeeDesignation;
	private String employeeUniqueCode;
}

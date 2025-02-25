package com.employeeservice.employeeappnew.service;

import com.employeeservice.employeeappnew.dto.EmployeeRequestDTO;
import com.employeeservice.employeeappnew.dto.EmployeeResponseDTO;
import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import com.employeeservice.employeeappnew.exception.EmployeeServiceBusinessException;
import com.employeeservice.employeeappnew.exception.ResourceNotFoundException;
import com.employeeservice.employeeappnew.util.EmployeeAppUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.employeeservice.employeeappnew.dao.EmployeeDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class EmployeeService implements EmployeeServiceInterface{

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public EmployeeResponseDTO onboardNewEmployee(EmployeeRequestDTO employeeRequestDTO) {

		//Convert DTO -> Entity
		EmployeeEntity employeeEntity = EmployeeAppUtil.mapEmployeeDTOToEmployeeEntity(employeeRequestDTO);
//		EmployeeEntity addEmployee = null;
		try {
			EmployeeEntity addEmployee = employeeDao.save(employeeEntity);
			EmployeeResponseDTO employeeResponseDTO = EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(addEmployee);
			//employeeResponseDTO.setEmployeeUniqueCode(UUID.randomUUID().toString().split("-")[0]);
			return employeeResponseDTO;
		} catch (Exception exception) {
			throw new EmployeeServiceBusinessException("onboardNewEmployee service method failed....");
		}
	}

	@Override
	public List<EmployeeResponseDTO> viewAllOnboardEmployees() {

		Iterable<EmployeeEntity> employeeEntities = null;
		try {
			employeeEntities = employeeDao.findAll();
			return StreamSupport.stream(employeeEntities.spliterator(),false)
					.map(EmployeeAppUtil::mapEmployeeEntityToEmployeeDTO)
					.collect(Collectors.toList());
		} catch (Exception exception) {
			throw new EmployeeServiceBusinessException("viewAllOnboardEmployees service method failed....");
		}
	}

	@Override
	public EmployeeResponseDTO findEmployeeByID(Integer employeeId) {

		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity = employeeDao.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee","employeeID",employeeId));
			return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(employeeEntity);
		} catch (Exception exception) {
			throw new EmployeeServiceBusinessException("findEmployeeByID service method failed....");
		}
	}

	@Override
	public void deleteEmployee(Integer employeeId) {
		try{
			employeeDao.deleteById(employeeId);
		} catch (Exception e){
			//throw new ResourceNotFoundException("Employee","employeeID",employeeId);
			throw new EmployeeServiceBusinessException("deleteEmployee service method failed....");
		}
	}

	@Override
	public EmployeeResponseDTO updateEmployeeByEmployeeId(Integer employeeId, EmployeeRequestDTO employeeRequestDTO) {

		//Get the existing object
		EmployeeEntity existingEmployeeEntity = null;
		try {
			existingEmployeeEntity = employeeDao.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee","employeeID",employeeId));
			//Modified the existing object with the new value
			existingEmployeeEntity.setEmployeeFirstName(employeeRequestDTO.getEmployeeFirstName());
			existingEmployeeEntity.setEmployeeLastName(employeeRequestDTO.getEmployeeLastName());
			//existingEmployeeEntity.setEmployeeJoiningDate(employeeRequestDTO.getEmployeeJoiningDate());
			existingEmployeeEntity.setEmployeeSalary(employeeRequestDTO.getEmployeeSalary());
			existingEmployeeEntity.setEmail(employeeRequestDTO.getEmail());
			existingEmployeeEntity.setContactNumber(employeeRequestDTO.getContactNumber());
			existingEmployeeEntity.setDesignation(employeeRequestDTO.getDesignation());
			//save the modified value
			EmployeeEntity updateEmployeeEntity = employeeDao.save(existingEmployeeEntity);
			return EmployeeAppUtil.mapEmployeeEntityToEmployeeDTO(updateEmployeeEntity);
		} catch (Exception exception) {
			throw new EmployeeServiceBusinessException("updateEmployeeByEmployeeId service method failed....");
		}
	}
}

package com.employeeservice.employeeappnew.dao;

import com.employeeservice.employeeappnew.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<EmployeeEntity, Integer>{

}

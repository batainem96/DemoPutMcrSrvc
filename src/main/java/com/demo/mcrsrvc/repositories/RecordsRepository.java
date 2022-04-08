package com.demo.mcrsrvc.repositories;

import com.demo.mcrsrvc.models.EmployeeRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface RecordsRepository extends MongoRepository<EmployeeRecord, String> {

    @Query("{empId:'?0'}")
    EmployeeRecord findRecordByEmpId(String empId);

}



package com.demo.mcrsrvc.services;

import com.demo.mcrsrvc.models.EmployeeRecord;
import com.demo.mcrsrvc.repositories.RecordsRepository;
import com.demo.mcrsrvc.util.exceptions.InvalidRequestException;
import com.demo.mcrsrvc.util.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The RecordsService Class is responsible for applying business logic to incoming HTTP Requests from the Controller
 * layer and passing them to the Data Access layer for querying the Employee Records collection.
 */
@Service
public class RecordsService {

    private final RecordsRepository recordsRepository;

    @Autowired
    public RecordsService(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    /**
     * This method verifies the empId is valid/not null and then passes the value to the RecordsRepository. If no record
     * is returned, will throw ResourceNotFoundException.
     * @param empId - employee ID for query.
     * @return - employee record with the employee ID queried.
     */
    public EmployeeRecord rewriteRecordByEmpId(String empId, String firstName, String lastName, String dept) {

        EmployeeRecord record = new EmployeeRecord(empId, firstName, lastName, dept);

        boolean isExceptionBeingThrown = false;
        String exceptionMessage = "The following input field(s) is/are invalid: ";

        // Validate each field in record and append exceptionMessage as necessary
        if(record.getEmpId() == null || record.getEmpId().trim().isEmpty()) {
            isExceptionBeingThrown = true;
            exceptionMessage += "empId";
        }
        if(record.getFirstName() == null || record.getFirstName().trim().isEmpty()) {
            if(isExceptionBeingThrown) exceptionMessage += ", ";
            isExceptionBeingThrown = true;
            exceptionMessage += "firstName";
        }
        if(record.getLastName() == null || record.getLastName().trim().isEmpty()) {
            if(isExceptionBeingThrown) exceptionMessage += ", ";
            isExceptionBeingThrown = true;
            exceptionMessage += "lastName";
        }
        if(record.getDept() == null || record.getDept().trim().isEmpty()) {
            if(isExceptionBeingThrown) exceptionMessage += ", ";
            exceptionMessage += "dept";
        }

        exceptionMessage += ".";

        // One or more fields in record were invalid
        if(isExceptionBeingThrown) {
            throw new InvalidRequestException(exceptionMessage);
        }

        // Placeholder EmployeeRecord to obtain _id from DB
        EmployeeRecord _idRecord = recordsRepository.findRecordByEmpId(record.getEmpId());

        // If record does not exist, stop. Do not insert new record (only updating existing records)
        if( _idRecord == null) {
            throw new ResourceNotFoundException("No existing record with empId #" + record.getEmpId() + " exists to update.");
        }

        record.setId(_idRecord.getId());

        return recordsRepository.save(record);

    }

}

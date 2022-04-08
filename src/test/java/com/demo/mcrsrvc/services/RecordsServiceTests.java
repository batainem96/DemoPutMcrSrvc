package com.demo.mcrsrvc.services;

import com.demo.mcrsrvc.models.EmployeeRecord;
import com.demo.mcrsrvc.repositories.RecordsRepository;
import com.demo.mcrsrvc.util.exceptions.InvalidRequestException;
import com.demo.mcrsrvc.util.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RecordsServiceTests {

    @InjectMocks
    RecordsService recordsService;

    @Mock
    RecordsRepository recordsRepository;

    @Test
    public void whenValidRecordGiven_thenAssertionSucceeds() {

        EmployeeRecord validRecord = new EmployeeRecord("123", "John", "Doe", "IT");

        Mockito.when(recordsRepository.save(validRecord)).thenReturn(validRecord);

        EmployeeRecord actualRecord = recordsRepository.save(validRecord);

        assertEquals(actualRecord, validRecord);

    }

    @Test
    public void whenInvalidRecordGiven_andInvalidRequestExceptionThrown_thenAssertionSucceeds() {

        EmployeeRecord invalidRecord_1 = new EmployeeRecord("", "valid", "valid", "valid");
        EmployeeRecord invalidRecord_2 = new EmployeeRecord("", null, "valid", "valid");
        EmployeeRecord invalidRecord_3 = new EmployeeRecord(null, "", null, "valid");
        EmployeeRecord invalidRecord_4 = new EmployeeRecord("", null, "", null);

        Mockito.when(recordsRepository.save(invalidRecord_1)).thenThrow(InvalidRequestException.class);
        Mockito.when(recordsRepository.save(invalidRecord_2)).thenThrow(InvalidRequestException.class);
        Mockito.when(recordsRepository.save(invalidRecord_3)).thenThrow(InvalidRequestException.class);
        Mockito.when(recordsRepository.save(invalidRecord_4)).thenThrow(InvalidRequestException.class);

        Exception exception_1 = assertThrows(InvalidRequestException.class, () -> {
            recordsService.rewriteRecordByEmpId(
                    invalidRecord_1.getEmpId(),
                    invalidRecord_1.getFirstName(),
                    invalidRecord_1.getLastName(),
                    invalidRecord_1.getDept()
            );
        });
        Exception exception_2 = assertThrows(InvalidRequestException.class, () -> {
            recordsService.rewriteRecordByEmpId(
                    invalidRecord_2.getEmpId(),
                    invalidRecord_2.getFirstName(),
                    invalidRecord_2.getLastName(),
                    invalidRecord_2.getDept()
            );
        });
        Exception exception_3 = assertThrows(InvalidRequestException.class, () -> {
            recordsService.rewriteRecordByEmpId(
                    invalidRecord_3.getEmpId(),
                    invalidRecord_3.getFirstName(),
                    invalidRecord_3.getLastName(),
                    invalidRecord_3.getDept()
            );
        });
        Exception exception_4 = assertThrows(InvalidRequestException.class, () -> {
            recordsService.rewriteRecordByEmpId(
                    invalidRecord_4.getEmpId(),
                    invalidRecord_4.getFirstName(),
                    invalidRecord_4.getLastName(),
                    invalidRecord_4.getDept()
            );
        });

        String expectedMessage_1 = "The following input field(s) is/are invalid: empId.";
        String expectedMessage_2 = "The following input field(s) is/are invalid: empId, firstName.";
        String expectedMessage_3 = "The following input field(s) is/are invalid: empId, firstName, lastName.";
        String expectedMessage_4 = "The following input field(s) is/are invalid: empId, firstName, lastName, dept.";

        String actualMessage_1 = exception_1.getLocalizedMessage();
        String actualMessage_2 = exception_2.getLocalizedMessage();
        String actualMessage_3 = exception_3.getLocalizedMessage();
        String actualMessage_4 = exception_4.getLocalizedMessage();

        assertTrue(actualMessage_1.contains(expectedMessage_1));
        assertTrue(actualMessage_2.contains(expectedMessage_2));
        assertTrue(actualMessage_3.contains(expectedMessage_3));
        assertTrue(actualMessage_4.contains(expectedMessage_4));

    }

    @Test
    public void whenRecordGiven_andRecordNotExist_andThrowsResourceNotFoundException_thenAssertionSucceeds() {

        EmployeeRecord novelRecord = new EmployeeRecord("1-800", "Does", "Not", "Exist");

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            recordsService.rewriteRecordByEmpId(
                    novelRecord.getEmpId(),
                    novelRecord.getFirstName(),
                    novelRecord.getLastName(),
                    novelRecord.getDept()
            );
        });

        String expectedMessage = "No existing record with empId #1-800 exists to update.";
        String actualMessage = exception.getLocalizedMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}

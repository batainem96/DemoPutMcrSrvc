package com.demo.mcrsrvc.controllers;

import com.demo.mcrsrvc.models.EmployeeRecord;
import com.demo.mcrsrvc.services.RecordsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PutControllerTests {

    @InjectMocks
    PutController putController;

    @Mock
    RecordsService recordsService;

    @Test
    public void updateRecord_whenGivenParams_returnsEmployeeRecord_thenAssertionSucceeds() {

        EmployeeRecord expectedRecord = new EmployeeRecord("123", "John", "Doe", "IT");

        Mockito.when(recordsService.rewriteRecordByEmpId("123", "John", "Doe", "IT"))
                .thenReturn(expectedRecord);

        EmployeeRecord actualRecord = putController.putRecordWithId("123", "John", "Doe", "IT");

        assertEquals(actualRecord, expectedRecord);

    }

}

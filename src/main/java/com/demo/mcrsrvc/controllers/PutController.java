package com.demo.mcrsrvc.controllers;

import com.demo.mcrsrvc.models.EmployeeRecord;
import com.demo.mcrsrvc.services.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class PutController {

    RecordsService recordsService;

    @Autowired
    public PutController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @PutMapping
    public EmployeeRecord putRecordWithId(
            @RequestParam String empId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dept
    ) {
        return recordsService.rewriteRecordByEmpId(empId, firstName, lastName, dept);
    }

}

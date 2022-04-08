package com.demo.mcrsrvc.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
@Document(collection = "records")
public class EmployeeRecord {

    // TODO: separate this model from another model object that doesn't hold id field (id field is for a workaround to
    //      an issue with ...mongodb.repository.MongoRepository's save method not recognizing matches without _id value)

    @Id
    @EqualsAndHashCode.Exclude
    private ObjectId id;
    @Indexed(unique = true)
    private String empId;
    private String firstName;
    private String lastName;
    private String dept;

    public EmployeeRecord(String empId, String firstName, String lastName, String dept) {
        super();
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
    }

    public EmployeeRecord(String firstName, String lastName, String dept) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dept = dept;
    }

    public EmployeeRecord(String empId) {
        super();
        this.empId = empId;
    }

}
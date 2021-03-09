package com.bootcamp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Employee<T> {
    private String empName;
    private Long empId;
    private String organization;
    private String role;

}

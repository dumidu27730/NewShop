package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Customer {
    private String id;
    private String name;
    private String address;
    private Double salary;
}

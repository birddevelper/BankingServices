package com.bluebank.accountservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name="customer")
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "id")
    private int ID;

    @Column(name = "name")
    privateString name;

    @Column(name = "sure_name")
    private String sureName;
}

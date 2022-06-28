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
    int ID;

    @Column(name = "name")
    String name;

    @Column(name = "sure_name")
    String sureName;
}

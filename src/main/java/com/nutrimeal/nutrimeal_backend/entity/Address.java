package com.nutrimeal.nutrimeal_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@Table(name = "addresses")
public class Address {

//    address_id int primary key,
//    user_id varchar(255),
//    full_name nvarchar(255),
//    phone varchar(10),
//    gender tinyint,
//    district nvarchar(255),
//    ward nvarchar(255),
//    foreign key (user_id) references users(user_id)
//);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;



}

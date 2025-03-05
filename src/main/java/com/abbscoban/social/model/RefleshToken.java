package com.abbscoban.social.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reflesh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class RefleshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "reflesh_token")
    private String refleshToken;

    @Column(name = "expire_date")
    private Date expireDate;

    @ManyToOne
    private User user;

}

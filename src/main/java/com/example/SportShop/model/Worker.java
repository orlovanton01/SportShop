package com.example.SportShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_id", nullable = false)
    private Integer id;

    @Column(name = "w_surname", length = 30)
    private String wSurname;

    @Column(name = "w_name", length = 30)
    private String wName;

    @Column(name = "w_patronymic", length = 30)
    private String wPatronymic;

}
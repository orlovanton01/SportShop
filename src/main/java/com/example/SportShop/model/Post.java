package com.example.SportShop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "w_id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "w_id", nullable = false, referencedColumnName = "w_id", insertable = false, updatable = false)
    private Worker worker;

    @Column(name = "p_id")
    private Integer pId;

    @Column(name = "post", length = 30)
    private String post;

    @Column(name = "salary")
    private Integer salary;

}
package com.santosh.cakemanager.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@DynamicUpdate
@Table(name="cake", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Cake implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @JsonProperty("title")
    @Column(name="title",nullable = false,updatable = false,unique = true)
    private String title;

    @JsonProperty("desc")
    @Column(name = "description", nullable = false)
    private String description;

    @JsonProperty("image")
    @Column(name = "imageLink", nullable = false)
    private String imageLink;

    public Cake(String title, String description, String imageLink) {
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
    }
}

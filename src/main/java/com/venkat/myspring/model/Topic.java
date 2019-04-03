package com.venkat.myspring.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="topic", schema = "searchapp")
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.AUTO did not work
    private Long id;

    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="text_field1")
    private String textField1;
    @Column(name="text_field2")
    private String textField2;

    public Topic(){}

    public Topic(String name, String description, String textField1, String getTextField2) {
        this.name = name;
        this.description = description;
        this.textField1 = textField1;
        this.textField2 = getTextField2;
    }


    public Topic(Long id, String name, String description, String textField1, String getTextField2) {
        this(name,description,textField1,getTextField2);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextField1() {
        return textField1;
    }

    public void setTextField1(String textField1) {
        this.textField1 = textField1;
    }

    public String getTextField2() {
        return textField2;
    }

    public void setTextField2(String textField2) {
        this.textField2 = textField2;
    }
}

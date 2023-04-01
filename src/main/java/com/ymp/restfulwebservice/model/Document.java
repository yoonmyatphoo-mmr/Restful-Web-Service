package com.ymp.restfulwebservice.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;


/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.model.File
 */


@Data
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;


    @Lob //@Lob annotation to specify that should be stored in a database BLOB (Binary Large Object) column.
    private List<Blob> contents;


    // constructors, getters, and setters omitted for brevity
}


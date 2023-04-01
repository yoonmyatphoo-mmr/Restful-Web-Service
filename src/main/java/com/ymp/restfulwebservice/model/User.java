package com.ymp.restfulwebservice.model;

import com.ymp.restfulwebservice.model.Request.UserListObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.model.User
 */

@Data // using lombok to get getter and setter method which is needed to inject data from properties file into fields by setter method, getter method is need for access field value from other class.
@NoArgsConstructor
@AllArgsConstructor
@Entity //@Entity annotation specifies that the class is an Entity.
//@Table(name="user")It specifies the table in the database with which this entity is mapped.
//if you don't provide table annotation then jpa smart enough provide the table as the name of the class.
public class User {
    public User( String name, int age, String occupation) {

        this.name = name;
        this.age = age;
        this.occupation = occupation;
    }

    @Id //@Id annotation specifies the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int age;
    private String occupation;
    public User(UserListObject userList) {
    }
}

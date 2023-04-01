package com.ymp.restfulwebservice.model.Request;

import com.ymp.restfulwebservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data// using lombok to get getter and setter method which is needed to inject data from properties file into fields by setter method, getter method is need for access field value from other class.
@NoArgsConstructor
@AllArgsConstructor
public class UserListObject {

    List<User> userListObject;

}

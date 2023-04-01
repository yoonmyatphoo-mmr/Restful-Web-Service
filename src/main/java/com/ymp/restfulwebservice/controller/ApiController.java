package com.ymp.restfulwebservice.controller;

import com.ymp.restfulwebservice.model.User;
import com.ymp.restfulwebservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.controller.ApiController
 */


@RestController //@RestController is a convenient annotation that combines @Controller and @ResponseBody
@Slf4j // for logging by using lombok
public class ApiController {

    //in this class, I will do CRUD with Repository Layer.
    @Autowired
    UserRepo userRepo;

    @GetMapping(value = "/") //localhost:8080
    public String getPage() {
        log.info("Enter getPage...");
        log.info(".....Exit getPage.....");
        return "Welcome";
    }

    @GetMapping(value = "/user") // http://localhost8080/user
    public User getUser() {
        log.info("Enter user");
        log.info(".....Exit user.....");
       /*User user = new User();
        user.setName("Hla");
        user.setAge(22);
        user.setOccupation("Beauty blogger");
        return user;*/

        return new User("Hla", 22, "Beauty Blogger");//you can return quickly.
        // return a java bin object that is single user object to the client
    }

    @GetMapping(value = "/getUsersList")
    public List<User> getUsersList() {
        log.info("Enter ListUsers");

        List<User> users = new ArrayList<>();
        users.add(new User("Pyae", 23, "teacher"));
        users.add(new User("Moe", 25, "assistant"));
        users.add(new User("Aye Aye", 25, "Engineering"));
        users.add(new User("Moe", 25, "assistant"));
        log.info("users: " + users);
        log.info(".....Exit getListUser.....");
        return users;
        //return array of json object to the client
    }

    //http://localhost8080/userInfo/Tony/22/ITSupport
    //add @PathVariable annotation to bind a template uri pathVariable vale to the method argument
    @GetMapping("/userInfo/{name}/{age}/{occupation}") //uri template path variable
    public User userPathVariable(@PathVariable("name") String name, @PathVariable("age") int age,
                                 @PathVariable("occupation") String occupation) {
        log.info("Enter userPathVariable....");
        log.info("name: " + name + " age: " + age + " occupation: " + occupation);
        log.info(".....Exit userPathVariable.....");
        return new User(name, age, occupation);
    }


    //build rest API to handle query parameters
    //in query method,  rest endpoint url, question mark we can keep a key value here key and then value.
    //for multiple query parameter,after keep post key value appear we can give ampersand.
    //http://localhost:8080/userQueryParam?name=Jonny&age=45&occupation=architect
    //add @RequestParam to bind the value of this web request parameter to method argument.
    @GetMapping(value = "/userQueryParam")
    public User userQueryParam(@RequestParam(value = "name") String name,
                               @RequestParam(value = "age") int age,
                               @RequestParam(value = "occupation") String occupation) {

        log.info("Enter userQueryParam....");
        log.info("name: " + name + " age: " + age + " occupation: " + occupation);
        log.info(".....Exit userQueryParam.....");
        return new User(name, age, occupation);

    }

    //localhost:8080/getUsers
    @GetMapping(value = "/getUsers")
    public List<User> getUsersDb() {
        log.info("Enter getUsers....");
        List<User> getUsers = userRepo.findAll();
        log.info("getUsers are: " + getUsers);
        log.info(".....Exit getUsers.....");
        return getUsers;
    }

    @PostMapping(value = "/saveUsers")
    //@RequestBody annotation allows us to retrieve the request's body and automatically covert it to java object.
    public String saveUser(@RequestBody User user) {
        log.info("Enter saveUserWithRepoLayer....");

        User saveUsers = userRepo.save(user);//save with directly repo layer.
        log.info("saveUsers : " + saveUsers);
        return "saved....";
    }

    @PutMapping(value = "/update/{id}")
    //we can provide complete response details in this responseEntity class.we can add a status header etc.
    public ResponseEntity updateUsers(@PathVariable long id, @RequestBody User user) {

        log.info("Enter updateUsers....");
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setAge(user.getAge());
        updatedUser.setOccupation(user.getOccupation());
        User updatedUsers = userRepo.save(updatedUser);

        log.info("updatedUsers: " + updatedUsers);
        log.info(".....Exit updateUsers.....");
        return ResponseEntity.ok().body(updatedUser);

    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        log.info("Enter deleteUsers....");
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        log.info("Successfully delete id :" + id);
        log.info(".....Exit deleteUser.....");
        return "Delete user with id: " + id;
    }


}

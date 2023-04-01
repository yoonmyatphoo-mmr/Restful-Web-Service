package com.ymp.restfulwebservice.service;

import com.ymp.restfulwebservice.exception.ResourceNotFoundException;
import com.ymp.restfulwebservice.model.Document;
import com.ymp.restfulwebservice.model.ResponseMessage;
import com.ymp.restfulwebservice.model.User;
import com.ymp.restfulwebservice.model.Request.UserListObject;
import com.ymp.restfulwebservice.model.Response.ResponseObject;
import com.ymp.restfulwebservice.model.Response.StatusObject;
import com.ymp.restfulwebservice.repository.DocumentRepo;
import com.ymp.restfulwebservice.repository.StatusObjectRepo;
import com.ymp.restfulwebservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.service.UserServiceImpl
 */
@Slf4j // for logging by using lombok
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    StatusObjectRepo statusObjectRepo;

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        /** Optional<User> user = userRepo.findById(id);
         if(user.isPresent()){
         return user.get();
         }else {
         throw new ResourceNotFoundException("user","Id","id");
         }**/
        return userRepo.findById(id).orElseThrow(() -> //by using lambda expression
                new ResourceNotFoundException("User", "Id", id));
    }

    @Override
    public User updateUser(User user, long id) {

        //we need to check whether user with given id exist in DB or Not.
        User existingUser = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));
        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        existingUser.setOccupation(user.getOccupation());
        //save existing user to DB.
        userRepo.save(existingUser);
        return existingUser;
    }

    @Override
    public long deleteUser(long id) {
        //check whether a user exist in a DB or not
        userRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "ID", id));

        userRepo.deleteById(id);
        return id;
    }

    @Override
    public List<ResponseMessage> responseCustomObjectListDemo() {
        return Arrays.asList(new ResponseMessage("Thank You", "job.myanmar@gmail.com"),
                new ResponseMessage("Position=Junior Developer", "099887887")
        );
    }

    @Override
    public String responseSingleStringDb(Long id) {
        String name = userRepo.findUserNameById(10L);//I put data in manual.you can put that id with @PathVariable
        return name; //return string only from DB.
    }

    @Override
    public User responseObjectDb() {

        User user = new User("Yoon", 21, "Junior Developer");
        userRepo.save(user);
        return user; //return user object
    }

    @Override
    public List<String> responseStringListDb(String occupation) {

        List<String> name = userRepo.findUserNameByOccupation("Product Owner");
        /**
         * you can create List<String> strList = new ArrayList<String>();
         * and can set data by using strList.add("Phyu Phyu"); strList.add("Mg Mg"), etc..
         * and return that stuList object.
         *
         * And also,you can add data by using Arrays.asList,
         * eg. List<String> strList = Arrays.asList("Yoon Yoon", "Student"); and return that strList.
         * here, that is for manaual.
         * I return retrieve String List form database...
         **/
        return name;

    }

    @Override
    public List<User> responseObjectListDemo(String occcupation) {


        /** that is for manual....
         * List<User> users = new ArrayList<>();

         User user1 = new User("Phway",22,"Teacher");
         User user2 = new User("Maung",12,"Student");
         User user3 = new User("Thant",30,"Manager");

         users.add(user1);
         users.add(user2);
         users.add(user3);
         **/

        List<User> userList = userRepo.findUserByOccupation(occcupation);
        return userList;
    }

    @Override
    public ResponseObject responseNestedObjectDemo() {

        String timestamp = Instant.now().toString(); // current time
        StatusObject statusObject = new StatusObject("SUCCESS", "No error");
        statusObjectRepo.save(statusObject);
        List<StatusObject> statusObject1 = statusObjectRepo.findAll();
        ResponseObject responseObject = new ResponseObject(timestamp, statusObject1);
        return responseObject;
    }

    @Override
    public User requestBodyObjectDb(User user) {
        User data = userRepo.save(user);
        return data;
    }
    @Override
    public void requestBodyObjectListDb(UserListObject userListObject) {

        /**
         *
         List<User> userList = new ArrayList<>();

         List<User> originalUserList = userListObject.getUserListObject();
         for (User user : originalUserList) {
         User newUser = new User(user.getName(), user.getAge(), user.getOccupation());
         userList.add(newUser);
         }
         I use with lambda expression below.
         */
        List<User> userList = userListObject.getUserListObject()
                .stream()
                .map(user -> new User(user.getName(), user.getAge(), user.getOccupation()))
                .collect(Collectors.toList());

        userRepo.saveAll(userList);
        // return (List<User>) userListObject;
    }

    @Override
    public User requestPathVariableIntegerDb(Long id) {

        Optional<User> userList = userRepo.findById(id);
        log.info("userList: {}", userList);
        return userRepo.findById(id).orElseThrow(() -> //by using lambda expression
                new ResourceNotFoundException("User", "Id", id));

    }

    @Override
    public void requestFromHeaderDemo(Map<String, String> allHeadersMap) {

    }

    @Override
    public void requestFromHeaderAndRequestBodyDb(Map<String, String> allHeadersMap, User user) {
       userRepo.save(user);
    }

    @Override
    public void requestFileUploadDemo(File file, String name) {

    }

    @Override
    public void requestFromHeaderAndListFileUploadDemo(List<MultipartFile> files) throws IOException {
       /* try {
            Document document = new Document();
            document.setTitle("Multiple Files");
            List<Blob> contents = new ArrayList<>();

            for (MultipartFile file : files) {
                contents.add(new SerialBlob(file.getBytes()));
            }

            document.setContents(contents);
            documentRepo.save(document);
        } catch (Exception e) {
            throw new IOException("Error saving document: " + e.getMessage());
        }*/
    }

    @Override
    public List<User> requestPathVariableAndResponseObjectDemo(String occupation) {

       List<User> object =  userRepo.findAllByOccupation(occupation);
       return object;
    }


}

package com.ymp.restfulwebservice.controller;

import com.ymp.restfulwebservice.model.ResponseMessage;
import com.ymp.restfulwebservice.model.User;
import com.ymp.restfulwebservice.model.Request.UserListObject;
import com.ymp.restfulwebservice.model.Response.ResponseObject;
import com.ymp.restfulwebservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author: Yoon Myat Phoo
 * @created: 25/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.controller.UserController
 */

@RestController //@RestController is a convenient annotation that combines @Controller and @ResponseBody
@RequestMapping("/api/users")//the base URL for this controller.
@Slf4j // for logging by using lombok

public class UserController {

    //in this class, I will do CRUD with serviceLayer.
    @Autowired
    UserService userService;

    //build create employee REST API
    @PostMapping()
    //we can provide complete response details in this responseEntity class.we can add a status header etc.
    public ResponseEntity<User> saveUsers(@RequestBody User user) {

        log.info("Enter saveUserWithServiceLayer....");
        User saveUser = userService.saveUser(user);
        log.info("saveUser: " + saveUser);
        log.info(".....Exit saveUserWithServiceLayer.....");
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    //build getAll user REST API
    @GetMapping
    public List<User> getAllUser() {
        log.info("Enter getAllUsers....");
        List<User> getAllUsers = userService.getAllUsers();
        log.info("getAllUsers: " + getAllUsers);
        log.info(".....Exit getAllUsers.....");
        return getAllUsers;
    }

    //build get user by id REST API
    //http:localhost:8080/api/user/1
    @GetMapping("{id}") //uri template variable
    public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
        log.info("Enter getUserById....");
        User getUserById = userService.getUserById(userId);
        log.info("getUserById: " + getUserById);
        log.info(".....Exit getUserById.....");
        return new ResponseEntity<>(getUserById, HttpStatus.OK);
    }


    @GetMapping("/responseCustomObjectListDemo")
    public ResponseEntity responseCustomObjectListDemo() {

        log.info("Enter responseCustomObjectListDemo method");
        List<ResponseMessage> messageAndContactMe = userService.responseCustomObjectListDemo();
        log.info("Response message: {}" + messageAndContactMe);
        log.info("Exit responseCustomObjectListDemo method");
        return ResponseEntity.ok().body(messageAndContactMe);
        // here you can return like that ->
        // return new ResponseEntity(dbStuNameAndClassList, HttpStatus.OK);
    }

    //build update user REST API
    //http:localhost:8080/api/users/1
    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
                                           @RequestBody User user) {
        log.info("Enter updateUser....");
        User updateUser = userService.updateUser(user, id);
        log.info("updateUser: " + updateUser);
        log.info(".....Exit updateUser.....");
        return new ResponseEntity<>(updateUser, HttpStatus.OK);

    }

    //build delete user REST API
    //http:localhost:8080/api/users/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {

        log.info("Enter deleteUser....");
        //delete user from DB.
        long deleteId = userService.deleteUser(id);
        log.info("Successfully deleted id: " + id);
        log.info(".....Exit deleteUser.....");
        return new ResponseEntity<>("user deleted successfully!", HttpStatus.OK);
    }

    /**
     * For Response...
     */
    @GetMapping("/responseSingleStringDb")
    public ResponseEntity<String> responseSingleStringDb(Long id) {
        try {

            log.info("Enter responseSingleStringDb method.....");
            String name = userService.responseSingleStringDb(id);
            log.info("Response data: {}", name);
            log.info("Exit responseSingleStringDb method.....");
            return ResponseEntity.ok().body(name);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/responseObjectDb")
    public ResponseEntity<User> responseObjectDb() {
        try {
            log.info("Enter responseObjectDb method....");
            User user = userService.responseObjectDb();
            log.info("Response data: {}", user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/responseStringListDemo")
    public ResponseEntity responseStringListDemo(String occupation) {
        try {

            log.info("Enter responseStringListDemo method");
            List<String> dbStringList = userService.responseStringListDb(occupation);
            log.info("Response Datas: {}", dbStringList);
            log.info("Exit responseStringListDemo method");
            return ResponseEntity.ok().body(dbStringList);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/responseObjectListDb")
    public ResponseEntity responseObjectListDb(@RequestParam String occupation) {
        try {
            log.info("Enter responseObjectListDb method...");
            List<User> userList = userService.responseObjectListDemo(occupation);
            log.info("Response datas: {}", userList);
            log.info("Exit responseObjectListDB methods....");
            return ResponseEntity.ok().body(userList);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/responseNestedObjectDemo")
    public ResponseEntity responseNestedObjectDemo() {
        try {
            log.info("Enter responseNestedObjectDemo method");

            ResponseObject responseObject = userService.responseNestedObjectDemo();
            log.info("Response data: {}", responseObject);

            log.info("Exit responseNestedObjectDemo method");
            return ResponseEntity.ok().body(responseObject);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();

        }
    }

    /**
     * for request..
     */

    @PostMapping("/requestBodyObjectDb")
    public ResponseEntity requestBodyObjectDemo(@RequestBody User user) {
        try {
            log.info("Enter requestBodyObjectDemo method....");
            log.info("Request body data: {}", user);
            User data = userService.requestBodyObjectDb(user);
            log.info("Exit requestBodyObjectDemo...");
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/requestBodyObjectListDb")
    public ResponseEntity requestBodyObjectList(@RequestBody UserListObject userListObject) {

        try {
            log.info("Enter requestBodyObjectList....");
            log.info("request body object list,userListObject: {}", userListObject);
        userService.requestBodyObjectListDb(userListObject);
            log.info("Exit requestBodyObjectList...");
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }


    @GetMapping("/requestPathVariableById/{id}")
    public ResponseEntity requestPathVariableById(@PathVariable Long id) {
        try {
            log.info("Enter requestPathVariableByFindAll....");
            log.info("requestPathVariableByFindAll,id:{}", id);
            User user = userService.requestPathVariableIntegerDb(id);
            log.info("Exit requestPathVariableByFindAll.....");
            return ResponseEntity.ok().body(user);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/requestFromHeaderDemo")
    public ResponseEntity requestFromHeaderDemo(@RequestHeader Map<String, String> allHeadersMap) {

        try {
            log.info("Enter requestFromHeaderDemo method....");
            log.info("request headers value, headers: {}", allHeadersMap);
            userService.requestFromHeaderDemo(allHeadersMap);
            log.info("Exit requestFromHeaderDemo method....");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/requestFromHeaderAndRequestBodyDb")
    public ResponseEntity requestFromHeaderAndRequestBodyDb(@RequestHeader Map<String, String> allHeadersMap,
                                                            @RequestBody User user) {

        try {
            log.info("Enter requestFromHeaderAndRequestBodyDb method....");
            log.info("request headers value, headers: {}", allHeadersMap);
            log.info("request body data, user: {}", user);
            if (!user.equals(null)) {
                userService.requestFromHeaderAndRequestBodyDb(allHeadersMap, user);
                log.info("Exit requestFromHeaderAndRequestBodyDb method...");
                return ResponseEntity.ok().build();
            } else {
                log.warn("Something Wrong!");
            }
            log.info("Exit requestFromHeaderAndRequestBodyDb...");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }


    }

    @PostMapping("/requestFileUpload")
    public ResponseEntity<Object> requestFileUpload(@RequestParam("file") MultipartFile multipartFile) {

        try {
            log.info("Enter requestFileUploadDemo method");

            if (multipartFile != null && !multipartFile.getOriginalFilename().trim().equals("")) {

                log.info("request file name: {}", multipartFile.getOriginalFilename());
                log.info("request file content type: {}", multipartFile.getContentType());

                File file = new File(multipartFile.getOriginalFilename());
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(multipartFile.getBytes());
                fos.close();

                userService.requestFileUploadDemo(file, multipartFile.getOriginalFilename());
            } else {
                log.warn("File is empty");
            }

            log.info("Exit requestFileUploadDemo method");
//
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("/requestFromHeaderAndListFileUploadDemo")
    // request multiple files
    public ResponseEntity<Object> requestFromHeaderAndListFileUploadDemo(@RequestHeader Map<String, String> allHeadersMap,
                                                                         @RequestParam("files") List<MultipartFile> files){

        try{
            log.info("Enter requestFromHeaderAndListFileUploadDemo method");
            log.info("request headers value, headers: {}", allHeadersMap);

            if(files!=null && !files.isEmpty()) {

                for (MultipartFile file : files) {

                    log.info("request file name: {}", file.getOriginalFilename());
                    log.info("request file name: {}", file.getContentType());
                }

                userService.requestFromHeaderAndListFileUploadDemo(files);

            }else {
                log.warn("File is empty");
            }

            log.info("Exit requestFromHeaderAndBulkFileUploadDemo method");
//            return ResponseEntity.ok().body(dbStudent); // if need to return data as body
            return ResponseEntity.ok().build();

        }catch(Exception e){
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /** for request and response..*/

    @GetMapping("/requestPathVariableAndResponseObjectDb/{occupation}")
    public ResponseEntity requestPathVariableAndResponseObjectDb(@PathVariable String occupation) {
        try {
            log.info("Enter requestPathVariableAndResponseObjectDb method...");
            log.info("request path variable value, occupation: {}", occupation);

            if (occupation != null && !occupation.trim().equals("")) {
              List<User> userList = userService.requestPathVariableAndResponseObjectDemo(occupation);
              log.info("userList: {}",userList);
                return ResponseEntity.ok().body(userList);
            } else {
                log.info("Empty");
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}


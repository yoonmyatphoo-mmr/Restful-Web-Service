package com.ymp.restfulwebservice.service;

import com.ymp.restfulwebservice.model.ResponseMessage;
import com.ymp.restfulwebservice.model.User;
import com.ymp.restfulwebservice.model.Request.UserListObject;
import com.ymp.restfulwebservice.model.Response.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.service.UserService
 */
public interface UserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    User updateUser(User user, long id);
    long deleteUser(long id);
    List<ResponseMessage> responseCustomObjectListDemo();


    /** for response **/

    String responseSingleStringDb(Long id);
    User responseObjectDb();
    List<String> responseStringListDb(String occupation);
    List<User> responseObjectListDemo(String occupation);
    ResponseObject responseNestedObjectDemo();


    /** for request **/

    User requestBodyObjectDb(User user);
    void requestBodyObjectListDb(UserListObject userListObject);
    User requestPathVariableIntegerDb(Long id);
    void requestFromHeaderDemo(Map<String, String> allHeadersMap);
    void requestFromHeaderAndRequestBodyDb(Map<String, String> allHeadersMap, User user);
    void requestFileUploadDemo(File file, String name);

    void requestFromHeaderAndListFileUploadDemo(List<MultipartFile> files) throws IOException;



    /**
     * for both request and response
     *
     * @return
     **/

    List<User> requestPathVariableAndResponseObjectDemo(String occupation);


}

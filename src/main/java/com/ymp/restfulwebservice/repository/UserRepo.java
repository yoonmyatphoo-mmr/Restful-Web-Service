package com.ymp.restfulwebservice.repository;


import com.ymp.restfulwebservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Yoon Myat Phoo
 * @created: 23/10/2022
 * @project: restful-web-service
 * @package: com.ymp.restfulwebservice.repository.UserRepo
 */

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    @Query("SELECT u.name FROM User u WHERE u.id = :userId")
    String findUserNameById(@Param("userId") Long userId);

    @Query("SELECT u.name FROM User u WHERE u.occupation =:occupation")
    List<String> findUserNameByOccupation(@Param("occupation") String occupation);

   /* @Query("SELECT u FROM User u WHERE u.occupation =:occupation")
    List<User> findUserObjectByOccupation(@Param("occupation") String occupation);*/
   List<User> findAllByOccupation(String occupation);
    List<User> findUserByOccupation(String occupation);


}

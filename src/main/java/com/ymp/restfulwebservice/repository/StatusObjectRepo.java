package com.ymp.restfulwebservice.repository;

import com.ymp.restfulwebservice.model.Response.StatusObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusObjectRepo extends JpaRepository<StatusObject,Long> {

}

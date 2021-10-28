package com.myapp.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Access;


@Repository
public interface AccessRepository extends CrudRepository<Access, Integer> {
 
    
	Optional <Access> findByEmail(String email);
}
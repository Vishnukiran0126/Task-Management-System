package com.example.Task.Specification;

import com.example.Task.entites.Task;
import com.example.Task.entites.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> getAllUsers(String userName, String userEmail){
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{

            List<Predicate> predicateList=new ArrayList<>();
            if(userName!=null && !userName.isEmpty()){
                predicateList.add(cb.like(cb.lower(root.get("name")),"%"+userName.toLowerCase()+"%"));
            }
            if(userEmail!=null && !userEmail.isEmpty()){
                predicateList.add(cb.like(cb.lower(root.get("email")),"%"+userEmail.toLowerCase()+"%"));
            }

            return cb.and(predicateList.toArray(new Predicate[0]));

        };//closing the return statement after returning specification form lambda expression

    }
}

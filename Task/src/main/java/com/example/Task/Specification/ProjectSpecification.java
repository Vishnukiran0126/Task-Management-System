package com.example.Task.Specification;

import com.example.Task.entites.Project;
import com.example.Task.entites.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {

    public static Specification<Project> getAllProjects(String projectName, String createdBy){

        return (Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb)->{
           List<Predicate> predicates=new ArrayList<>();
//           if(projectName!=null && createdBy!=null) {
//               predicates.add(cb.and(
//                       cb.equal(root.get("name"), projectName),
//                       cb.equal(root.get("createdBy").get("name"), createdBy)
//               ));
//           }
           if(projectName!=null && !projectName.isEmpty()){
               predicates.add(cb.like(cb.lower(root.get("name")),"%"+projectName.toLowerCase()+"%"));
           }
           if(createdBy!=null && !createdBy.isEmpty()){
               predicates.add(cb.equal(root.get("createdBy").get("name"),createdBy));
           }
           if(predicates.isEmpty()){
               return cb.conjunction(); // nofilters case
           }

           return cb.and(predicates.toArray(new Predicate[0]));

        };
    }
}

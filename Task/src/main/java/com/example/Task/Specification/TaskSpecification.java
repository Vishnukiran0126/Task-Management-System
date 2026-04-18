package com.example.Task.Specification;

import com.example.Task.entites.Task;
import com.example.Task.enums.TaskPriority;
import com.example.Task.enums.TaskStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> getTasks(TaskStatus status, TaskPriority priority, LocalDate dueDate,LocalDate dueDateFrom,LocalDate dueDateTo,String name,Long projectId){


        //Specifcation is a Functional Interface meaning only one method to implement
        //return an anonymous object(it can use an interface name)
        //this can be substituted by a lambda expression (root,query,criteriaBuilder)->
        return new Specification<Task>(){
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


                List<Predicate> predicates =new ArrayList<>();

                if(status!=null){
                    predicates.add(cb.equal(root.get("status"),status)); //root.get("columnName") in the current entity
                }

                if(priority!=null){
                    predicates.add(cb.equal(root.get("priority"),priority)); //root.gte is getting column
                }

                if(dueDate!=null){
                    predicates.add(cb.equal(root.get("dueDate"),dueDate));
                }
                if (dueDateFrom != null && dueDateTo != null) {
                    predicates.add(cb.between(root.get("dueDate"), dueDateFrom, dueDateTo));
                } else {
                    if (dueDateFrom != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("dueDate"), dueDateFrom));
                    }
                    if (dueDateTo != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("dueDate"), dueDateTo));
                    }
                }

                if(name!=null){
                    predicates.add(cb.like(cb.lower(root.
                            join("assignedUser").get("name")),"%"+name.toLowerCase()+"%"));
                }

                if(projectId!=null){
                    predicates.add(cb.equal(root.join("project").get("id"),projectId));
                }

//                if(projectId!=null){
//                    predicates.add(cb.equal(root.get("pr")))
//                }
                //creating an array of size 0 with type as Predicate java will automatically create crct size array
                return cb.and(predicates.toArray(new Predicate[0]));// using AND operation on all available conditions and filtering
            }

        };

    }
}

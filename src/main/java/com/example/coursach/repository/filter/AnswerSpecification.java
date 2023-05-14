package com.example.coursach.repository.filter;

import com.example.coursach.entity.Answer;
import com.example.coursach.entity.Answer_;
import com.example.coursach.entity.Booking_;
import com.example.coursach.entity.User_;
import com.example.coursach.entity.Work_;
import com.example.coursach.entity.enums.TimeStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public class AnswerSpecification {

    public static Specification<Answer> creatAnswerSpecification(Long workId, String username, TimeStatus status) {

        return (root, query, cb) -> {
            Predicate userPred = null;
            Predicate equal = null;
            Predicate main = null;
            Predicate byWorkId = cb.equal(root.get(Answer_.WORK).get(Work_.ID), workId);
            if (status != null) {
                equal = cb.equal(root.get(Answer_.STATUS), status);
                main = cb.and(equal, byWorkId);
            }
            if (username != null && !username.isEmpty()) {
                List<String> split = List.of(username.split(" "));
                String lastname = null;
                if (split.size() >= 2) {
                    lastname = split.get(1);
                    userPred = cb.and(
                            cb.like(cb.lower(root.get(Booking_.USER).get(User_.FIRSTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"),
                            cb.like(cb.lower(root.get(Booking_.USER).get(User_.LASTNAME.toLowerCase())),
                                    Optional.ofNullable(lastname).orElse(
                                            username).toLowerCase() + "%"));
                }else{
                    userPred = cb.or(
                            cb.like(cb.lower(root.get(Booking_.USER).get(User_.FIRSTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"),
                            cb.like(cb.lower(root.get(Booking_.USER).get(User_.LASTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"));
                }
                main = cb.and(userPred, byWorkId);
            }

            if (equal != null && userPred != null) {
                return cb.and(equal, userPred, byWorkId);
            } else
                return main;
        };
    }

}

package com.example.coursach.repository.filter;

import com.example.coursach.entity.Role_;
import com.example.coursach.entity.User;
import com.example.coursach.entity.User_;
import com.example.coursach.entity.enums.UserRole;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public class UserSpecification {

    public static Specification<User> createBookingSpecification(String username) {

        return (root, query, cb) -> {
            Predicate userPred = null;

            if (username != null && !username.isEmpty()) {
                List<String> split = List.of(username.split(" "));
                String lastname = null;
                if (split.size() >= 2) {
                    lastname = split.get(1);
                    userPred = cb.and(
                            cb.like(cb.lower(root.get(User_.FIRSTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"),
                            cb.like(cb.lower(root.get(User_.LASTNAME.toLowerCase())),
                                    Optional.ofNullable(lastname).orElse(
                                            username).toLowerCase() + "%"));
                } else {
                    userPred = cb.or(
                            cb.like(cb.lower(root.get(User_.FIRSTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"),
                            cb.like(cb.lower(root.get(User_.LASTNAME.toLowerCase())),
                                    Optional.ofNullable(split.get(0)).orElse(
                                            username).toLowerCase() + "%"));
                }

                return cb.and(userPred);
            }
            return  cb.like(cb.lower(root.get(User_.FIRSTNAME.toLowerCase())),
                            Optional.ofNullable(username).orElse(
                                    username).toLowerCase() + "%");
        };
    }

}

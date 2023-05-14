package com.example.coursach.repository.filter;

import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Booking_;
import com.example.coursach.entity.Course_;
import com.example.coursach.entity.User_;
import com.example.coursach.entity.enums.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookingSpecification {

    public static Specification<Booking> createBookingSpecification(String title, String username, BookingStatus status) {


        return (root, query, cb) -> {
            Predicate coursePred = null;
            Predicate userPred = null;
            Predicate equal = cb.equal(root.get(Booking_.STATUS), status);
            Predicate main = equal;
            if (title != null && !title.isEmpty()) {
                coursePred = cb.like(cb.lower(root.get(Booking_.COURSE).get(Course_.TITLE.toLowerCase())), title.toLowerCase() + "%");
                main = cb.and(equal, coursePred);
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
                main = cb.and(equal, userPred);
            }

            if (coursePred != null && userPred != null) {
                return cb.and(equal, coursePred, userPred);
            }else
                return main;
        };
    }

}

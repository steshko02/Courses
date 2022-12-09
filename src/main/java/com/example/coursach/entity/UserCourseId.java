package com.example.coursach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseId implements Serializable {
    private static final long serialVersionUID = 7931703347956291301L;
    @Column(name = "userid")
    private Long usersId;

    @Column(name = "coursesid")
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCourseId that = (UserCourseId) o;
        return Objects.equals(usersId, that.usersId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, courseId);
    }
}

package com.example.coursach.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_courses")
public class CourseUser {
    @EmbeddedId
    private UserCourseId id;

    @JoinColumn(name = "roleid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;

}

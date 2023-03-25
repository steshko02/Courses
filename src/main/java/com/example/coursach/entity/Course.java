package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.converters.CourseTypeConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.entity.enums.CourseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Convert(converter = CourseStatusConverter.class)
    @Column(name = "status", nullable = false)
    private TimeStatus status;

    @Convert(converter = CourseTypeConverter.class)
    @Column(name = "type", nullable = false)
    private CourseType type;

    @Column(name = "size")
    private Integer size;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_start", nullable = false)
    private LocalDateTime start;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_end", nullable = false)
    private LocalDateTime end;

    @OneToMany(mappedBy = "course")
    private List<CourseUser> joinUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<Lesson> lessons;
}

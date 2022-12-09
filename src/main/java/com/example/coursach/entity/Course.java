package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.converters.CourseTypeConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.entity.enums.CourseType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

}

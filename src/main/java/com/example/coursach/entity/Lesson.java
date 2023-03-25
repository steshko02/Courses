package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.enums.TimeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "sourceurl")
    private String sourceUrl;

    @Column(name = "duration")
    private Integer duration;

    @JoinColumn(name = "resourceid")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private Resource resource;

    @JoinColumn(name = "courseid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @Convert(converter = CourseStatusConverter.class)
    @Column(name = "status", nullable = false)
    private TimeStatus status;
}

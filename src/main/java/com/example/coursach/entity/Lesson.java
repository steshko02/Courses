package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.enums.TimeStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @Convert(converter = CourseStatusConverter.class)
    @Column(name = "status", nullable = false)
    private TimeStatus status;
}

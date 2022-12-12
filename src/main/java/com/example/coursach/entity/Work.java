package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.TimeStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "sourceurl")
    private String sourceUrl;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @JoinColumn(name = "lessonid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Lesson lesson;

    @Convert(converter = CourseStatusConverter.class)
    @Column(name = "status", nullable = false)
    private TimeStatus status;
}

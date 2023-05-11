package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

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

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Convert(converter = CourseStatusConverter.class)
    @Column(name = "status", nullable = false)
    private TimeStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Resource> resources;

    @OneToOne (cascade = CascadeType.PERSIST)
    private Lesson lesson;

    @PreRemove
    public void remove(){
        lesson.setWork(null);
    }

}

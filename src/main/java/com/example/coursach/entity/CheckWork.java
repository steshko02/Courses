package com.example.coursach.entity;

import com.example.coursach.entity.converters.CourseStatusConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.TimeStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "check_work")
public class CheckWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "mark")
    private Integer mark;

    @JoinColumn(name = "answer_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User mentor;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_creation") 
    private LocalDateTime dateCreation;
}

package com.example.coursach.entity;

import com.example.coursach.entity.converters.BookingStatusConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.BookingStatus;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @JoinColumn(name = "courseid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Course course;

    @JoinColumn(name = "status")
    @Convert(converter = BookingStatusConverter.class)
    private BookingStatus status;
}

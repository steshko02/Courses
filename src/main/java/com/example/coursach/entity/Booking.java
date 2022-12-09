package com.example.coursach.entity;

import com.example.coursach.entity.converters.BookingStatusConverter;
import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.BookingStatus;
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
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
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

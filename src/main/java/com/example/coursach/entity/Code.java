package com.example.coursach.entity;

import com.example.coursach.entity.converters.LocalDateTimeConverter;
import com.example.coursach.entity.enums.CodeType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "otp")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "codetype")
    @Enumerated(EnumType.STRING)
    private CodeType type;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @JoinColumn(name = "userid", unique = true)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User requested;
}

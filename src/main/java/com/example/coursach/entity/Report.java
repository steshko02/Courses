package com.example.coursach.entity;

import com.example.coursach.entity.converters.ReportStatusConverter;
import com.example.coursach.entity.enums.ReportStatus;
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
@Table(name = "works_reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @JoinColumn(name = "lectorid")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User lector;

    @JoinColumn(name = "workid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Work work;

    @Column(name = "sourceurl")
    private String answerUrl;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private Integer rating;

    @Convert(converter = ReportStatusConverter.class)
    @Column(name = "status")
    private ReportStatus status;
}

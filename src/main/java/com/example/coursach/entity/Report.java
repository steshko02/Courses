package com.example.coursach.entity;

import com.example.coursach.entity.converters.ReportStatusConverter;
import com.example.coursach.entity.enums.ReportStatus;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

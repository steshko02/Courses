package com.example.coursach.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class    Profile {

    @Id
    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "photourl")
    private String photoUrl;

    @Column(name = "department")
    private String department;

    @Column(name = "jobtitle")
    private String jobTitle;

    @Column(name = "other")
    private String other;

    @Column(name = "githuburl")
    private String githubUrl;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "picture_format")
    private String pictureFormat;

    @MapsId
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}

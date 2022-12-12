//package com.example.coursach.entity;
//
//import com.example.coursach.entity.converters.LocalDateTimeConverter;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Convert;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "events")
//public class Event {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "subject")
//    private String subject;
//
//    @Convert(converter = LocalDateTimeConverter.class)
//    @Column(name = "date", nullable = false)
//    private LocalDateTime date;
//
//    @Column(name = "message")
//    private String message;
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE})
//    @JoinTable(
//            name = "users_events",
//            joinColumns = @JoinColumn(name = "eventsid"),
//            inverseJoinColumns = @JoinColumn(name = "userid")
//    )
//    private Set<User> users;
//
//}

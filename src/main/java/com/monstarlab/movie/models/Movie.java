package com.monstarlab.movie.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.monstarlab.movie.models.enums.MovieType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private MovieType type;

    @Column(name = "release_date")
    private Timestamp releaseDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonIgnore
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private Timestamp updatedAt;

    @Transient
    @JsonIgnore
    private boolean hasNextPage;
}

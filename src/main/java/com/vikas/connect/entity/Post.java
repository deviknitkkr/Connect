package com.vikas.connect.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, length = 36)
    private String id;
    private String title;
    private String description;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<User> taggedPeople;

    @OneToMany(mappedBy = "post")
    private Set<Reaction> reactions;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}



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
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, length = 36)
    private String id;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String image;

    @ManyToOne
    private Post post;

    @OneToOne
    private Comment parentComment;

    @ManyToOne
    private User commenter;

    @OneToMany(mappedBy = "comment")
    private Set<Reaction> reactions;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}

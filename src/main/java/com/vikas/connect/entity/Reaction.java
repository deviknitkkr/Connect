package com.vikas.connect.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, length = 36)
    private String id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User reactor;

    private ReactionType reactionType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    enum ReactionType {
        LIKE, LOVE, INSPIRE
    }

}

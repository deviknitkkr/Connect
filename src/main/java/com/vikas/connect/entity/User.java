package com.vikas.connect.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, length = 36)
    private String id;

    @Column(length = 50,unique = true,nullable = false)
    private String email;
    private String password;

    @Column(columnDefinition = "text")
    private String profilePic;
    private Boolean expired;
    private Boolean locked;
    private Boolean enabled;
    private Boolean credentialExpired;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> friends;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<Post> posts;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

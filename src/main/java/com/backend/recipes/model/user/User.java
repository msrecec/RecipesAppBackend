package com.backend.recipes.model.user;

import com.backend.recipes.model.authority.Authority;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = "id")
})
@ToString
public class User {
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id")
    @Id
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @ManyToMany(targetEntity = Authority.class)
    @JoinTable(
            name="user_authority",
            schema = "public",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id") }
    )
    @ToString.Exclude
    private List<Authority> authorities;
}

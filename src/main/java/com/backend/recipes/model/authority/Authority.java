package com.backend.recipes.model.authority;

import com.backend.recipes.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "authority")
public class Authority {
    @SequenceGenerator(name = "authority_sequence", sequenceName = "authority_sequence", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_sequence")
    @Column(name = "id")
    @Id
    private Long id;
    @Column(name="name")
    private String name;
    @ManyToMany(targetEntity = User.class, mappedBy = "authorities")
    private List<User> users;
}

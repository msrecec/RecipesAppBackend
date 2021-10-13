package com.backend.recipes.model.userAuthority;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_authority")
public class UserAuthority {
    @SequenceGenerator(name = "user_authority_sequence", sequenceName = "user_authority_sequence", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_authority_sequence")
    @Column(name = "id")
    @Id
    private Long id;
}

package com.backend.recipes.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
}

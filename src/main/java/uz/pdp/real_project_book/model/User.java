package uz.pdp.real_project_book.model;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User{
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role="user";
    private boolean active = true;
}

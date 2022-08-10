package uz.pdp.real_project_book.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private String username;
    private String email;
    private String password;
}

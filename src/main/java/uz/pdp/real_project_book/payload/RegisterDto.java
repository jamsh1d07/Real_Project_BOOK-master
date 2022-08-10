package uz.pdp.real_project_book.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}

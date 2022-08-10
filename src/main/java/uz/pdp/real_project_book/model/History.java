package uz.pdp.real_project_book.model;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class History {
    private Long id;
    private Integer userId;
    private Date createdAt;
    private String action;
    private String object;
    private String objectName;

}

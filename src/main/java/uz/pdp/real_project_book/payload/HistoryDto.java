package uz.pdp.real_project_book.payload;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HistoryDto {

    private Integer userId;
    private String action;
    private String object;
    private String objectName;

}

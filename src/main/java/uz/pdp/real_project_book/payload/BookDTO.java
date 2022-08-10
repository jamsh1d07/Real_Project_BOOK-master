package uz.pdp.real_project_book.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private String name;
    private Double price;
    private Integer categoryId;
}

package uz.pdp.real_project_book.model;


import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Book {

    private Integer id ;
    private String name;
    private Double price;
    private UUID code ;
    private Integer categoryId;

    public Book(String name, Double price, Integer categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

}

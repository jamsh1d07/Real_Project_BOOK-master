package uz.pdp.real_project_book.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Category {
     private Integer id;
     private String name;
     private boolean active = true;
}

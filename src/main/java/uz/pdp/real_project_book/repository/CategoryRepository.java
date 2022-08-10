package uz.pdp.real_project_book.repository;

import uz.pdp.real_project_book.model.Category;

import java.util.List;

public interface CategoryRepository {
    int save(Category category);
    int update(Category category,Integer id);
    Category findById(Integer id);
    List<Category> findAll();
    int delete(Integer id);
}

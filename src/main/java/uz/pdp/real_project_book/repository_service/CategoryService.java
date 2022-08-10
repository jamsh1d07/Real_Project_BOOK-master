package uz.pdp.real_project_book.repository_service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.real_project_book.model.Category;
import uz.pdp.real_project_book.repository.CategoryRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryService implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Category category) {
        return jdbcTemplate.update("INSERT into \"Category\"(name,active ) values (?,?)",
                category.getName(),true);
    }

    @Override
    public int update(Category category, Integer id) {
        return jdbcTemplate.update("Update \"Category\" SET name =? WHERE id = ?",
                category.getName(), id);
    }

    @Override
    public Category findById(Integer id) {
        Category category = jdbcTemplate.queryForObject("SELECT * FROM \"Category\" WHERE id=?",
                BeanPropertyRowMapper.newInstance(Category.class), id);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from \"Category\" where active = true",
                BeanPropertyRowMapper.newInstance(Category.class));
    }

    @Override
    public int delete(Integer id) {
        return jdbcTemplate.update("Update \"Category\" SET active = false WHERE id = ?",
                id);
    }


}

package uz.pdp.real_project_book.repository_service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.real_project_book.model.Book;
import uz.pdp.real_project_book.payload.BookDTO;
import uz.pdp.real_project_book.repository.BookRepository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookService implements BookRepository {

private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(BookDTO dto) {
        return jdbcTemplate.update("insert into \"Book\"(name, price, code, category_id, active) VALUES (?,?,?,?,?)",
                dto.getName(),dto.getPrice(), UUID.randomUUID(),dto.getCategoryId(),true);
    }

    @Override
    public int update(BookDTO dto, Integer id) {

        return jdbcTemplate.update("update \"Book\" set name = ? ,price = ? , category_id = ?  where id = ?",
                dto.getName(), dto.getPrice(), dto.getCategoryId(),id);
    }

    @Override
    public Book findById(Integer id) {
        return jdbcTemplate.queryForObject("select * from \"Book\" where id = ?", BeanPropertyRowMapper.newInstance(Book.class),id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select * from \"Book\" where active = true;", BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public int delete(Integer id) {
        return jdbcTemplate.update("update \"Book\" set active = false where id = ?",id);
    }
}

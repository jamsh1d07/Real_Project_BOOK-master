package uz.pdp.real_project_book.repository_service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.real_project_book.model.Book;
import uz.pdp.real_project_book.model.User;
import uz.pdp.real_project_book.payload.UserDto;
import uz.pdp.real_project_book.repository.UserRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserService implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(UserDto dto) {
        return jdbcTemplate.update("insert into users (username,email,password,role,active) values (?,?,?,?,?)",
                dto.getUsername(), dto.getEmail(), dto.getPassword(),"user",true);
    }

    @Override
    public int update(UserDto dto, Integer id) {
        return jdbcTemplate.update("update users set username = ? ,email =? ,password = ?  where id = ?",
                dto.getUsername(), dto.getEmail(), dto.getPassword(),id);
    }

    @Override
    public User findById(Integer id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", User.class,id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override  // update query bo'ladi
    public int delete(Integer id) {
        return jdbcTemplate.update("update users set active = false where id = ?",id);
    }
}

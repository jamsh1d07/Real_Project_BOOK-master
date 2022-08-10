package uz.pdp.real_project_book.repository;

import uz.pdp.real_project_book.model.Book;
import uz.pdp.real_project_book.model.User;
import uz.pdp.real_project_book.payload.BookDTO;
import uz.pdp.real_project_book.payload.UserDto;

import java.util.List;

public interface UserRepository {
    int save(UserDto dto);
    int update(UserDto dto,Integer id);
    User findById(Integer id);
    List<User> findAll();
    int delete(Integer id);
}

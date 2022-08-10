package uz.pdp.real_project_book.repository;

import uz.pdp.real_project_book.model.Book;
import uz.pdp.real_project_book.payload.BookDTO;

import java.util.List;

public interface BookRepository {
    int save(BookDTO dto);
    int update(BookDTO dto,Integer id);
    Book findById(Integer id);
    List<Book> findAll();
    int delete(Integer id);
}

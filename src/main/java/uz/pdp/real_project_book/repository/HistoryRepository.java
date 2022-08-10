package uz.pdp.real_project_book.repository;

import uz.pdp.real_project_book.model.History;
import uz.pdp.real_project_book.payload.HistoryDto;

import java.util.List;

public interface HistoryRepository {
    int save(HistoryDto dto);
    int update(HistoryDto dto,Integer id);
    History findById(Integer id);
    List<History> findAll();
    int delete(Integer id);  // bu odatda delete qilinmaydi
}

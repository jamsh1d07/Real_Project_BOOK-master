package uz.pdp.real_project_book.repository_service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.real_project_book.model.History;
import uz.pdp.real_project_book.payload.HistoryDto;
import uz.pdp.real_project_book.repository.HistoryRepository;

import java.util.Date;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class HistoryService implements HistoryRepository {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(HistoryDto dto) {
        return jdbcTemplate.update("insert into history(user_id, created_at, action, object, \"objectName\") values (?,?,?,?,?)",
                dto.getUserId(),new Date(), dto.getAction(), dto.getObject(), dto.getObjectName());
    }


    @Override
    public List<History> findAll() {
        return jdbcTemplate.query("select *  from history", BeanPropertyRowMapper.newInstance(History.class));
    }

    //==========================================Bulardan foydalanilmaydi=============
    @Override
    public int update(HistoryDto dto, Integer id) {
        return 0;
    }

    @Override
    public History findById(Integer id) {
        return null;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }
}

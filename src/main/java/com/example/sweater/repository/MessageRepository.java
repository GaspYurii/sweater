package com.example.sweater.repository;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import com.example.sweater.model.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("select new com.example.sweater.model.dto.MessageDto(" +
            "m, " +
            "count(ml), " +
            "sum(case when ml = :user then 1 else 0 end) > 0" +
            ")" +
            " from Message m left join m.likes ml " +
            "group by m " +
            "order by m.id")
    Page<MessageDto> findAll(Pageable pageable, @Param("user") User user);

    @Query("select new com.example.sweater.model.dto.MessageDto(" +
            "m, " +
            "count(ml), " +
            "sum(case when ml = :user then 1 else 0 end) > 0" +
            ")" +
            " from Message m left join m.likes ml " +
            "where m.tag = :tag " +
            "group by m " +
            "order by m.id")
    Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable , @Param("user") User user);

    @Query("from Message as m where m.author = :author")
    Page<MessageDto> findAllByUser(Pageable pageable, @Param("author") User author);
}

package com.example.sweater.repository;

import com.example.sweater.model.Message;
import com.example.sweater.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Page<Message> findAll(Pageable pageable);

    Page<Message> findByTag(String tag, Pageable pageable);

    @Query("from Message as m where m.author = :author")
    Page<Message> findAllByUser(Pageable pageable, @Param("author") User author);
}

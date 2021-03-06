package com.venkat.myspring.dao;

import com.venkat.myspring.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long>, ExtendedRepository<Topic, Long> {
    List<Topic> findByDescriptionLikeIgnoreCase(String description);
}

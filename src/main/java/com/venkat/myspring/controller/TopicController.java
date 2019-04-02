package com.venkat.myspring.controller;

import com.venkat.myspring.dao.TopicRepository;
import com.venkat.myspring.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping("/all_topics")
    public List<Topic> getAll(){
        return topicRepository.findAll();
    }

    @GetMapping("/topics")
    public List<Topic> searchTopics(@RequestParam("searchString") String searchString){
        return topicRepository.findByDescriptionLikeIgnoreCase("%" + searchString + "%");
    }
}

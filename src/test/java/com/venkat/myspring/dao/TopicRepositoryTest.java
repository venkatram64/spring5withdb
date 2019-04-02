package com.venkat.myspring.dao;

import com.venkat.myspring.model.Topic;
/*import org.junit.Test;
import org.junit.runner.RunWith;*/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicRepositoryTest {

    /*@Autowired
    private TestRestTemplate testRestTemplate;*/
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testBasicSearch(){
        Topic[] topics = this.testRestTemplate.getForObject("/topics?searchString=spring", Topic[].class);
        assertTrue(topics.length >=2);
        for(int i = 0; i < topics.length; i++){
            assertTrue(topics[i].getName() != null);
        }

    }
}

package com.venkat.myspring.dao;

import com.venkat.myspring.model.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.client.HttpClient;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicRepositoryTest {

    @Before
    public void setup() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    /*@Autowired
    private TestRestTemplate testRestTemplate;*/
    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void testBasicSearch(){
        String resourceUrl = "http://localhost:8080";
        Topic[] topics = this.testRestTemplate.getForObject(resourceUrl + "/topics?searchString=spring", Topic[].class);
        assertTrue(topics.length >=2);
        for(int i = 0; i < topics.length; i++){
            assertTrue(topics[i].getName() != null);
        }
    }

    @Test
    public void testBasicPost() {
        Topic topic = new Topic();
        String name = "The new topic";
        String desc = "New topic description";
        String tfield1 = "Text field 1";
        String tfield2 = "Text field 2";
        topic.setName(name);
        topic.setDescription(desc);
        topic.setTextField1(tfield1);
        topic.setTextField2(tfield2);
        String resourceUrl = "http://localhost:8080";
        Topic createdTopic = this.testRestTemplate.postForObject(resourceUrl + "/topics", topic, Topic.class);
        assertTrue(createdTopic.getId() != null);
        assertTrue(createdTopic.getName().equals(name));
        assertTrue(createdTopic.getDescription().equals(desc));
        assertTrue(createdTopic.getTextField1().equals(tfield1));
        assertTrue(createdTopic.getTextField2().equals(tfield2));
    }

    @Test
    public void testBasicGet() {
        Topic topic = doGetTopic(Long.valueOf(2));
        assertTrue(topic.getName().contains("Spring"));
    }

    @Test
    public void testBasicPut() {

        Topic topic = new Topic();
        String name = "A new topic to update";
        topic.setName(name);
        topic.setDescription("Description");
        topic.setTextField1("Text field 1");
        topic.setTextField2("Text field 2");
        String resourceUrl = "http://localhost:8080";
        Topic createdTopic = this.testRestTemplate.postForObject(resourceUrl + "/topics", topic, Topic.class);

        String originalName = createdTopic.getName();

        topic = new Topic();
        topic.setId(createdTopic.getId());
        name = "Replaced Topic";
        topic.setName(name);

        // There no "putForObject" in RestTemplate
        HttpEntity<Topic> entity = new HttpEntity<Topic>(topic);
        ResponseEntity<Topic> updatedTopic = this.testRestTemplate.exchange(resourceUrl + "/topic/"+createdTopic.getId(), HttpMethod.PUT, entity, Topic.class);
        assertTrue(updatedTopic.getBody().getName().equals(name));

        // after replacement all other fields should be null
        topic = doGetTopic(Long.valueOf(createdTopic.getId()));
        assertTrue(topic.getName().equals(name));
        assertNull(topic.getDescription());
        assertNull(topic.getTextField1());
        assertNull(topic.getTextField2());

    }


    @Test
    public void testBasicPatch() {
        Topic topic = doGetTopic(Long.valueOf(1));

        String originalName = topic.getName();
        String changedName = "Changed Name";
        //topic.setName(changedName);

        Topic aNewTopic = new Topic();
        aNewTopic.setName(changedName);

        String resourceUrl = "http://localhost:8080";
        Topic updatedTopic = this.testRestTemplate.patchForObject(resourceUrl + "/topic/1", aNewTopic, Topic.class);
        assertTrue(updatedTopic.getName().equals(changedName));

        topic = doGetTopic(Long.valueOf(1));
        assertTrue(topic.getName().equals(changedName));

        // revert back to original name and double check
        aNewTopic.setName(originalName);
        updatedTopic = this.testRestTemplate.patchForObject(resourceUrl + "/topic/1", aNewTopic, Topic.class);
        assertTrue(updatedTopic.getName().equals(originalName));
        topic = doGetTopic(Long.valueOf(1));
        assertTrue(topic.getName().equals(originalName));

    }

    private Topic doGetTopic(Long id){
        String resourceUrl = "http://localhost:8080";
        Topic topic = this.testRestTemplate.getForObject(resourceUrl + "/topic/"+id, Topic.class);
        assertTrue(topic != null);
        assertTrue(topic.getId().equals(id));
        return topic;
    }
}

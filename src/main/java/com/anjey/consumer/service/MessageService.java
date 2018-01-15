package com.anjey.consumer.service;

import com.anjey.consumer.entity.Message;
import com.anjey.consumer.repository.MessageRepository;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("messageService")
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<String> findAll() {
        DBCollection coll= mongoTemplate.getCollection("message");
        DBCursor cursor = coll.find();
        ArrayList<String> list = new ArrayList<>();
        while(cursor.hasNext()){
            String msg = cursor.next().get("message").toString();
            list.add(msg);
        }
        cursor.close();

        return list;
    }

    @Transactional
    public void saveMessage(String message) {
        Message messageObj=new Message(message);
        messageRepository.insert(messageObj);
    }

//    Get message from queue
//    @JmsListener(destination = "Message_queue")
//    public void getMessage(String message) {
//        System.out.println("Received " + message);
//    }
}

package application.data.service;

import application.data.model.Cart;
import application.data.model.Message;
import application.data.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


@Service
public class MessageService  {
    @Autowired
    MessageRepository messageRepository;

    public  Message findOne(Integer id){
        return  messageRepository.findOne(id);
    }
    public void update(Message message) {
        messageRepository.save(message);
    }
    public  Page<Message> getAllSearch(Pageable pageable,  String keyword){
        return messageRepository.getAllSearch(pageable,keyword);
    }
    public Integer getTotalUnread(){
        return messageRepository.getTotalUnread();
    }


}

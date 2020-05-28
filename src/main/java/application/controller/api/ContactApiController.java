package application.controller.api;

import application.data.model.*;
import application.data.service.MessageService;
import application.model.api.BaseApiResult;
import application.model.dto.CartProductDTO;
import application.model.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/contact")
public class ContactApiController {
    @Autowired
    MessageService messageService;

    @PostMapping("/add")
    public BaseApiResult addContact( @Valid @RequestBody MessageDTO dto) {
        BaseApiResult result = new BaseApiResult();
        try{
            Message obj = new Message();
            obj.setContent(dto.getContent());
            obj.setTitle(dto.getTitle());
            obj.setEmail(dto.getEmail());
            obj.setCreatedDate(new Date());
            obj.setStatus(1);
            obj.setTrash(0);
            obj.setImportant(0);
            obj.setSent(0);
            messageService.update(obj);
            result.setMessage("We wil contact you as soon as possible!");
            result.setSuccess(true);
            return result;
        }catch (Exception e){
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return result;
        }
    }

}

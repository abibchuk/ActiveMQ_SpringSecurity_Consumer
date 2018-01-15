package com.anjey.consumer.controller;

import com.anjey.consumer.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import java.util.List;

/**
 * Created by Anjey on 21.12.2017.
 */

@Controller
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(com.anjey.consumer.controller.UserController.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/get_message", method = RequestMethod.GET)
    public String getPage() {
        return "get_message";
    }

    @RequestMapping(value = "/get_message", method = RequestMethod.POST)
    public ModelAndView getMessage() throws JMSException {
        ModelAndView model = new ModelAndView("get_message");
        try {
            String msg = jmsTemplate.receiveAndConvert().toString();
            model.addObject("msg", msg);
            messageService.saveMessage(msg);
        }
        catch (NullPointerException e){
            model.addObject("msg", "No message available");
        }
        return model;
    }

    @RequestMapping(value = "/show_message", method = RequestMethod.GET)
    public ModelAndView showMessage() {
        ModelAndView model = new ModelAndView("show_message");
        try {
            List list = messageService.findAll();
            model.addObject("list", list);
        } catch (NullPointerException e) {
            model.addObject("list", "No message available");
        }
        return model;
    }

}

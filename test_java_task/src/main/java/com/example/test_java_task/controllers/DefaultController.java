package com.example.test_java_task.controllers;

import com.example.test_java_task.model.pojo.User;
import com.example.test_java_task.model.request.UserRequest;
import com.example.test_java_task.service.HtmlService;
import com.example.test_java_task.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

    private  final HtmlService htmlService;
    private  final UserService userService;

    public DefaultController(HtmlService htmlService, UserService userService) {
        this.htmlService = htmlService;
        this.userService = userService;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index() {
        return htmlService.getIndexHtml();
    }

    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody UserRequest input)  {
        String result = userService.saveUser(input);
        if (result.equals("Done")){
            return new ResponseEntity<>("Done", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user_update")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest input)  {
        String result = userService.updateUser(input);
        if (result.equals("Done")){
            return new ResponseEntity<>("Done", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user")
    ResponseEntity<?> getUser(@RequestParam String name){
        User result = userService.getUser(name);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
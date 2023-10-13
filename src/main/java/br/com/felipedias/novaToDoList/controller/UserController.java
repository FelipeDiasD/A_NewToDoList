package br.com.felipedias.novaToDoList.controller;

import br.com.felipedias.novaToDoList.model.UserModel;
import br.com.felipedias.novaToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel user){
        UserModel returnedObj = userRepository.save(user);
        return ResponseEntity.ok().body(returnedObj);
    }
}

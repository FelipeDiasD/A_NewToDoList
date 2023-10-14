package br.com.felipedias.novaToDoList.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.felipedias.novaToDoList.model.UserModel;
import br.com.felipedias.novaToDoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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


        if(userRepository.findByUsername(user.getUsername()) != null){
            return ResponseEntity.badRequest().body("Usuário com este username já existe");
        }

        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12, user.getPassword().toCharArray());

        user.setPassword(passwordHashred);

        UserModel returnedObj = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedObj);
    }
}

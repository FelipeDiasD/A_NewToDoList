package br.com.felipedias.novaToDoList.controller;

import br.com.felipedias.novaToDoList.model.TaskModel;
import br.com.felipedias.novaToDoList.repository.TaskRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){

       var userId =  request.getAttribute("userId");
       taskModel.setUserId((UUID) userId);

       var currentDate = LocalDateTime.now();

       if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter((taskModel.getEndAt()))){
            return ResponseEntity.badRequest().body("Data de início/fim deve ser maior que a atual");
       }

       var createdTask = this.taskRepository.save(taskModel);

       return ResponseEntity.ok().body(createdTask);
    }


}

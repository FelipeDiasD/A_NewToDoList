package br.com.felipedias.novaToDoList.controller;

import br.com.felipedias.novaToDoList.model.TaskModel;
import br.com.felipedias.novaToDoList.repository.TaskRepository;
import br.com.felipedias.novaToDoList.utils.Utils;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){

       var userId =  request.getAttribute("userId");
       taskModel.setUserId((UUID) userId);

       var currentDate = LocalDateTime.now();

       if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter((taskModel.getEndAt()))){
            return ResponseEntity.badRequest().body("Data de início/fim deve ser maior que a atual");
       }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.badRequest().body("Data de início deve ser menor que a data de término");
        }

       var createdTask = this.taskRepository.save(taskModel);

       return ResponseEntity.ok().body(createdTask);
    }

    @GetMapping("/")
    public List<TaskModel> findAllTasks(HttpServletRequest request){
        var userId =  request.getAttribute("userId");
        var tasks = this.taskRepository.findByUserId((UUID)userId);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){

        var userId = request.getAttribute("userId");

        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null) {
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }

        if(!task.getUserId().equals(userId)){
            return ResponseEntity.badRequest().body("Usuário sem permissão para alterar esta tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var updatedTask = this.taskRepository.save(task);

        return ResponseEntity.ok().body(updatedTask);

    }


}

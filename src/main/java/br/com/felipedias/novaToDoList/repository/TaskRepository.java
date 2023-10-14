package br.com.felipedias.novaToDoList.repository;

import br.com.felipedias.novaToDoList.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

    List<TaskModel> findByUserId(UUID id);
    TaskModel findByIdAndUserId(UUID taskId, UUID userId);
}

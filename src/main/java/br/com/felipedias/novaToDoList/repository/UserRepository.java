package br.com.felipedias.novaToDoList.repository;

import br.com.felipedias.novaToDoList.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}

package com.TaskClaude.repository;

import com.TaskClaude.model.State;
import com.TaskClaude.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task save(Task task); //guardar una tarea
    Optional<Task> findById(Long id); //traer una tarea por ID
    List<Task> findAll(); // listar todas las tareas
    void deleteById(Long id); //eliminar una tarea por ID
    List<Task> findByState(State state);
}

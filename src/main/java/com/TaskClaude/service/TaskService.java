package com.TaskClaude.service;

import com.TaskClaude.model.State;
import com.TaskClaude.model.Task;
import com.TaskClaude.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Crear tarea
    public Task createTask(Task task) {
        // Forzamos valores correctos para creación:
        task.setId(null);  // La BD genera el ID
        task.setState(State.PENDING);  // Siempre empieza PENDING
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    // Marcar como completada
    public Task markAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setState(State.COMPLETED);
        return taskRepository.save(task);
    }

    // Obtener tarea por ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    // Listar todas las tareas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Actualizar tarea
    public Task updateTask(Long id, Task updatedTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        // Actualizar solo los campos permitidos
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        // NO actualizamos: id, state (tiene su metodo), createdAt (inmutable)

        return taskRepository.save(task);
    }

    // Eliminar tarea
    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {  // ← Solo verifica existencia
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);// ← Elimina directo
    }

}

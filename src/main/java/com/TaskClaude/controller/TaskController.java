package com.TaskClaude.controller;

import com.TaskClaude.model.Task;
import com.TaskClaude.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //crear tarea
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    //traer todos
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> getAll = taskService.getAllTasks();
        return new ResponseEntity<>(getAll, HttpStatus.OK);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);  // 200
    }

    // Actualizar - PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);  // 200
    }

    //elimiar tarea
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);  // Si falla, lanza excepción
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    // Marcar como completada
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markAsCompleted(@PathVariable Long id) {
        Task completedTask = taskService.markAsCompleted(id);
        return ResponseEntity.ok(completedTask);  // 200
    }

}

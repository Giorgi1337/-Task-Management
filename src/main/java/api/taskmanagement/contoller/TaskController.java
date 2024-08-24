package api.taskmanagement.contoller;


import api.taskmanagement.dto.TaskDTO;
import api.taskmanagement.model.Task;
import api.taskmanagement.model.TaskStatus;
import api.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/paged")
    public ResponseEntity<Page<Task>> getTasks(Pageable pageable) {
        Page<Task> tasks = taskService.getTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Task with ID " + task.getId() + " was successfully created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, @Valid @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok("Task with ID " + updatedTask.getId() + " was successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with ID " + id + " was successfully deleted.");
    }

    @GetMapping
    public ResponseEntity<Page<Task>> getTasksByStatus(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        Page<Task> tasks;
        if (status != null) {
            tasks = taskService.getTasksByStatus(status, pageable);
        } else {
            tasks = taskService.getTasks(pageable);
        }

        return ResponseEntity.ok(tasks);
    }
}

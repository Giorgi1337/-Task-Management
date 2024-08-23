package api.taskmanagement.service;

import api.taskmanagement.exception.TaskNotFoundException;
import api.taskmanagement.model.Task;
import api.taskmanagement.model.TaskStatus;
import api.taskmanagement.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int id, Task task) {
       Task existingTask = taskRepository.findById(id)
               .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

       if (task.getTitle() != null) {
           existingTask.setTitle(task.getTitle());
       }
       if (task.getDescription() != null) {
           existingTask.setDescription(task.getDescription());
       }
       if (task.getStatus() != null) {
           existingTask.setStatus(task.getStatus());
       }
       if (task.getPriority() != null) {
           existingTask.setPriority(task.getPriority());
       }
       if (task.getDueDate() != null) {
           existingTask.setDueDate(task.getDueDate());
       }

       return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(int id) {
       if (!taskRepository.existsById(id))
           throw new TaskNotFoundException("Task with ID " + id + " not found");

       taskRepository.deleteById(id);
    }

    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

}

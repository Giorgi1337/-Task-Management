package api.taskmanagement.service;

import api.taskmanagement.dto.TaskDTO;
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

    public Task createTask(TaskDTO taskDTO) {
        Task task = mapDtoToEntity(taskDTO);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int id, TaskDTO taskDTO) {
       Task existingTask = taskRepository.findById(id)
               .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

       updateEntityFromDto(existingTask, taskDTO);
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

    private Task mapDtoToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setDueDate(taskDTO.getDueDate());
        return task;
    }

    private void updateEntityFromDto(Task existingTask, TaskDTO taskDTO) {
        if (taskDTO.getTitle() != null) {
            existingTask.setTitle(taskDTO.getTitle());
        }
        if (taskDTO.getDescription() != null) {
            existingTask.setDescription(taskDTO.getDescription());
        }
        if (taskDTO.getStatus() != null) {
            existingTask.setStatus(taskDTO.getStatus());
        }
        if (taskDTO.getPriority() != null) {
            existingTask.setPriority(taskDTO.getPriority());
        }
        if (taskDTO.getDueDate() != null) {
            existingTask.setDueDate(taskDTO.getDueDate());
        }
    }
}

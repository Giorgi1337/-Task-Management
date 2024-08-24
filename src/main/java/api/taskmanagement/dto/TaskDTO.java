package api.taskmanagement.dto;

import api.taskmanagement.model.TaskPriority;
import api.taskmanagement.model.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Status is mandatory")
    private TaskStatus status;

    private TaskPriority priority;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
}

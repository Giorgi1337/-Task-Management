package api.taskmanagement.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

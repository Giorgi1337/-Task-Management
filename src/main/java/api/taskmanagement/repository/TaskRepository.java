package api.taskmanagement.repository;

import api.taskmanagement.model.Task;
import api.taskmanagement.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByAssigneeId(Integer userId);
    List<Task> findByCategoryId(Integer categoryId);
}

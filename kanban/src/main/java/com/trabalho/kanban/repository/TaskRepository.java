package com.trabalho.kanban.repository;

import com.trabalho.kanban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // buscar tarefas por status e prioridade
    List<Task> findByStatusAndPriority(Task.Status status, Task.Priority priority);

    // buscar tarefas por status
    List<Task> findByStatus(Task.Status status);
}

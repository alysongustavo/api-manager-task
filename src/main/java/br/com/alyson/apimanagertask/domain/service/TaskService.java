package br.com.alyson.apimanagertask.domain.service;

import br.com.alyson.apimanagertask.domain.exception.EntityNotFoundException;
import br.com.alyson.apimanagertask.domain.model.Status;
import br.com.alyson.apimanagertask.domain.model.Task;
import br.com.alyson.apimanagertask.domain.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listTaskUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Transactional
    public Task save(Task task) {
        task.setStatus(Status.ACTIVE);
        return taskRepository.save(task);
    }

    public Task findById(Long id, Long userId) {
        Task task =  taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (userId != null && !task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this task");
        }

        return task;
    }

    @Transactional
    public void delete(Long id) {
        Task task = findById(id, null);
        taskRepository.delete(task);
    }

    @Transactional
    public Task update(Long id, Task task) {
        Task taskFound = findById(id, null);
        taskFound.setTitle(task.getTitle());
        taskFound.setDescription(task.getDescription());
        taskFound.setUser(task.getUser());
        taskFound.setCategory(task.getCategory());
        taskFound.setPriority(task.getPriority());
        taskFound.setDateStarter(task.getDateStarter());

        if(task.getDateEnd() != null){
            taskFound.setDateEnd(task.getDateEnd());
        }

        return taskRepository.save(taskFound);
    }
}

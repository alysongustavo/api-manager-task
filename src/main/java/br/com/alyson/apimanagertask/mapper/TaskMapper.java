package br.com.alyson.apimanagertask.mapper;

import br.com.alyson.apimanagertask.domain.model.Task;
import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.dto.TaskDTO;
import br.com.alyson.apimanagertask.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory().getId(),
                task.getUser().getId(),
                task.getSituation(),
                task.getPriority(),
                task.getDateStarter(),
                task.getDateEnd(),
                task.getStatus()
        );
    }

    public List<TaskDTO> toDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

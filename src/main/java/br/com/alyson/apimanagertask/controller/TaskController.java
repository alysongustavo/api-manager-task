package br.com.alyson.apimanagertask.controller;

import br.com.alyson.apimanagertask.domain.model.Category;
import br.com.alyson.apimanagertask.domain.model.Task;
import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.domain.service.CategoryService;
import br.com.alyson.apimanagertask.domain.service.TaskService;
import br.com.alyson.apimanagertask.dto.CategorySaveDto;
import br.com.alyson.apimanagertask.dto.TaskDTO;
import br.com.alyson.apimanagertask.dto.TaskSaveDto;
import br.com.alyson.apimanagertask.dto.UserDTO;
import br.com.alyson.apimanagertask.mapper.TaskMapper;
import br.com.alyson.apimanagertask.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAll() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserDTO userDTO = userMapper.toDTO(currentUser);

        List<Task> tasks = taskService.listTaskUser(userDTO.getId());
        List<TaskDTO> taskDTOs = taskMapper.toDTOs(tasks);

        return ResponseEntity.ok(taskDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Task task = taskService.findById(id, currentUser.getId());
        TaskDTO taskDTO = taskMapper.toDTO(task);

        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody @Valid TaskSaveDto taskSaveDto) {

        Category category = new Category();
        category.setId(taskSaveDto.getCategoryId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        User user = new User();
        user.setId(currentUser.getId());

        Task task = new Task();
        task.setCategory(category);
        task.setUser(user);

        BeanUtils.copyProperties(taskSaveDto, task, "id", "categoryId", "userId");

        Task taskSaved = taskService.save(task);

        return ResponseEntity.ok(taskSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @Valid @RequestBody TaskSaveDto taskSaveDto) {

        Category category = new Category();
        category.setId(taskSaveDto.getCategoryId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        Task task = new Task();
        task.setCategory(category);
        task.setUser(currentUser);

        BeanUtils.copyProperties(taskSaveDto, task, "id", "categoryId", "userId");

        Task task1 = taskService.update(id, task);

        return ResponseEntity.ok(task1);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

}

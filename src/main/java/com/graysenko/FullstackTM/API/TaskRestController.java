package com.graysenko.FullstackTM.API;

import com.graysenko.FullstackTM.Dtos.TaskDTO;
import com.graysenko.FullstackTM.Entities.Task;
import com.graysenko.FullstackTM.Exceptions.TaskError;
import com.graysenko.FullstackTM.Services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class TaskRestController {

    private final TaskService taskService;


    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    //TASKS CONTROL

    @GetMapping("/tasks")
    public ResponseEntity<Page<TaskDTO>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Task> tasksPage = taskService.findAllTasks(PageRequest.of(page, size));
        Page<TaskDTO> taskDTOS = tasksPage.map(this::convertToDTO);

        return ResponseEntity.ok(taskDTOS);
    }

    @GetMapping("/tasks/search")
    public ResponseEntity<?> getSearchTasks(@RequestParam(required = false) String value){
        List<Task> taskOpt = taskService.findTaskByNameContaining(value);
        List<TaskDTO> taskDTOS = taskOpt.stream().map(this::convertToDTO).toList();

        return ResponseEntity.ok(taskDTOS);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {

        if (taskDTO.getName() == null || taskDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body(new TaskError(400, "Name should not be empty!"));
        }

        Task task = new Task(
                taskDTO.getName(),
                taskDTO.getDescription(),
                taskDTO.getTags(),
                taskDTO.getCompleted()
        );

        taskService.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(task));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Optional<Task> task = taskService.findTaskById(id);
        if (task.isPresent()) {
            taskService.delete(task.get());

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TaskError(404, "Task not found!"));
        }
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<Task> taskOpt = taskService.findTaskById(id);
        if (taskOpt.isPresent()) {

            if (taskDTO.getName() == null || taskDTO.getName().isBlank()) {
                return ResponseEntity.badRequest().body(new TaskError(400, "Name should not be empty!"));
            }

            Task task = taskOpt.get();
            task.setName(taskDTO.getName());
            if (taskDTO.getDescription() != null) task.setDescription(taskDTO.getDescription());
            if (taskDTO.getTags() != null) task.setTags(taskDTO.getTags());
            if (taskDTO.getCompleted() != null) task.setCompleted(!task.getCompleted());

            taskService.save(task);
            return ResponseEntity.ok(convertToDTO(task));
        } else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TaskError(404, "Task not found!"));
        }
    }


    //Utils

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setTags(task.getTags());

        return dto;
    }
}

package br.com.alyson.apimanagertask.dto;

import br.com.alyson.apimanagertask.domain.model.Category;
import br.com.alyson.apimanagertask.domain.model.Situation;
import br.com.alyson.apimanagertask.domain.model.Status;
import br.com.alyson.apimanagertask.domain.model.User;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskSaveDto {

    @NotNull(message = "Category is mandatory")
    private Long categoryId;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Priority is mandatory")
    private String priority;

    @NotNull(message = "Situation is mandatory")
    private Situation situation;

    @NotNull(message = "DataStart is mandatory")
    private LocalDate dateStarter;

    @NotNull(message = "DataEnd is mandatory")
    private LocalDate dateEnd;
}

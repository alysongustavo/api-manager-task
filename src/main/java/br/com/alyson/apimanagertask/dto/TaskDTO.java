package br.com.alyson.apimanagertask.dto;

import br.com.alyson.apimanagertask.domain.model.Situation;
import br.com.alyson.apimanagertask.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private Long userId;
    private Situation situation;
    private String priority;
    private LocalDate dateStarter;
    private LocalDate dateEnd;
    private Status status;


}

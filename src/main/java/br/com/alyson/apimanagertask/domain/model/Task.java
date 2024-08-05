package br.com.alyson.apimanagertask.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(generator = "task_seq")
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
    private String title;
    private String description;
    private Situation situation;
    private String priority;

    @Column(name = "start_date")
    private LocalDate dateStarter;

    @Column(name = "end_date")
    private LocalDate dateEnd;
    private Status status;

}

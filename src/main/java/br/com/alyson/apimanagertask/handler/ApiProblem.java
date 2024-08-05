package br.com.alyson.apimanagertask.handler;

import br.com.alyson.apimanagertask.response.ValidationErrorResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiProblem {

    private final LocalDateTime timestamp;
    private final String detail;
    private final Integer status;
    private final String type;
    private final String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationErrorResponse.Violation> violations;


}

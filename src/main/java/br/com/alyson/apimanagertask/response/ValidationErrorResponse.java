package br.com.alyson.apimanagertask.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidationErrorResponse {

    private List<Violation> violations;

    // Getters and setters

    @Getter
    @Setter
    public static class Violation {
        private String fieldName;
        private String message;

        public Violation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        // Getters and setters
    }
}

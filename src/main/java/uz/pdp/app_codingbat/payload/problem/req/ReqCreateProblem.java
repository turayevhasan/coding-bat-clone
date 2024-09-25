package uz.pdp.app_codingbat.payload.problem.req;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uz.pdp.app_codingbat.entity.Category;

@Getter
@Setter
public class ReqCreateProblem {

    @NotBlank
    private String name;

    @NotBlank
    private String questionTitle;

    @NotBlank
    private String exampleCase;

    @NotBlank
    private String questionCode;

    @NotNull
    private Long categoryId;

}

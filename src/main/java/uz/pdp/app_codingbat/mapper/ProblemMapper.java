package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.Category;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.payload.problem.req.ReqCreateProblem;
import uz.pdp.app_codingbat.payload.problem.req.ReqUpdateProblem;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.app_codingbat.utils.CoreUtils.getIfExists;

public interface ProblemMapper {

    static ResProblem fromEntityToDto(Problem problem) {
        return ResProblem.builder()
                .Id(problem.getId())
                .name(problem.getName())
                .questionTitle(problem.getQuestionTitle())
                .exampleCase(problem.getExampleCase())
                .questionCode(problem.getQuestionCode())
                .categoryName(problem.getCategory().getName())
                .createdAt(problem.getCreatedAt())
                .updatedAt(problem.getUpdatedAt())
                .deleted(problem.isDeleted())
                .build();
    }

    static Problem fromReqToEntity(ReqCreateProblem req, Category Category) {
        return Problem.builder()
                .name(req.getName())
                .questionTitle(req.getQuestionTitle())
                .exampleCase(req.getExampleCase())
                .questionCode(req.getQuestionCode())
                .category(Category)
                .build();
    }


    static void updateProblemFromReq(Problem problem, ReqUpdateProblem req, Category category) {
        problem.setName(getIfExists(req.getName(), problem.getName()));
        problem.setQuestionTitle(getIfExists(req.getQuestionTitle(), problem.getQuestionTitle()));
        problem.setExampleCase(getIfExists(req.getExampleCase(), problem.getExampleCase()));
        problem.setQuestionCode(getIfExists(req.getQuestionCode(), problem.getQuestionCode()));
        problem.setCategory(category);
    }

    static List<ResProblem> fromListEntityToListDto(List<Problem> problems) {
        ArrayList<ResProblem> resProblems = new ArrayList<>();

        problems.forEach(problem ->
                resProblems.add(fromEntityToDto(problem))
        );

        return resProblems;
    }

    static List<ResProblem> getAllFromPages(Page<Problem> problems) {
        List<ResProblem> resProblems = new ArrayList<>();

        problems.forEach(problem ->
                resProblems.add(fromEntityToDto(problem))
        );

        return resProblems;
    }
}

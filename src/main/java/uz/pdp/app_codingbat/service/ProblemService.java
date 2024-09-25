package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.entity.Case;
import uz.pdp.app_codingbat.entity.Category;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.ProblemMapper;
import uz.pdp.app_codingbat.payload.problem.req.ReqCreateProblem;
import uz.pdp.app_codingbat.payload.user.req.RequestFilterByCreatedDate;
import uz.pdp.app_codingbat.payload.problem.req.ReqUpdateProblem;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;
import uz.pdp.app_codingbat.repository.CaseRepository;
import uz.pdp.app_codingbat.repository.CategoryRepository;
import uz.pdp.app_codingbat.repository.ProblemRepository;
import uz.pdp.app_codingbat.utils.CoreUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProblemService {
    private  final CategoryRepository categoryRepository;
    private final ProblemRepository problemRepository;
    private final CaseRepository caseRepository;

    public ResProblem create(ReqCreateProblem req) {
        Category category = getCategory(req.getCategoryId());

        Problem problem = ProblemMapper.fromReqToEntity(req, category);

        problemRepository.save(problem);

        return ProblemMapper.fromEntityToDto(problem);
    }

    public ResProblem update(ReqUpdateProblem req){
        Problem problem = getProblem(req.getId());

        ProblemMapper.updateProblemFromReq(
                problem,
                req,
                getCategoryFromUpdateReq(req.getCategoryId(), problem.getCategory())
                );

        problemRepository.save(problem);

        return ProblemMapper.fromEntityToDto(problem);
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));
    }

    public Problem getProblem(Long problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROBLEM_NOT_FOUND));
    }

    private Category getCategoryFromUpdateReq(Long categoryId, Category category) {
        if (categoryId != null) {
            return getCategory(categoryId);
        }
        return category;
    }

    public boolean delete(Long id) {
        Problem problem = getProblem(id);
        problemRepository.delete(problem);
        return true;
    }

    public ResProblem getProblemById(Long problemId) {
        Problem problem = getProblem(problemId);
        return ProblemMapper.fromEntityToDto(problem);
    }

    public List<ResProblem> getAllByPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Problem> problems = problemRepository.findAll(pageRequest);
        return ProblemMapper.getAllFromPages(problems);
    }

    public List<ResProblem> filterProblems(RequestFilterByCreatedDate req) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize(), sort);

        Page<Problem> problems = problemRepository.findProblemsByCreatedAtBetween(
                req.getStartDate(),
                req.getEndDate(),
                pageRequest
        );

        return ProblemMapper.getAllFromPages(problems);
    }

    public void deleteCasesByProblemId(Long problemId) {
        List<Case> cases = caseRepository.findAllByProblemId(problemId);
        caseRepository.deleteAll(cases);
    }

    public Page<ResProblem> search(String keyword, Pageable pageable) {
        Page<Problem> problems = problemRepository.search(keyword, pageable);

        List<ResProblem> resProblems = problems.getContent()
                .stream()
                .map(ProblemMapper :: fromEntityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(resProblems, pageable, problems.getTotalElements());
    }

}

package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.entity.Case;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.CaseMapper;
import uz.pdp.app_codingbat.payload.cases.req.ReqCreateCase;
import uz.pdp.app_codingbat.payload.cases.req.ReqUpdateCase;
import uz.pdp.app_codingbat.payload.cases.res.ResCase;
import uz.pdp.app_codingbat.repository.CaseRepository;
import uz.pdp.app_codingbat.repository.ProblemRepository;
import uz.pdp.app_codingbat.utils.CoreUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseService {
    private final CaseRepository caseRepository;
    private final ProblemRepository problemRepository;

    public ResCase create(ReqCreateCase req) {
        Problem problem = getProblem(req.getProblemId());

        Case aCase = CaseMapper.fromReqToEntity(req, problem);
        caseRepository.save(aCase);

        return CaseMapper.fromEntityToDto(aCase);

    }

    public ResCase update(ReqUpdateCase req) {
        Case aCase = getCase(req.getId());

        CaseMapper.updateCaseFromReq(
                aCase,
                req,
                getProblemFromUpdateReq(req.getProblemId(), aCase.getProblem())
        );

        caseRepository.save(aCase);
        return CaseMapper.fromEntityToDto(aCase);
    }

    public boolean delete(Long id) {
        Case aCase = getCase(id);
        caseRepository.delete(aCase);
        return true;
    }

    public ResCase getCaseById(Long id) {
        Case aCase = getCase(id);
        return CaseMapper.fromEntityToDto(aCase);
    }

    public List<ResCase> getAllByPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Case> cases = caseRepository.findAll(pageRequest);
        return CaseMapper.getAllFromPages(cases);
    }

    public Problem getProblem(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROBLEM_NOT_FOUND));
    }

    public Case getCase(Long id) {
        return caseRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CASE_NOT_FOUND));
    }

    public Problem getProblemFromUpdateReq(Long problemId, Problem problem) {
        if (problemId != null) {
            return getProblem(problemId);
        }

        return problem;
    }
}

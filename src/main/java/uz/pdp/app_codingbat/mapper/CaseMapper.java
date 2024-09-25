package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.Case;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.payload.compile.CaseDTO;
import uz.pdp.app_codingbat.payload.cases.req.ReqCreateCase;
import uz.pdp.app_codingbat.payload.cases.req.ReqUpdateCase;
import uz.pdp.app_codingbat.payload.cases.res.ResCase;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.app_codingbat.utils.CoreUtils.getIfExists;

public interface CaseMapper {

    static ResCase fromEntityToDto(Case aCase) {
        return ResCase.builder()
                .id(aCase.getId())
                .args(aCase.getArgs())
                .expected(aCase.getExpected())
                .visible(aCase.getVisible())
                .problem(ProblemMapper.fromEntityToDto(aCase.getProblem()))
                .createdAt(aCase.getCreatedAt())
                .updatedAt(aCase.getUpdatedAt())
                .deleted(aCase.isDeleted())
                .build();
    }

    static Case fromReqToEntity(ReqCreateCase req, Problem problem) {
        return Case.builder()
                .args(req.getArgs())
                .expected(req.getExpected())
                .visible(req.getVisible())
                .problem(problem)
                .build();
    }

    static void updateCaseFromReq(Case aCase, ReqUpdateCase req, Problem problem) {
        aCase.setArgs(getIfExists(req.getArgs(), aCase.getArgs()));
        aCase.setExpected(getIfExists(req.getExpected(), aCase.getExpected()));
        aCase.setVisible(getIfExists(req.getVisible(), aCase.getVisible()));
        aCase.setProblem(problem);
    }

    static List<ResCase> fromListEntityToListDto(List<Case> cases) {
        ArrayList<ResCase> resCases = new ArrayList<>();

        cases.forEach(aCase -> {
            resCases.add(fromEntityToDto(aCase));
        });

        return resCases;
    }

    static List<ResCase> getAllFromPages(Page<Case> cases) {
        List<ResCase> resCases = new ArrayList<>();

        cases.forEach(aCase ->
                resCases.add(fromEntityToDto(aCase))
        );

        return resCases;
    }

    static List<CaseDTO> entityToDto(List<Case> cases) {
        List<CaseDTO> list = new ArrayList<>();
        for (Case c : cases) {
            list.add(new CaseDTO(c.getArgs(), c.getExpected(), c.getVisible()));
        }
        return list;
    }
}

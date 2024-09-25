package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.compiler.Compiler;
import uz.pdp.app_codingbat.config.core.GlobalVar;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.payload.compile.CompilerResult;
import uz.pdp.app_codingbat.entity.Case;
import uz.pdp.app_codingbat.mapper.CaseMapper;
import uz.pdp.app_codingbat.payload.compile.ReqCompileCode;
import uz.pdp.app_codingbat.repository.CaseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilerService {
    private final CaseRepository caseRepository;
    private final SubmissionService submissionService;

    public List<CompilerResult> run(ReqCompileCode req) {
        List<Case> cases = caseRepository.findAllByProblemId(req.getProblemId());

        List<CompilerResult> compilerResults = Compiler.compileCode(req.getCode(), cases.get(0).getProblem().getName(), CaseMapper.entityToDto(cases));

        User user = GlobalVar.getUser();

        if (user != null) {
            submissionService.save(req.getCode(), compilerResults, user, req.getProblemId());
        }

        return compilerResults;
    }

}

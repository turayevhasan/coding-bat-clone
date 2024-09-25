package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.entity.Category;
import uz.pdp.app_codingbat.entity.Language;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.CategoryMapper;
import uz.pdp.app_codingbat.payload.category.req.ReqCreateCategory;
import uz.pdp.app_codingbat.payload.category.req.ReqUpdateCategory;
import uz.pdp.app_codingbat.payload.category.res.ResCategory;
import uz.pdp.app_codingbat.repository.CategoryRepository;
import uz.pdp.app_codingbat.repository.LanguageRepository;
import uz.pdp.app_codingbat.repository.ProblemRepository;
import uz.pdp.app_codingbat.utils.CoreUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LanguageRepository languageRepository;
    private final ProblemRepository problemRepository;
    private final CaseService caseService;
    private final ProblemService problemService;

    public ResCategory addCategory(ReqCreateCategory req) {
        Long languageId = req.getLanguageId();
        Language language = getLanguage(languageId);

        Category category = CategoryMapper.fromReqToEntity(req, language);

        categoryRepository.save(category);

        return CategoryMapper.fromEntityToDto(category);
    }

    private Language getLanguage(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LANGUAGE_NOT_FOUND));
    }

    public ResCategory updateCategory(ReqUpdateCategory req) {
        Category category = getCategory(req.getId());
        Language language = getUpdateLanguage(req.getLanguageId());

        CategoryMapper.updateCategory(category, req, language);

        categoryRepository.save(category);

        return CategoryMapper.fromEntityToDto(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategory(id);

        deleteProblemsByCategoryId(id);

        categoryRepository.delete(category);
    }

    public ResCategory getOneCategory(Long id) {
        Category category = getCategory(id);
        return CategoryMapper.fromEntityToDto(category);
    }

    public List<ResCategory> getAllByPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Category> categories = categoryRepository.findAll(pageRequest);

        return CategoryMapper.CategoriesPageToList(categories);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.CATEGORY_NOT_FOUND));
    }

    private Language getUpdateLanguage(Long id) {
        return id != null ? getLanguage(id) : null;
    }

    public void deleteProblemsByCategoryId(Long id) {
        List<Problem> problems = problemRepository.findAllByCategoryId(id);

        problems.forEach(problem -> {
            problemService.deleteCasesByProblemId(problem.getId());
            problemRepository.delete(problem);
        });

    }
}

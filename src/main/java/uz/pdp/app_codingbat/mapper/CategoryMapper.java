package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.Category;
import uz.pdp.app_codingbat.entity.Language;
import uz.pdp.app_codingbat.payload.category.req.ReqCreateCategory;
import uz.pdp.app_codingbat.payload.category.req.ReqUpdateCategory;
import uz.pdp.app_codingbat.payload.category.res.ResCategory;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.app_codingbat.utils.CoreUtils.getIfExists;

public interface CategoryMapper {

    static ResCategory fromEntityToDto(Category category) {
        return ResCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .languageName(category.getLanguage().getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .deleted(Boolean.FALSE)
                .build();
    }

    static Category fromReqToEntity(ReqCreateCategory req, Language language) {
        return Category.builder()
                .name(req.getName())
                .description(req.getDescription())
                .maxStars(req.getMaxStars())
                .language(language)
                .build();
    }

    static void updateCategory(Category category, ReqUpdateCategory req, Language newLanguage) {
        category.setName(getIfExists(req.getName(), category.getName()));
        category.setDescription(getIfExists(req.getDescription(), category.getDescription()));
        category.setMaxStars(getIfExists(req.getMaxStars(), category.getMaxStars()));
        category.setLanguage(getIfExists(newLanguage, category.getLanguage()));
    }

    static List<ResCategory> CategoriesPageToList(Page<Category> categories) {
        List<ResCategory> resCategories = new ArrayList<>();

        categories.forEach(category ->
                resCategories.add(fromEntityToDto(category))
        );

        return resCategories;
    }
}

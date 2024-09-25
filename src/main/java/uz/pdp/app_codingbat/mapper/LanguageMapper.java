package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.Language;
import uz.pdp.app_codingbat.payload.language.req.ReqUpdateLanguage;
import uz.pdp.app_codingbat.payload.language.res.ResLanguage;

import java.util.ArrayList;
import java.util.List;

public interface LanguageMapper {

    static ResLanguage fromEntityToDto(Language language) {
        return ResLanguage.builder()
                .id(language.getId())
                .name(language.getName())
                .build();
    }

    static List<ResLanguage> fromPagesToList(Page<Language> all) {
        List<ResLanguage> languages = new ArrayList<>();
        for (Language language : all) {
            languages.add(fromEntityToDto(language));
        }
        return languages;
    }
}


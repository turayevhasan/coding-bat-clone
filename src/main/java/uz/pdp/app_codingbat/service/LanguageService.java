package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.entity.Language;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.LanguageMapper;
import uz.pdp.app_codingbat.payload.language.req.ReqUpdateLanguage;
import uz.pdp.app_codingbat.payload.language.res.ResLanguage;
import uz.pdp.app_codingbat.repository.LanguageRepository;
import uz.pdp.app_codingbat.utils.CoreUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public ResLanguage create(String name) {
        Language language = Language.builder()
                .name(name)
                .build();

        languageRepository.save(language);

        return LanguageMapper.fromEntityToDto(language);
    }

    public ResLanguage update(ReqUpdateLanguage req) {
        Language language = getLanguage(req.getId());

        language.setName(req.getName());

        languageRepository.save(language);

        return LanguageMapper.fromEntityToDto(language);
    }

    public boolean delete(Long id) {
        Language language = getLanguage(id);
        languageRepository.delete(language);
        return true;
    }

    public ResLanguage get(Long id) {
        Language language = getLanguage(id);
        return LanguageMapper.fromEntityToDto(language);
    }

    private Language getLanguage(Long id) {
        return languageRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.LANGUAGE_NOT_FOUND));
    }

    public List<ResLanguage> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Language> pages = languageRepository.findAll(pageRequest);

        return LanguageMapper.fromPagesToList(pages);
    }
}


package uz.pdp.app_codingbat.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    READ_ME,

    ADD_LANGUAGE,
    DELETE_LANGUAGE,
    UPDATE_LANGUAGE,
    GET_LANGUAGE,

    ADD_CATEGORY,
    DELETE_CATEGORY,
    UPDATE_CATEGORY,
    GET_CATEGORY,

    ADD_USER,
    DELETE_USER,
    UPDATE_USER,
    GET_ALL_USERS,
    GET_USER,

    ADD_PROBLEM,
    DELETE_PROBLEM,
    UPDATE_PROBLEM,
    GET_PROBLEM,

    ADD_ROLE,
    DELETE_ROLE,
    UPDATE_ROLE,
    GET_ROLE,

    ADD_SUBMISSION,
    DELETE_SUBMISSION,
    UPDATE_SUBMISSION,
    GET_SUBMISSION,

    ADD_CASE,
    DELETE_CASE,
    UPDATE_CASE,
    GET_CASE;

    @Override
    public String getAuthority() {
        return name();
    }
}

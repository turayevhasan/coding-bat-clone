package uz.pdp.app_codingbat.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreUtils {

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Collection<?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isPresent(String str) {
        return StringUtils.hasText(str);
    }

    public static boolean isPresent(Object obj) {
        return obj != null;
    }

    public static boolean isPresent(Collection<?> col) {
        return col != null && !col.isEmpty();
    }

    public static boolean isPresent(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static String generateSmsCode() {
        return String.valueOf((int) (Math.random() * ((999999 - 100000) + 1)) + 100000).substring(0, 6);
    }


    public static boolean validatePhone(String phoneNumber) {

        if (!StringUtils.hasText(phoneNumber)) {
            return true;
        }

        Pattern pattern = Pattern.compile("^998\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        return !matcher.matches();

    }

    public static  <E> E getIfExists(E newObj, E oldObj) {
        return Objects.nonNull(newObj) ? newObj : oldObj;
    }

}

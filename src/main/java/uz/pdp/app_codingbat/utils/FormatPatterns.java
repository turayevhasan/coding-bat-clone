package uz.pdp.app_codingbat.utils;

public interface FormatPatterns {
    public static final String BANK_ID = "^\\d{5}$";
    public static final String PINFL = "^\\d{14}$";
    public static final String INN = "^\\d{9}$";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String NAME = "^[A-Za-zA-Яа-я-']+$";

    //FIXME replace on NAME_WITH_SPACE or change contract
    public static final String NAME_DIGIT_SPACE = "^[0-9A-Za-zA-Яа-я- ']+$";
    public static final String NAME_WITH_SPACE = "^[A-Za-zA-Яа-я- ']+$";
    public static final String LATIN_NAME = "^[A-Za-z-']+$";

    //FIXME replace on LATIN_NAME_WITH_SPACE or change contract
    public static final String LATIN_NAME_DIGIT_SPACE = "^[0-9A-Za-z- ']+$";
    public static final String LATIN_NAME_WITH_SPACE = "^[A-Za-z- ']+$";
    public static final String REGION = "^\\d{1,2}$";
    public static final String MOBILE_PHONE = "^998\\d{9}$";
    public static final String REQUEST_ID = "^[0-9a-zA-Z-]{10,40}$";
    public static final String DIGITS = "^\\d*$";
    public static final String ACCOUNT_NUMBER = "^\\d{20}$";
    public static final String TERM = "^\\d+(M|D)$";
}

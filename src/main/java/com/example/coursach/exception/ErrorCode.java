package com.example.coursach.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_ALREADY_EXIST("user.already.exist"),
    WEAK_PASSWORD("weak.password"),
    VALIDATION_FAILED("validation.failed"),
    INVALID_EMAIL_OR_PASSWORD("invalid.email.or.password"),
    USER_NOT_FOUND("user.not.found"),
    PROFILE_NOT_FOUND("profile.not.found"),
    PROFILE_ALREADY_EXIST("profile.already.exist"),

    NOT_FOUND_IN_DATABASE("not.found.in.database"),
    FILE_IS_EMPTY("file.is.empty"),
    WRONG_FORMAT("wrong.format"),

    INVALID_DATA("invalid.data"),
    USAGE_ALREADY_EXISTS("usage.already.exists"),
    INVALID_DATE_INTERVAL("invalid.date.interval"),
    CANCEL_TIME_OUT_EXCEPTION("cancel.time.out.exception"),
    UPDATE_TIME_OUT_EXCEPTION("update.time.out.exception"),

    INVALID_ITEM_TYPE("invalid.item.type"),

    //SharedItem
    INVALID_HEX_CODE("invalid.hex.code"),
    FIELD_NOT_FOUND("field.not.found"),
    UNKNOWN_FIELDS("unknown.fields"),
    INVALID_ADDITIONAL_FIELD("invalid.additional.field"),
    WEAK_MODEL("weak.model"),
    WEAK_YEAR("weak.year"),
    WEAK_DATE("weak.date"),
    WEAK_NUMBER("weak.number"),
    WEAK_OTHER_INFORMATION("week.other.information.code"),
    WEAK_TYPE("weak.type"),
    WEAK_ADDRESS("weak.address"),
    WEAK_GPS("weak.gps"),

    NOT_OWNER("not.owner"),
    COOWNER_NOT_FOUND("coowner.not.found"),
    SHARED_ITEM_NOT_FOUND("shared.item.not.found"),

    INVOICE_NOT_FOUND("invoice.not.found"),

    //Usage
    USAGE_HISTORY_ACCESS_DENIED("usage.history.access.denied"),
    USAGE_NOT_FOUND("usage.not.found"),
    BOOKINGS_NOT_FOUND("bookings.not.found"),

    //ItemFinances
    WEAK_BALANCE("weak.balance"),
    WEAK_RENTAL_HOUR("weak.rental.hour"),
    WEAK_MONTHLY_PAYMENT("weak.monthly.payment"),
    WEAK_ANNUAL_USE("weak.annual.use"),
    WEAK_CURRENCY("weak.currency"),
    //Rules
    WEAK_RULES("weak.rules"),
    //AdditionalFields
    WEAK_ADDITIONAL_FIELDS("weak.additional.fields"),

    WEAK_COST("weak.cost"),

    //For tests, empty in repository
    TEST_CODE("test.code"),

    //Registration
    INVALID_CODE("invalid.code"),

    //Notification
    NOTIFICATION_NOT_FOUND("notification.not.found");


    //Fields for javax.validation annotations
    //Login
    public static final String WEAK_FINANCES = "weak.finances";
    public static final String WEAK_EMAIL = "weak.email";
    public static final String WEAK_NICKNAME = "weak.nickname";
    public static final String WEAK_ITEM_TYPE = "weak.item.type";
    public static final String WEAK_ITEM_NAME = "weak.item.name";
    public static final String UUID_CANT_BE_NULL = "uuid.cant.be.null";
    public static final String INVALID_PRESET_FIELDS = "invalid.preset.fields";

    //Invoice
    public static final String WEAK_INVOICE_TITLE = "weak.invoice.title";
    public static final String WEAK_EXPENSE_TITLE = "weak.expense.title";
    public static final String WEAK_DETAILS = "weak.details";
    public static final String WEAK_INVOICE_UUID = "weak.invoice.uuid";
    public static final String WEAK_ITEM_UUID = "weak.item.uuid";
    public static final String WEAK_INVOICE_COST = "weak.invoice.cost";
    public static final String WEAK_INVOICE_DATE = "weak.invoice.date";

    private static final Map<String, ErrorCode> LOOKUP;

    private final String code;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(ErrorCode.values())
                .collect(Collectors.toMap(ErrorCode::getCode, Function.identity())));
    }

    public static ErrorCode lookup(String code) {
        return Optional.ofNullable(LOOKUP.get(code))
                .orElseThrow(() -> new IllegalArgumentException("Unknown code " + code));
    }
}
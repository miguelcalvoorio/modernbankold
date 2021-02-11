package org.modernbank.architecture.core.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorMessages {

    public enum ErrorLevel {INFO, WARNING, ERROR}

    private ErrorMessages() {}

    public static String getMessageForLocale(String messageKey) {
        return ResourceBundle.getBundle("error-messages", new Locale("en")).getString(messageKey);
    }
    
    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("error-messages", locale).getString(messageKey);
    }
}

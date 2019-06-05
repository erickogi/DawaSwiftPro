package com.tufike.taxi.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static boolean validateEmailAddress(String emailAddress) {
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher   = regexPattern.matcher(emailAddress);
        return regMatcher.matches();
    }
}

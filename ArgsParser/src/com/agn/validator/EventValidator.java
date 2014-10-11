package com.agn.validator;


import java.util.List;

public interface EventValidator {

    boolean validateTitle(String title);
    boolean validateTimeStartEnd(List<String> timeList);
}

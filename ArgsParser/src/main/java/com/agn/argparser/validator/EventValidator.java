package com.agn.argparser.validator;


import org.joda.time.DateTime;

import java.util.List;

public interface EventValidator {

    boolean validateTitle(String title);

    boolean validateTimeStartEnd(List<DateTime> timeList);

    DateTime validateTimeParse(String timeString);

}

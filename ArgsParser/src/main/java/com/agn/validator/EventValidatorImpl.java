package com.agn.validator;

import org.joda.time.DateTime;

import java.util.List;

public class EventValidatorImpl implements EventValidator {
    @Override
    public boolean validateTimeStartEnd(List<DateTime> timeList) {
        DateTime timeStart = null;
        DateTime timeEnd = null;
        if (timeList.size() > 1) {
            timeStart = timeList.get(0);
            timeEnd = timeList.get(1);
        } else System.out.println("Log: date is not defined!");

        if (timeStart != null) {
            if (timeStart.isAfter(timeEnd)) {
                System.out.println("Please set END date in future relatively to START date.");
                return false;
            }
        }
        return true;
    }

    @Override
    public DateTime validateTimeParse(String timeString) {
        DateTime timeParsed = null;
        try {
            timeParsed = DateTime.parse(timeString);
        } catch (Exception e) {
            System.out.println("Log: date conversion error " + e.getMessage());
        }
        return timeParsed;
    }


    @Override
    public boolean validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("Please set proper event title!");
            return false;
        }
        return true;
    }
}

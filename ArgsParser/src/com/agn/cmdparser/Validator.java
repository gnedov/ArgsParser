package com.agn.cmdparser;

import org.joda.time.DateTime;

import java.util.List;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class Validator {

    public boolean doValidate(EventParameters evParameters) {

        System.out.println(" validate phase starts....");

        switch (evParameters.getActionTypeId()) {
            case CREATE_CODE:
                return (validateTitle(evParameters.getTitle())
                        && validateTimeStartEnd(evParameters.getTimeList()));

            case UPDATE_CODE:
                return true;

            case SEARCH_CODE:

                return true;

            case SEARCH_ALL_CODE:
                return true;

            case SEARCH_BY_TITLE_CODE:
                return validateTitle(evParameters.getTitle());

            default:
                return true;
        }
    }

    private boolean validateTimeStartEnd(List<String> timeList) {
        DateTime timeStart = null;
        DateTime timeEnd = null;
        if (timeList.size() > 0) {
            try {
                timeStart = DateTime.parse(timeList.get(0));
                timeEnd = DateTime.parse(timeList.get(1));
            } catch (Exception e) {
                System.out.println("Log: date conversion error " + e.getMessage());
            }
        } else System.out.println("Log: date is not defined!");

        if (timeStart != null) {
            if (timeStart.isAfter(timeEnd)) {
                System.out.println("Please set END date in future relatively to START date.");
                return false;
            }
        }

        return true;
    }

    private boolean validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("Please set proper event title!");
            return false;
        }
        return true;
    }
}

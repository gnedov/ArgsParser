package com.agn.validator;

import com.agn.cmdparser.EventParameters;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class ActionParametersValidator {

    public boolean doValidate(EventParameters evParameters) {
        EventValidator eventValidator = new EventValidatorImpl();

        System.out.println(" validate phase starts....");

        switch (evParameters.getActionTypeId()) {
            case CREATE_CODE:
                List<DateTime> timeList = new ArrayList<>(2);
                for (String timeString : evParameters.getTimeList()) {
                    timeList.add(eventValidator.validateTimeParse(timeString));
                }

                return (eventValidator.validateTitle(evParameters.getTitle())
                        && eventValidator.validateTimeStartEnd(timeList));

            case UPDATE_CODE:
                return true;

            case SEARCH_CODE:

                return true;

            case SEARCH_ALL_CODE:
                return true;

            case SEARCH_BY_TITLE_CODE:
                return eventValidator.validateTitle(evParameters.getTitle());

            case EXIT_CODE:
                return true;

            default:
                return true;
        }
    }


}

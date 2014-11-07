package com.agn.validator;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;

public class EventValidatorImpl implements EventValidator {
    private static final Logger LOG = Logger.getLogger(EventValidatorImpl.class);

    @Override
    public boolean validateTimeStartEnd(List<DateTime> timeList) {
        DateTime timeStart = null;
        DateTime timeEnd = null;
        if (timeList.size() > 1) {
            timeStart = timeList.get(0);
            timeEnd = timeList.get(1);
        } else {
            System.out.println("Date is not defined properly!");
            LOG.warn("Date is not defined properly!");
        }

        if (timeStart != null) {
            if (timeStart.isAfter(timeEnd)) {
                System.out.println("Please set END date in future relatively to START date.");
                LOG.warn("Start End dates sequence is broken! timeStart: " + timeStart.toString() +
                        "; timeEnd: " + timeEnd.toString());
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
            LOG.warn("date conversion error" + e.getMessage());
        }
        return timeParsed;
    }


    @Override
    public boolean validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("Please set proper event title!");
            LOG.warn("event title is not correct!");
            return false;
        }
        return true;
    }
}

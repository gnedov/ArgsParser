package com.agn.cmdparser;

import com.agn.clndrclient.CalendarClient;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class ActionExecutor {
    private boolean isBreakApplication;
    private final CalendarClient calendarClient;

    public ActionExecutor(CalendarClient calendarClient) {
         this.calendarClient = calendarClient;
    }

    public boolean isBreakApplication() {
        return isBreakApplication;
    }

    public void doAction(EventParameters evParameters) {

        switch (evParameters.getActionTypeId()) {
            case CREATE_CODE:
                //call service.createEvent(evParameters.getTitle(), ...)
                System.out.println("Event <" + evParameters.getTitle() + "> is created! Congratulations!");
                break;
            case UPDATE_CODE:
                // call update ...
                break;
            case DELETE_CODE:
                // call delete etc.
                // And so on...
                break;
            case SEARCH_CODE:

                break;
            case SEARCH_ALL_CODE:
                System.out.println(calendarClient.searchAll().toString());
                break;
            case SEARCH_BY_TITLE_CODE:
                //call searchByTitle
                break;
            case EXIT_CODE:
                System.out.println("Goodbye!");
                isBreakApplication = true;
                break;
            default:
                break;
        }
    }
}

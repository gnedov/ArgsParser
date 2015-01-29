package com.agn.argparser.cmd;

import com.agn.argparser.clndrclient.CalendarClient;
import org.apache.log4j.Logger;

import static com.agn.argparser.cmd.ConstActionTypeEnum.*;

class ActionExecutor {
    private boolean isBreakApplication;
    private final CalendarClient calendarClient;
    private static final Logger LOG = Logger.getLogger(ActionExecutor.class);

    public ActionExecutor(CalendarClient calendarClient) {
        this.calendarClient = calendarClient;
    }

    public boolean isBreakApplication() {
        if (LOG.isDebugEnabled())
            LOG.debug("call isBreakApplication() returns " + isBreakApplication);
        return isBreakApplication;
    }

    public void doAction(EventParameters evParameters) {

        switch (evParameters.getActionTypeId()) {
            case CREATE_CODE:
                if (LOG.isDebugEnabled())
                    LOG.debug("try to call service.createEvent(evParameters.getTitle(), ...) ");
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
                if (LOG.isDebugEnabled())
                    LOG.debug("try to call calendarClient.searchAll() ");
                calendarClient.searchAll();
                printLastSearchResult();
                break;
            case SEARCH_BY_TITLE_CODE:
                if (LOG.isDebugEnabled())
                    LOG.debug("try to call calendarClient.searchAll() ");
                calendarClient.searchByTitle(evParameters.getTitle());
                printLastSearchResult();
                break;
            case EXIT_CODE:
                if (LOG.isDebugEnabled())
                    LOG.debug("application is going to exit ");
                System.out.println("Goodbye!");
                isBreakApplication = true;
                break;
            default:
                break;
        }
    }

    private void printLastSearchResult() {
        System.out.println("The last search result is:");
        System.out.println(calendarClient.getLastSearchResult());
    }
}

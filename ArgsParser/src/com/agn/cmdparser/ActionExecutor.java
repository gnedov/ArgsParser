package com.agn.cmdparser;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class ActionExecutor {
    private boolean isBreakApplication;

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
                //call searchAll
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

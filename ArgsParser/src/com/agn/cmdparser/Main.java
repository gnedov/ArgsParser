package com.agn.cmdparser;

import com.beust.jcommander.ParameterException;

import java.util.Scanner;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class Main {

    public static void main(String[] args) {

        doParse(args);

    }

    private static void doParse(String[] args) {
        EventParameters evParameters = new EventParameters();
        ArgumentParser argParser = new ArgumentParser();
        Scanner in = new Scanner(System.in);
        String strToParse = null;
        Validator validator = new Validator();

//hardcode! this line only for testing
        strToParse = "create -t eventTitle_hc -d descr bnbnb kkfkf -ts 2014-07-02T16:22:34 -te 2014-07-02T23:11:11 -att eeeee@mail.ff vvvvv@mail.ff ";

        if (args.length > 0) {
            strToParse = args.toString(); // [Andr]: need check this!!
        }

        while (true) {
            if (strToParse == null) {
                if (!argParser.isSearchModeON())
                    System.out.print(" Type command here([help] - display all possible commands and usage) _>");
                else {
                    System.out.println("Please select number of desired search type and required parameters for it:");
                    System.out.println("(example: 2 -t eventTitle )");
                    System.out.println("[1]- search all events. No parameters. ");
                    System.out.println("[2]- search event by title. Parameters: -title ");
                    System.out.println("[0]- Disable search mode. No parameters. ");
                }

                strToParse = in.nextLine();
                if (strToParse.isEmpty()) {
                    strToParse = null;
                    continue;
                }
            }

            try {
                evParameters = argParser.parse(strToParse);
            } catch (ParameterException e) {
                System.out.println("Be careful! Error detected: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("So bad: " + e.getMessage());
            } finally {
                strToParse = null;
            }

            if (argParser.isBreak()) {
                System.out.println("Goodbye!");
                break;
            }
            if (evParameters != null) {
                if (validator.doValidate(evParameters))
                    doAction(evParameters);
            }
            evParameters = null;
        }
    }


    private static void doAction(EventParameters evParameters) {

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
            default:
                break;
        }
    }
}
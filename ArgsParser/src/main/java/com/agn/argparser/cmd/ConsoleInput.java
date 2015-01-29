package com.agn.argparser.cmd;

import com.agn.argparser.clndrclient.CalendarClient;
import com.agn.argparser.validator.ActionParametersValidator;
import com.beust.jcommander.ParameterException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInput {
    private final CalendarClient calendarClient;
    private static final Logger LOG = Logger.getLogger(ConsoleInput.class);

    public ConsoleInput(CalendarClient calendarClient) {

        this.calendarClient = calendarClient;
        if (LOG.isDebugEnabled())
            LOG.debug("Initialized ConsoleInput with CalendarClient " + calendarClient.getClass().getName());
    }

    public CalendarClient getCalendarClient() {
        return calendarClient;
    }

    public boolean startConsole(String[] args) {
        //hardcode! this line only for testing
        //    strToParse = "create -t eventTitle_hc -d descr bnbnb kkfkf -ts 2014-07-02T16:22:34 -te 2014-07-02T23:11:11 -att eeeee@mail.ff vvvvv@mail.ff ";
        if (args.length > 0) {
            String inputString = ArgumentParser.getWholeString(Arrays.asList(args));
            if (LOG.isDebugEnabled())
                LOG.debug("try to start doParse() with input: " + inputString);
            doParse(inputString);
        } else{
            if (LOG.isDebugEnabled())
                LOG.debug("try to start doParse() without input arguments.");
            doParse(null);
        }
        return true;
    }

    private void doParse(String strToParse) {
        EventParameters evParameters = new EventParameters();
        ArgumentParser argParser = new ArgumentParser();
        Scanner in = new Scanner(System.in);
        ActionExecutor actionExecutor = new ActionExecutor(calendarClient);
        ActionParametersValidator validator = new ActionParametersValidator();
        if (LOG.isDebugEnabled())
            LOG.debug("try to enter to infinite loop...");
        while (true) {
            if (strToParse == null) {
                printInvitation(argParser.isSearchModeON());

                strToParse = in.nextLine();
                if (strToParse.isEmpty()) {
                    strToParse = null;
                    continue;
                }
            }

            try {
                evParameters = argParser.parse(strToParse);
            } catch (ParameterException e) {
                System.out.println("Be careful! Please input the command properly!");
                LOG.warn("Something wrong with input parameters parsing: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Bad news for you. Please restart the application to avoid undesirable behavior.");
                LOG.error("Got the error from Parser: "+ e.getMessage());
            } finally {
                strToParse = null;
            }

            if (evParameters != null) {
                if (validator.doValidate(evParameters))
                    actionExecutor.doAction(evParameters);

                if (actionExecutor.isBreakApplication())
                    break;
            }
            evParameters = null;
        }
        if (LOG.isDebugEnabled())
            LOG.debug("exited from infinite loop.");
    }

    private void printInvitation(boolean isSearchModeON) {
        if (!isSearchModeON)
            System.out.print(" Type command here([help] - display all possible commands and usage) _>");
        else {
            System.out.println("Please select the number of desired search type and required parameters for it:");
            System.out.println("(example: 2 -t eventTitle )");
            System.out.println("[1]- search all events. No parameters. ");
            System.out.println("[2]- search event by title. Parameters: -title ");
            System.out.println("[0]- exit search mode. No parameters. ");
        }
    }

}

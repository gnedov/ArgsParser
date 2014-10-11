package com.agn.cmdparser;


import com.agn.validator.ActionParametersValidator;
import com.beust.jcommander.ParameterException;

import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInput {

    public boolean startConsole(String[] args) {
        //hardcode! this line only for testing
        //    strToParse = "create -t eventTitle_hc -d descr bnbnb kkfkf -ts 2014-07-02T16:22:34 -te 2014-07-02T23:11:11 -att eeeee@mail.ff vvvvv@mail.ff ";

        if (args.length > 0) {
            doParse(ArgumentParser.getWholeString(Arrays.asList(args)));
        }
        return true;
    }

    private void doParse(String strToParse) {
        EventParameters evParameters = new EventParameters();
        ArgumentParser argParser = new ArgumentParser();
        Scanner in = new Scanner(System.in);
        ActionExecutor actionExecutor = new ActionExecutor();
        ActionParametersValidator validator = new ActionParametersValidator();
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
                System.out.println("Be careful! Error detected: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("So bad: " + e.getMessage());
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
    }

    private void printInvitation(boolean isSearchModeON) {
        if (!isSearchModeON)
            System.out.print(" Type command here([help] - display all possible commands and usage) _>");
        else {
            System.out.println("Please select number of desired search type and required parameters for it:");
            System.out.println("(example: 2 -t eventTitle )");
            System.out.println("[1]- search all events. No parameters. ");
            System.out.println("[2]- search event by title. Parameters: -title ");
            System.out.println("[0]- exit search mode. No parameters. ");
        }
    }

}

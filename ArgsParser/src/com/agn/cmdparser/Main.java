package com.agn.cmdparser;

import com.beust.jcommander.*;

import java.util.Scanner;

import static com.agn.cmdparser.ConstActionTypeEnum.*;

public class Main {

    public static void main(String[] args) {

        doParse(args);

    }

    private static void doParse(String[] args) {
        EventPrototype evPrototype = new EventPrototype();
        ArgumentParser argParser = new ArgumentParser();
        Scanner in = new Scanner(System.in);
        String strToParse = null;

        if (args.length > 0) {
            strToParse = args.toString(); // [Andr]: need check this!!
        }

        while (true) {
            if (strToParse == null) {
                System.out.print(" Type command here([help] - display all possible commands and usage) _>");
                strToParse = in.nextLine();
                if (strToParse.isEmpty()) {
                    strToParse = null;
                    continue;
                }
            }
            try {
                evPrototype = argParser.parse(strToParse);
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
            if (evPrototype != null) {
                if (doValidate(evPrototype))
                    doAction(evPrototype);
            }
            evPrototype = null;
        }

    }


    // TODO: add informative Exception message
    private static boolean doValidate(EventPrototype evPrototype) {
        boolean isValidateOk = true;
        System.out.println(" validate phase starts....");

        if (evPrototype == null)
            return false;
        //1. primary checking ALL EVENT fields
        if (evPrototype.getTitle() == null
                || evPrototype.getTimeStart() == null
                || false) {
            return false;
        }
        if (evPrototype.getTitle().isEmpty()
                || evPrototype.getTimeStart().isEmpty()
                || false) {
            return false;
        }
        //[Andr]:
        //2. secondary checking fields values constraints
        //2.1. cast section
        //  (DateTime) evPrototype.getTimeStart()
        //  (DateTime) evPrototype.getTimeEnd()
        //2.2. logic checking
        //   if (evPrototype.getTimeStart() > evPrototype.getTimeEnd()) return false;

        return isValidateOk;
    }

    private static void doAction(EventPrototype evPrototype) {

        switch (evPrototype.getActionTypeId()) {
            case CREATE_CODE:
                //call service.createEvent(evPrototype.getTitle(), ...)
                System.out.println("Event <" + evPrototype.getTitle() + "> is created! Congratulations!");
            case UPDATE_CODE:
                // call update ...
            case DELETE_CODE:
                // call delete etc.
                // And so on...
        }
    }
}
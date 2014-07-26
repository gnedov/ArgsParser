package com.agn.cmdparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.*;

import static com.agn.cmdparser.ConstActionTypeEnum.*;


public class ArgumentParser {
    private boolean isBreak;
    private JCommander jc;
    private Map<String, IActionTypeParameters> mapRun = new HashMap<>();

    public ArgumentParser() {
        jc = new JCommander();
        isBreak = false;
        initJCommander(jc);
    }

    private void initJCommander(JCommander jc) {

        if (jc == null) {
            jc = new JCommander();
        }

        ParseCreate pCreate = new ParseCreate();
        ParseUpdate pUpdate = new ParseUpdate();
        ParseDelete pDelete = new ParseDelete();
        ParseDisplay pDisplay = new ParseDisplay();
        ParseHelp pHelp = new ParseHelp();
        ParseLoad pLoad = new ParseLoad();
        CloseApp closeApp = new CloseApp();

        mapRun.put(CREATE, pCreate);
        mapRun.put(UPDATE, pUpdate);
        mapRun.put(DELETE, pDelete);
        mapRun.put(DISPLAY, pDisplay);
        mapRun.put(LOAD, pLoad);
        mapRun.put(HELP, pHelp);
        mapRun.put(EXIT, closeApp);

        jc.addCommand(pCreate);
        jc.addCommand(pUpdate);
        jc.addCommand(pDelete);
        jc.addCommand(pDisplay);
        jc.addCommand(pLoad);
        jc.addCommand(pHelp);
        jc.addCommand(closeApp);
    }

    public EventPrototype parse(String strToParse) {
        String commandName;
        String[] args;
        args = strToParse.split(" ");
        try {
            jc.parse(args);

        } catch (Exception e) {
            System.out.println("something error with parsing: " + e.getMessage());
            return null;
        }

        commandName = jc.getParsedCommand();
        ConstActionTypeEnum cateAction = new ConstActionTypeEnum();

        switch (cateAction.commandCode.get(commandName)) {
            case EXIT_CODE:
                isBreak = true;
                return null;

            default:
                return mapRun.get(commandName).getParsedActionTypeParameters();
        }

    }

    @Parameters(commandNames = {CREATE}, commandDescription = "- creates a new event from console input data.")
    private class ParseCreate implements IActionTypeParameters {
        @Parameter(names = {"-t", "--title"}, variableArity = true, description = " set event title")
        private List<String> evTitleList = new ArrayList<>();

        @Parameter(names = {"-d", "-desc", "--description"}, description = " set event description")
        private String evDescription = "emptyDesc";

        @Parameter(names = {"-a", "-att", "--attenders"}, description = " set event attenders")
        private String evAttenders = "emptyAtt";

        @Parameter(names = {"-ts", "--timestart"}, description = " set event start time")
        private String evTimeStart = "noTimeStart";

        @Parameter(names = {"-te", "--timeend"}, description = " set event end time")
        private String evTimeEnd = "noTimeEnd";

        @Parameter(names = "--help", help = false)
        private boolean help = false;

        @Override
        public EventPrototype getParsedActionTypeParameters() {

            if (help) {
                jc.usage(CREATE);
                help = false;
                return null;
            }

            System.out.println(" parsing create command...");
            String evTitle = getWholeString(evTitleList);
            return new EventPrototype(CREATE_CODE, evTitle, evDescription, evAttenders,
                    evTimeStart, evTimeEnd);
        }
    }

    @Parameters(commandNames = {UPDATE}, commandDescription = "- updates the event by title.")
    private class ParseUpdate implements IActionTypeParameters {
        @Parameter(names = {"-t", "--title"}, description = " set event title")
        private String evTitle;

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            System.out.println(" parsing update command...");
            return new EventPrototype();
        }
    }

    @Parameters(commandNames = {DELETE}, commandDescription = "- delete event.")
    private class ParseDelete implements IActionTypeParameters {

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            return null;
        }
    }

    @Parameters(commandNames = {DISPLAY, "quit", "break"}, commandDescription = "- show event.")
    private class ParseDisplay implements IActionTypeParameters {

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            return null;
        }
    }

    @Parameters(commandNames = {LOAD}, commandDescription = "- load event from file.")
    private class ParseLoad implements IActionTypeParameters {

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            return null;
        }
    }

    @Parameters(commandNames = {HELP}, commandDescription = "- show help.")
    private class ParseHelp implements IActionTypeParameters {

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            //[Andr]:  show help file here

            jc.usage();
            return null;
        }
    }

    @Parameters(commandNames = {EXIT, "quit", "break"}, commandDescription = "- close application.")
    private class CloseApp implements IActionTypeParameters {

        @Override
        public EventPrototype getParsedActionTypeParameters() {
            return null;
        }
    }

    public void showUsage() {
        System.out.println(" how to use");
        jc.usage();
    }

    public boolean isBreak() {
        return this.isBreak;
    }

    private String getWholeString(List<String> arrStr) {
        StringBuilder sb = new StringBuilder();
        for (String s : arrStr) {
            sb.append(s);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

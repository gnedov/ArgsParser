package com.agn.cmdparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import java.util.*;

import static com.agn.cmdparser.ConstActionTypeEnum.*;


public class ArgumentParser {
    private boolean isBreak;
    private boolean isSearchMode;
    private JCommander jc;
    private Map<String, IActionTypeParameters> mapRun = new HashMap<>();
    private final String DELIMITER_SPACE = " ";
    private static final String SEARCHENGINE_NUMBER = "-searchEngineNumber";

    public ArgumentParser() {
        jc = new JCommander();
        isBreak = false;
        isSearchMode = false;
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
        ParseSearch pSearch = new ParseSearch();
        CloseApp closeApp = new CloseApp();

        mapRun.put(CREATE, pCreate);
        mapRun.put(UPDATE, pUpdate);
        mapRun.put(DELETE, pDelete);
        mapRun.put(DISPLAY, pDisplay);
        mapRun.put(LOAD, pLoad);
        mapRun.put(HELP, pHelp);
        mapRun.put(SEARCH, pSearch);
        mapRun.put(EXIT, closeApp);

        jc.addCommand(pCreate);
        jc.addCommand(pUpdate);
        jc.addCommand(pDelete);
        jc.addCommand(pDisplay);
        jc.addCommand(pLoad);
        jc.addCommand(pHelp);
        jc.addCommand(pSearch);
        jc.addCommand(closeApp);
    }

    public EventParameters parse(String strToParse) {
        String commandName;
        String[] args;
        if (isSearchModeON()) {
            strToParse = SEARCH + DELIMITER_SPACE + SEARCHENGINE_NUMBER + DELIMITER_SPACE + strToParse;
        }

        args = strToParse.split(DELIMITER_SPACE);
        try {
            jc.parse(args);

        } catch (Exception e) {
            System.out.println("some error with parsing: " + e.getMessage());

            return null;
        }

        commandName = jc.getParsedCommand();
        ConstActionTypeEnum cateAction = new ConstActionTypeEnum();

        switch (cateAction.commandCode.get(commandName)) {
            case SEARCH_CODE:
                if (isSearchModeON()) {
                    setSearchModeOFF();
                    return mapRun.get(commandName).getParsedActionTypeParameters();

                } else {
                    setSearchModeON();                      // sets search mode ON
                    return new EventParameters(SEARCH_CODE);// and wait user to select search engine with parameters.
                }

            case EXIT_CODE:
                isBreak = true;
                return null;

            default:
                return mapRun.get(commandName).getParsedActionTypeParameters();
        }

    }

    // write once - use everywhere.
    //this class contains parameters for create, update commands.
    private class DelegateParameters {
        @Parameter(names = {"-t", "--title"}, variableArity = true,
                description = " set event title")
        private List<String> titleList = new ArrayList<>();

        @Parameter(names = {"-d", "-desc", "--description"}, variableArity = true,
                description = " set event description")
        private List<String> descriptionList = new ArrayList<>();

        @Parameter(names = {"-a", "-att", "--attenders"}, variableArity = true,
                description = " set event attenders")
        private List<String> attendersList = new ArrayList<>();

        @Parameter(names = {"-ts", "--timestart"}, description = " set event start time")
        private List<String> timeStart = new ArrayList<>();

        @Parameter(names = {"-te", "--timeend"}, description = " set event end time")
        private List<String> timeEnd = new ArrayList<>();

        @Parameter(names = "-help", help = false)
        private List<Boolean> help = new ArrayList<>();

        public void clearAll() {
            titleList.clear();
            descriptionList.clear();
            attendersList.clear();
            timeStart.clear();
            timeEnd.clear();
            help.clear();
        }
    }

    @Parameters(commandNames = {CREATE}, commandDescription = "- creates a new event from console input data.")
    private class ParseCreate implements IActionTypeParameters {
        @ParametersDelegate
        private DelegateParameters delegate = new DelegateParameters();

        @Override
        public EventParameters getParsedActionTypeParameters() {
            EventParameters evParameters;

            List<String> titleList = new ArrayList<>();
            List<String> descriptionList = new ArrayList<>();
            List<String> timeList = new ArrayList<>();

            System.out.println(" parsing create command...");

            titleList.add(0, getWholeString(delegate.titleList));
            descriptionList.add(0, getWholeString(delegate.descriptionList));
            if (delegate.timeStart.size() > 0)
                timeList.add(0, delegate.timeStart.get(0));
            if (delegate.timeEnd.size() > 0)
                timeList.add(1, delegate.timeEnd.get(0));

            evParameters = new EventParameters(CREATE_CODE, titleList, descriptionList, delegate.attendersList, timeList);

            delegate.clearAll();
            return evParameters;
        }
    }

    @Parameters(commandNames = {UPDATE}, commandDescription = "- updates the event by title.")
    private class ParseUpdate implements IActionTypeParameters {
        @ParametersDelegate
        private DelegateParameters delegate = new DelegateParameters();

        @Override
        public EventParameters getParsedActionTypeParameters() {
            System.out.println(UPDATE + " command is under construction.");
            return new EventParameters();
        }
    }

    @Parameters(commandNames = {DELETE}, commandDescription = "- delete event.")
    private class ParseDelete implements IActionTypeParameters {

        @Override
        public EventParameters getParsedActionTypeParameters() {
            System.out.println(DELETE + " command is under construction.");
            return null;
        }
    }

    @Parameters(commandNames = {DISPLAY}, commandDescription = "- show event.")
    private class ParseDisplay implements IActionTypeParameters {

        @Override
        public EventParameters getParsedActionTypeParameters() {
            System.out.println(DISPLAY + " command is under construction.");
            return null;
        }
    }

    @Parameters(commandNames = {LOAD}, commandDescription = "- load event from file.")
    private class ParseLoad implements IActionTypeParameters {

        @Override
        public EventParameters getParsedActionTypeParameters() {
            System.out.println(LOAD + " command is under construction.");
            return null;
        }
    }

    @Parameters(commandNames = {HELP}, commandDescription = "- show help.")
    private class ParseHelp implements IActionTypeParameters {

        @Override
        public EventParameters getParsedActionTypeParameters() {
            //[Andr]: todo  show help file here

            jc.usage();
            return null;
        }
    }

    @Parameters(commandNames = {EXIT, "quit", "break"}, commandDescription = "- close application.")
    private class CloseApp implements IActionTypeParameters {

        @Override
        public EventParameters getParsedActionTypeParameters() {
            return null;
        }
    }

    @Parameters(commandNames = {SEARCH}, commandDescription = "- search one or several events.")
    private class ParseSearch implements IActionTypeParameters {

        @ParametersDelegate
        private DelegateParameters delegate = new DelegateParameters();

        @Parameter(names = SEARCHENGINE_NUMBER, description = " the number of search engine ", hidden = true)
        private List<Integer> searchEngine = new ArrayList<>(1);


        @Override
        public EventParameters getParsedActionTypeParameters() {
            System.out.println(" parsing search command...");
            EventParameters eventParameters;

            switch (searchEngine.get(0)) {
                case 1:  // search all events
                    eventParameters = new EventParameters(SEARCH_ALL_CODE);
                    break;

                case 2:  // search by title
                    List<String> titleList = new ArrayList<>(1);

                    titleList.add(0, getWholeString(delegate.titleList));
                    eventParameters = new EventParameters(SEARCH_BY_TITLE_CODE, titleList, null, null, null);
                    break;

                case 0: // proper exit from search mode
                    System.out.println("Search mode disabled.");
                    eventParameters = null;
                    break;

                default:
                    System.out.println("Wrong number! Please choose one of the available.");
                    setSearchModeON();
                    eventParameters = new EventParameters(SEARCH_CODE); // continue search mode
            }
            delegate.clearAll();
            searchEngine.clear();

            return eventParameters;
        }
    }

    public void showUsage() {
        System.out.println(" how to use");
        jc.usage();
    }

    public boolean isBreak() {
        return this.isBreak;
    }

    private void setSearchModeON() {
        this.isSearchMode = true;
    }

    private void setSearchModeOFF() {
        this.isSearchMode = false;
    }

    public boolean isSearchModeON() {
        return this.isSearchMode;
    }

    private String getWholeString(List<String> arrStr) {
        StringBuilder sb = new StringBuilder();
        for (String s : arrStr) {
            sb.append(s);
            sb.append(DELIMITER_SPACE);
        }
        if (arrStr.size() >= 1)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}

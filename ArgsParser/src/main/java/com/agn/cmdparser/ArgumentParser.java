package com.agn.cmdparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agn.cmdparser.ConstActionTypeEnum.*;


public class ArgumentParser {
    private boolean isSearchMode;
    private JCommander jc;
    private Map<String, IActionTypeParameters> mapRun = new HashMap<>();
    private static final String DELIMITER_SPACE = " ";
    private static final String SEARCHENGINE_NUMBER = "-searchEngineNumber";
    private static final Logger LOG = Logger.getLogger(ArgumentParser.class);

    public ArgumentParser() {
        if (LOG.isDebugEnabled())
            LOG.debug("setup new instance ");
        jc = new JCommander();
        isSearchMode = false;
        initJCommander(jc);
    }

    private void initJCommander(JCommander jc) {

        if (jc == null) {
            jc = new JCommander();
        }
        if (LOG.isDebugEnabled())
            LOG.debug("got the new JCommander instance.");
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
        if (LOG.isDebugEnabled())
            LOG.debug("filled JCommander instance with handler objects: " + mapRun.size() + " commands.");
    }

    public EventParameters parse(String strToParse) {
        String commandName;
        String[] args;
        if (isSearchModeON()) {
            LOG.info("parsing search engine #" + strToParse.trim().split(DELIMITER_SPACE)[0]);
            strToParse = SEARCH + DELIMITER_SPACE + SEARCHENGINE_NUMBER + DELIMITER_SPACE + strToParse.trim();
        }
        LOG.info("string to parse is: " + strToParse);
        args = strToParse.trim().split(DELIMITER_SPACE);
        try {
            if (LOG.isDebugEnabled())
                LOG.debug("try to parse by JCommander");
            jc.parse(args);

        } catch (Exception e) {
            System.out.println("some error with parsing: " + e.getMessage());
            LOG.warn("some error with parsing: " + e.getMessage());
            return null;
        }

        commandName = jc.getParsedCommand();
        ConstActionTypeEnum cateAction = new ConstActionTypeEnum();

        if (LOG.isDebugEnabled())
            LOG.debug("parsed command is \"" + commandName + "\"");

        switch (cateAction.commandCode.get(commandName)) {
            case SEARCH_CODE:
                if (isSearchModeON()) {
                    setSearchModeOFF();
                    return mapRun.get(commandName).getParsedActionTypeParameters();

                } else {
                    setSearchModeON();                      // sets search mode ON
                    return new EventParameters(SEARCH_CODE);// and wait user to select search engine with parameters.
                }

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
            if (LOG.isDebugEnabled())
                LOG.debug("Parser cleared its variables.");
        }
    }

    @Parameters(commandNames = {CREATE}, commandDescription = "- creates a new event from console input data.")
    private class ParseCreate implements IActionTypeParameters {
        @ParametersDelegate
        private DelegateParameters delegate = new DelegateParameters();
        private final Logger LOG = Logger.getLogger(ParseCreate.class);

        @Override
        public EventParameters getParsedActionTypeParameters() {
            EventParameters evParameters;
            String title;
            String description;
            List<String> timeList = new ArrayList<>();

            if (LOG.isDebugEnabled())
                LOG.debug(" parsing create command...");

            title = getWholeString(delegate.titleList);
            description = getWholeString(delegate.descriptionList);
            if (delegate.timeStart.size() > 0)
                timeList.add(0, delegate.timeStart.get(0));
            if (delegate.timeEnd.size() > 0)
                timeList.add(1, delegate.timeEnd.get(0));
            if (LOG.isDebugEnabled())
                LOG.debug("got EventParameters instance with:");
            evParameters = new EventParameters(CREATE_CODE, title, description, delegate.attendersList, timeList);

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
            return new EventParameters(EXIT_CODE);
        }
    }

    @Parameters(commandNames = {SEARCH}, commandDescription = "- search one or several events.")
    private class ParseSearch implements IActionTypeParameters {
        private final Logger LOG = Logger.getLogger(ParseSearch.class);

        @ParametersDelegate
        private DelegateParameters delegate = new DelegateParameters();

        @Parameter(names = SEARCHENGINE_NUMBER, description = " the number of search engine ", hidden = true)
        private List<Integer> searchEngine = new ArrayList<>(1);


        @Override
        public EventParameters getParsedActionTypeParameters() {
            if (LOG.isDebugEnabled())
                LOG.debug(" parsing search command...");
            EventParameters eventParameters;
            int inputEngineId = searchEngine.get(0);

            switch (inputEngineId) {
                case 1:  // search all events
                    eventParameters = new EventParameters(SEARCH_ALL_CODE);
                    break;

                case 2:  // search by title
                    String titleList;
                    titleList = getWholeString(delegate.titleList);
                    eventParameters = new EventParameters(SEARCH_BY_TITLE_CODE, titleList, null, null, null);
                    break;

                case 0: // proper exit from search mode
                    System.out.println("Search mode disabled.");
                    eventParameters = null;
                    break;

                default:
                    System.out.println("Wrong number! Please choose one of the available.");
                    if (LOG.isDebugEnabled())
                        LOG.debug("user chose unsupported search engine #" + inputEngineId);
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

    private void setSearchModeON() {
        LOG.info("search mode is ON");
        this.isSearchMode = true;
    }

    private void setSearchModeOFF() {
        LOG.info("search mode is OFF");
        this.isSearchMode = false;
    }

    public boolean isSearchModeON() {
        return this.isSearchMode;
    }

    public static String getWholeString(List<String> arrStr) {
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

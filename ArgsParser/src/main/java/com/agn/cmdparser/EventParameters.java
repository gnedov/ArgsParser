package com.agn.cmdparser;

import org.apache.log4j.Logger;

import java.util.List;

public class EventParameters {
    private int actionTypeId;
    private List<String> parameters;
    private String title;
    private String description;
    private List<String> attendersList;
    private List<String> timeList;
    private static final Logger LOG = Logger.getLogger(EventParameters.class);

    public EventParameters() {
        this.actionTypeId = 0;
        this.parameters = null;
    }

    public EventParameters(int actionTypeId) {
        this.actionTypeId = actionTypeId;
        if (LOG.isDebugEnabled())
            LOG.debug("actionTypeId: " + getActionTypeId());
    }

    public EventParameters(int actionTypeId, List<String> parameters) {
        this.actionTypeId = actionTypeId;
        this.parameters = parameters;
    }

    public EventParameters(int actionTypeId, String title, String description,
                           List<String> attendersList, List<String> timeList) {
        this.actionTypeId = actionTypeId;
        this.title = title;
        this.description = description;
        this.attendersList = attendersList;
        this.timeList = timeList;
        if (LOG.isDebugEnabled())
            LOG.debug("actionTypeId: " + getActionTypeId() + ";\n title: " + getTitle() +
                    ";\n description: " + getDescription() +
                    ";\n attendersList: " + getAttenders().toString() +
                    ";\n timeList: " + getTimeList().toString());
    }

    public int getActionTypeId() {
        return actionTypeId;
    }

    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "EventParameters{" +
                "actionTypeId=" + actionTypeId +
                ", parameters=" + parameters +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAttenders() {
        return attendersList;
    }

    public List<String> getTimeList() {
        return timeList;
    }

}

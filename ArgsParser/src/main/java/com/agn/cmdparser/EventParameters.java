package com.agn.cmdparser;

import java.util.List;

public class EventParameters {
    private int actionTypeId;
    private List<String> parameters;
    private String title;
    private String description;
    private List<String> attendersList;
    private List<String> timeList;

    public EventParameters() {
        this.actionTypeId = 0;
        this.parameters = null;
    }

    public EventParameters(int actionTypeId) {
        this.actionTypeId = actionTypeId;
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

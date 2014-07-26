package com.agn.cmdparser;

import java.util.List;

public class EventPrototype {
    private int actionTypeId;
    private List<String> parameters;
    private String title;
    private String description;
    private String attenders;
    private String timeStart;
    private String timeEnd;

    public EventPrototype() {
        this.actionTypeId = 0;
        this.parameters = null;
    }

    public EventPrototype(int actionTypeId, List<String> parameters) {
        this.actionTypeId = actionTypeId;
        this.parameters = parameters;
    }

    public EventPrototype(int actionTypeId, String evTitle, String evDescription,
                          String evAttenders, String timeStart, String timeEnd) {
        this.actionTypeId = actionTypeId;
        this.title = evTitle;
        this.description = evDescription;
        this.attenders = evAttenders;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getActionTypeId() {
        return actionTypeId;
    }

    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "EventPrototype{" +
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

    public String getAttenders() {
        return attenders;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
}

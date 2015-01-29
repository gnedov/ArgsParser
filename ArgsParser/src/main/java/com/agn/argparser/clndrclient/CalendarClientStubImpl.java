package com.agn.argparser.clndrclient;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalendarClientStubImpl implements CalendarClient {
    List<String> strList = new ArrayList<>();
    private static final Logger LOG = Logger.getLogger(CalendarClientStubImpl.class);

    @Override
    public Collection getLastSearchResult() {
        if (LOG.isDebugEnabled())
            LOG.debug("call getLastSearchResult() returns list: " + strList);
        return strList;
    }

    @Override
    public void searchAll() {
        if (LOG.isDebugEnabled())
            LOG.debug("call searchAll() method.");
        strList.clear();
        strList.add("ssew");
        strList.add("fdfg");
        strList.add("gyjtyj");
    }

    @Override
    public void searchByTitle(String eventTitle) {
        if (LOG.isDebugEnabled())
            LOG.debug("call searchByTitle() with: " + eventTitle);
        strList.clear();
        strList.add(eventTitle + " :1");
        strList.add(eventTitle + " :2");
    }
}

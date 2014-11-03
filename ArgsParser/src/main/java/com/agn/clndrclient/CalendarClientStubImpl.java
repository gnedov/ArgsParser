package com.agn.clndrclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalendarClientStubImpl implements CalendarClient{
    private List<String> strList = new ArrayList<>();

    @Override
    public Collection getLastSearchResult() {

        return strList;
    }

    @Override
    public void searchAll() {
        strList.clear();
        strList.add("ssew");
        strList.add("fdfg");
        strList.add("gyjtyj");
    }

    @Override
    public void searchByTitle(String eventTitle) {
        strList.clear();
        strList.add(eventTitle + " :1");
        strList.add(eventTitle + " :2");
    }
}

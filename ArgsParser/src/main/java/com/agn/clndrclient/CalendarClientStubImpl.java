package com.agn.clndrclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalendarClientStubImpl implements CalendarClient{
    @Override
    public Collection getSearchResult() {
        return null;
    }

    @Override
    public Collection searchAll() {
        List<String> strList = new ArrayList<>();
        strList.add("ssew");
        strList.add("fdfg");
        strList.add("gyjtyj");
        return strList;
    }
}

package com.agn.clndrclient;

import java.util.Collection;

public interface CalendarClient {
    Collection getLastSearchResult();

    void searchAll();
    
    void searchByTitle(String eventTitle);

}

package com.franco.listener;

import com.franco.event.AddEvent;
import com.franco.group.MatchGroup;

import java.util.EventObject;

public class AddMatchListener implements MatchListener {

    @Override
    public void handleEvent(EventObject event) {
        if(event instanceof AddEvent) {
            MatchGroup group = (MatchGroup) event.getSource();
            group.onHandleAddEvent();
        }
    }
}

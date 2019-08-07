package com.franco.listener;

import com.franco.event.IdleEvent;
import com.franco.group.MatchGroup;

import java.util.EventObject;

public class IdleMatchListener implements MatchListener {

    @Override
    public void handleEvent(EventObject event) {
        if(event instanceof IdleEvent) {
            MatchGroup group = (MatchGroup) event.getSource();
            group.onHandleIdleEvent();
        }
    }
}

package com.franco.group;

import com.franco.domain.Matchable;
import com.franco.domain.Matcher;
import com.franco.event.AddEvent;
import com.franco.listener.MatchListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class AbstractMatchGroup implements MatchGroup {

    protected List<Matchable> waitingGroup;
    protected Matcher matcher;
    private Thread matchThread;
    private List<MatchListener> listeners;

    public AbstractMatchGroup(List waitingGroup, Matcher matcher) {
        this.waitingGroup = waitingGroup;
        this.matcher = matcher;
        this.listeners = new ArrayList<>();
    }

    @Override
    public boolean add(Matchable obj) {
        System.out.println(obj);
        synchronized (waitingGroup) {
            if(waitingGroup.size() == 0) {
                pushEvent(new AddEvent(this));
            }
            waitingGroup.add(obj);
        }
        return true;
    }

    @Override
    public final void pushEvent(EventObject object) {
        for(MatchListener listener : listeners) {
            listener.handleEvent(object);
        }
    }

    @Override
    public final MatchGroup addListener(MatchListener matchListener) {
        listeners.add(matchListener);
        return this;
    }

    @Override
    public void onHandleAddEvent() {
        if(matchThread == null || matchThread.isInterrupted()) {
            matchThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while(true) {
                        try {
                            match();
                            Thread.currentThread().sleep(500);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            });
            System.out.println("开始监控线程");
            matchThread.start();
        }
    }

    @Override
    public int match() {
        return 0;
    }

    @Override
    public void notifyMatcher(List<Matchable> matchers) {

    }
}

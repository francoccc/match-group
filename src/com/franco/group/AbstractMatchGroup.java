package com.franco.group;

import com.franco.domain.Matchable;
import com.franco.domain.Matcher;
import com.franco.event.AddEvent;
import com.franco.listener.MatchListener;
import com.franco.strategy.MatchStrategy;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class AbstractMatchGroup implements MatchGroup {

    protected List<Matchable> waitingGroup;
    protected Matcher matcher;
    protected MatchStrategy matchStrategy;
    private Thread matchThread;
    private List<MatchListener> listeners;

    public AbstractMatchGroup(List waitingGroup,
                              Matcher matcher,
                              MatchStrategy matchStrategy) {
        this.waitingGroup = waitingGroup;
        this.matcher = matcher;
        this.matchStrategy = matchStrategy;
        matchStrategy.setMatcher(matcher);
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
                            System.out.println("结束监控线程");
                        }
                    }
                }
            });
            System.out.println("开始监控线程");
            matchThread.start();
        }
    }

    @Override
    public void onHandleIdleEvent() {
        if(matchThread != null && !matchThread.isInterrupted()) {
            matchThread.interrupt();
        }
        matchThread = null;
    }

    @Override
    public int match() {
        return 0;
    }

    @Override
    public void notifyMatcher(List<Matchable> matchers) {

    }

    public MatchStrategy getMatchStrategy() {
        return matchStrategy;
    }

    public void setMatchStrategy(MatchStrategy matchStrategy) {
        this.matchStrategy = matchStrategy;
    }
}

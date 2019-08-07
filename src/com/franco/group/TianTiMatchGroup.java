package com.franco.group;

import java.util.ArrayList;
import java.util.List;

import com.franco.domain.MatchPlayer;
import com.franco.domain.Matchable;
import com.franco.domain.Matcher;
import com.franco.event.IdleEvent;
import com.franco.strategy.MatchStrategy;

public class TianTiMatchGroup extends AbstractMatchGroup {

    public TianTiMatchGroup(List waitingQueue,
                            Matcher<MatchPlayer> matcher,
                            MatchStrategy matchStrategy) {
        super(waitingQueue, matcher, matchStrategy);
    }

    @Override
    public int match() {
        List<Matchable> candidates = new ArrayList<>();
        synchronized (waitingGroup) {
            try {
                matchStrategy.doMatch(waitingGroup, candidates);
            } catch (Exception e) {
                System.out.println("进行匹配产生异常！");
            } finally {
                notifyMatcher(candidates);
                for(Matchable matchable : candidates) {
                    waitingGroup.remove(matchable);
                }
            }
            if(waitingGroup.isEmpty()) {
                pushEvent(new IdleEvent(this));
            }
        }
        return candidates.size();
    }

    @Override
    public void notifyMatcher(List<Matchable> matchers) {
        for(Matchable matchable : matchers) {
            System.out.println(matchable + " 匹配成功！");
        }
    }
}

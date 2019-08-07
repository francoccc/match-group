package com.franco.group;

import java.util.ArrayList;
import java.util.List;

import com.franco.common.Tuple;
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
        List<Tuple<Matchable, Matchable>> candidates = new ArrayList<>();
        synchronized (waitingGroup) {
            try {
                matchStrategy.doMatch(waitingGroup, candidates);
            } catch (Exception e) {
                System.out.println("进行匹配产生异常！");
            } finally {
                notifyMatcher(candidates);
                for(Tuple tuple : candidates) {
                    waitingGroup.remove(tuple.left);
                    waitingGroup.remove(tuple.right);
                }
            }
            if(waitingGroup.isEmpty()) {
                pushEvent(new IdleEvent(this));
            }
        }
        if(candidates.size() != 0) {
            System.out.println("匹配成功玩家:" + candidates.size());
        }
        return candidates.size();
    }

    @Override
    public void notifyMatcher(List<Tuple<Matchable, Matchable>> matchers) {
        for(Tuple tuple : matchers) {
            System.out.println(tuple.left.toString()
                    + tuple.right.toString()
                    + " 匹配成功！");
        }
    }
}

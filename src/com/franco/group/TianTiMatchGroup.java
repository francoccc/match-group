package com.franco.group;

import java.util.ArrayList;
import java.util.List;

import com.franco.domain.MatchPlayer;
import com.franco.domain.Matchable;
import com.franco.domain.Matcher;

public class TianTiMatchGroup extends AbstractMatchGroup {

    public TianTiMatchGroup(List waitingQueue, Matcher<MatchPlayer> matcher) {
        super(waitingQueue, matcher);
    }

    @Override
    public int match() {
        List<Matchable> candidates = new ArrayList<>();
        synchronized (waitingGroup) {
            for(int i = 0; i < waitingGroup.size(); i++) {
                if(matcher.match(waitingGroup.get(0), waitingGroup.get(1))) {
                    candidates.add(waitingGroup.get(0));
                    candidates.add(waitingGroup.get(1));
                }
            }
            for(Matchable matchable : candidates) {
                waitingGroup.remove(matchable);
            }
            notifyMatcher(candidates);
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

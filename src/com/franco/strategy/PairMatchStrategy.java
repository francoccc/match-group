package com.franco.strategy;

import com.franco.domain.MatchPlayer;
import com.franco.domain.Matchable;
import com.franco.domain.Matcher;

import java.util.*;

public class PairMatchStrategy implements MatchStrategy<MatchPlayer> {

    private final int ONLY_ONE_PLAYER = 1;
    private Matcher matcher;
    private Map<MatchPlayer, MatchPlayer> pairs;
    private Set<Integer> vis;
    private List<MatchPlayer> matchPlayers;

    @Override
    public void doMatch(List<MatchPlayer> matchPlayers,
                        List<MatchPlayer> candidates) {
        pairs = new HashMap<>();
        vis = new HashSet<>();
        this.matchPlayers = matchPlayers;
        if(matchPlayers.size() == ONLY_ONE_PLAYER) {
            return;
        }
        if(matcher == null) {
            throw new RuntimeException("matcher is null");
        }
        int l = matchPlayers.size() / 2;
        for(int i = 0; i < l; i++) {
            // 给攻击方匹配
            // 攻击方选择更大
            for(int j = l; j < matchPlayers.size(); j++) {
                if (canMatch(matchPlayers.get(i), matchPlayers.get(j), l)) {
                    pairs.put(matchPlayers.get(i), matchPlayers.get(j));
                    pairs.put(matchPlayers.get(i), matchPlayers.get(j));
                }
            }
        }
        for(MatchPlayer key : pairs.keySet()) {
            if(!candidates.contains(key)) {
                candidates.add(key);
            }
        }
    }

    /**
     * 匹配
     * @param p1 待匹配对象
     * @param p2 匹配对象
     * @return
     */
    private boolean canMatch(MatchPlayer p1, MatchPlayer p2, int l) {
        if(!pairs.containsKey(p2)) {
            return matcher.match(p1, p2);
        }
        // 此节点无法再生成增广路
        if(vis.contains(p2.getUuid())) {
            return false;
        }
        // 还有一次机会
        MatchPlayer p3 = pairs.get(p2);
        int i = matchPlayers.indexOf(p3);
        if(i < l) {
            i = l;
        } else {
            i = 0;
        }

        while(i < l || i < matchPlayers.size()) {
            MatchPlayer _p = matchPlayers.get(i);
            if (_p == p2) {
                i++;
                continue;
            }
            if(canMatch(p3, _p,l)) {
                pairs.put(p3, _p);
                pairs.put(_p, p3);
                return true;
            }
            i++;
        }
        vis.add(p2.getUuid());
        return false;
    }

    @Override
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }
}

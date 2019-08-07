package com.franco.strategy;

import com.franco.common.Tuple;
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
                        List<Tuple<MatchPlayer, MatchPlayer>> candidates) {
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
            Set<Integer> set = new HashSet<>();
            for(int j = l; j < matchPlayers.size(); j++) {
                set.add(matchPlayers.get(i).getUuid());
                set.add(matchPlayers.get(j).getUuid());
                if (canMatch(matchPlayers.get(i), matchPlayers.get(j), l, set)) {
                    pairs.put(matchPlayers.get(i), matchPlayers.get(j));
                    pairs.put(matchPlayers.get(j), matchPlayers.get(i));
                    break;
                }
                set.remove(matchPlayers.get(i).getUuid());
                set.remove(matchPlayers.get(j).getUuid());
            }
        }
        for(MatchPlayer key : pairs.keySet()) {
            candidates.add(new Tuple(key, pairs.get(key)));
        }
    }

    /**
     * 匹配
     * @param p1 待匹配对象
     * @param p2 匹配对象
     * @return
     */
    private boolean canMatch(MatchPlayer p1, MatchPlayer p2, int l, Set<Integer> t) {
        if(!matcher.match(p1, p2)) {
            return false;
        }
        if(!pairs.containsKey(p2)) {
            return true;
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
        t.add(p3.getUuid());
        while(i < l || i < matchPlayers.size()) {
            MatchPlayer _p = matchPlayers.get(i);
            if (_p == p2) {
                i++;
                continue;
            }
            if(t.contains(_p.getUuid())) {
                continue;
            }
            if(canMatch(p3, _p, l, t)) {
                pairs.put(p3, _p);
                pairs.put(_p, p3);
                return true;
            }
            i++;
        }
        t.remove(p3.getUuid());
        if(pairs.containsKey(p2)) {
            // 已经匹配队列中的
            vis.add(p2.getUuid());
            vis.add(pairs.get(p2).getUuid());
        }
        return false;
    }

    @Override
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }
}

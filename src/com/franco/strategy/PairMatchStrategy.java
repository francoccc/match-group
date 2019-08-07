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
    private Map<MatchPlayer, MatchPlayer> vis;
    private List<MatchPlayer> matchPlayers;

    @Override
    public void doMatch(List<MatchPlayer> matchPlayers,
                        List<Tuple<MatchPlayer, MatchPlayer>> candidates) {
        pairs = new HashMap<>();
        vis = new HashMap<>();
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
                    pairs.put(matchPlayers.get(j), matchPlayers.get(i));
                    vis.put(matchPlayers.get(i), matchPlayers.get(j));
                    break;
                }
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
    private boolean canMatch(MatchPlayer p1, MatchPlayer p2, int l) {
        if(!matcher.match(p1, p2)) {
            return false;
        }
        if(!pairs.containsKey(p2)) {
            return true;
        }
        // 此节点无法再生成增广路
        // 还有一次机会
        MatchPlayer p3 = pairs.get(p2);  // p3 一定为攻击方节点
        int i = matchPlayers.indexOf(vis.get(p3)) + 1; // i 为防御节点索引
        if(i < l) {
            System.out.println("Fatal error");
        } else {
            // do nothing
        }
        while(i < l || i < matchPlayers.size()) {
            MatchPlayer _p = matchPlayers.get(i);
            if(canMatch(p3, _p, l)) {
                pairs.put(p3, _p);
                pairs.put(_p, p3);
                vis.put(p3, _p); // p3 攻击方记录现在节点
                return true;
            }
            i++;
            vis.put(p3, _p); // p3 攻击方记录现在节点
        }
        return false;
    }

    @Override
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }
}

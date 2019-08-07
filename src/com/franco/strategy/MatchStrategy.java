package com.franco.strategy;

import com.franco.common.Tuple;
import com.franco.domain.Matcher;

import java.util.List;

public interface MatchStrategy<M> {

    void doMatch(List<M> matchPlayers, List<Tuple<M, M>> candidates);

    void setMatcher(Matcher matcher);
}

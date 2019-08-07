package com.franco.strategy;

import com.franco.domain.Matcher;

import java.util.List;

public interface MatchStrategy<M> {

    void doMatch(List<M> matchPlayers, List<M> candidates);

    void setMatcher(Matcher matcher);
}

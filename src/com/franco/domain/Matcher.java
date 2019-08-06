package com.franco.domain;

public interface Matcher<M> {

    boolean match(M o1, M o2);
}

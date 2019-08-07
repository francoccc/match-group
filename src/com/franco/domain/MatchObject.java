package com.franco.domain;

public abstract class MatchObject implements Matchable {

    /** 生成的时间戳 */
    private final long timeStamp = System.currentTimeMillis();

    public long getTimeStamp() {
        return timeStamp;
    }
}

package com.franco.domain;

public abstract class MatchObject implements Matchable {

    /** 生成的时间戳 */
    private long timeStamp;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

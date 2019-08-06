package com.franco.domain;

public class MatchPlayer extends MatchObject {

    private int uuid;

    private int win;

    public MatchPlayer(int uuid, int win) {
        this.uuid = uuid;
        this.win = win;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    @Override
    public String toString() {
        return "[" + uuid + ", " + win + "]";
    }
}

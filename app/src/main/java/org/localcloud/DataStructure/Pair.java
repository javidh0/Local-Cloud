package org.localcloud.DataStructure;

public class Pair<P, Q> {
    private P first;
    private Q second;

    public Pair(P first, Q second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {}

    public void setFirst(P first) {
        this.first = first;
    }

    public void setSecond(Q second) {
        this.second = second;
    }

    public P getFirst() {
        return first;
    }

    public Q getSecond() {
        return second;
    }
}

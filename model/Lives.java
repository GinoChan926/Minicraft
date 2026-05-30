package org.minicraft02160.model;

// Used for player lives behaviour
public final class Lives {
    private final int max;
    private int current;

    public Lives(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max lives must be > 0");
        }
        this.max = max;
        this.current = max;
    }

    public int current() {
        return current;
    }

    public int max() {
        return max;
    }

    public void decrement() {
        if (current > 0) {
            current -= 1;
        }
    }

    public void increment() {
        if (current < max) {
            current += 1;
        }
    }
}


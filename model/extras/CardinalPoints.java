package org.minicraft02160.model.extras;

public enum CardinalPoints {
    N(180), S(0), E(270), W(90);

    private int angle;

    private CardinalPoints(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }
}

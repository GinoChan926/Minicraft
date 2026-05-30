package org.minicraft02160.model.extras;

public enum ConsumableItem {
    WOOL("/wool.png"),
    FUR("/fur.png");

    private final String imagePath;

    ConsumableItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean getType() {
        return switch (this) {
            case WOOL -> true;
            case FUR -> false;
        };
    }
}

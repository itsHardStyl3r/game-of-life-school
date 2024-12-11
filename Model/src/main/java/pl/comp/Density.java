package pl.comp;

public enum Density {
    LOW,
    MEDIUM,
    HIGH,
    FULL;

    public float getDensity() {
        if (this == LOW) {
            return 0.1f;
        } else if (this == MEDIUM) {
            return 0.3f;
        } else if (this == HIGH) {
            return 0.5f;
        } else if (this == FULL) {
            return 1f;
        }
        return 0;
    }
}
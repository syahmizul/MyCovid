package com.syahmizul.mycovid;

public class Utils {

    // Clamps a value between a specific range.
    public static float Clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}

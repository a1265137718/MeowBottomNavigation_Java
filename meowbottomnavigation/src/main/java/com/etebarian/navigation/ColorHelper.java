package com.etebarian.navigation;

public class ColorHelper {

    public static int mixTwoColors(int a, int b, float ratio) {
        if (ratio > 1f) {
            ratio = 1f;
        } else if (ratio < 0f) {
            ratio = 0f;
        }
        float iRatio = 1.0f - ratio;

        int aA = (a >> 24 & 0xff);
        int aR = ((a & 0xff0000) >> 16);
        int aG = ((a & 0xff00) >> 8);
        int aB = (a & 0xff);

        int bA = (b >> 24 & 0xff);
        int bR = ((b & 0xff0000) >> 16);
        int bG = ((b & 0xff00) >> 8);
        int bB = (b & 0xff);

        int A = ((int) (aA * iRatio) + (int) (bA * ratio));
        int R = ((int) (aR * iRatio) + (int) (bR * ratio));
        int G = ((int) (aG * iRatio) + (int) (bG * ratio));
        int B = ((int) (aB * iRatio) + (int) (bB * ratio));

        return A << 24 | R << 16 | G << 8 | B;
    }
}
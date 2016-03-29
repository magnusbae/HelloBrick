package no.itera.lego.color;

public enum Color {
         
    GREY(18, 49, 25, 50, 19, 39), // Avrg: 42, 43, 28
    WHITE(77, 208, 75, 216, 71, 181), //171, 176, 150
    BLACK(3, 17, 3, 27, 2, 17), //11, 14, 10
    GREEN(13, 48, 42, 146, 13, 44), //38, 120, 35
    YELLOW(57, 193, 36, 129, 19, 61), //145, 96, 46
    RED(21, 65, 4, 24, 3, 18), //50, 16, 12
    BLUE(10, 41, 32, 128, 45, 143), //32, 100, 116
    UNDEFINED(-1,-1,-1,-1,-1,-1);

    int redLow, redHigh, greenLow, greenHigh, blueLow, blueHigh;

    Color(int redLow, int redHigh, int greenLow, int greenHigh, int blueLow, int blueHigh) {
        this.redLow = redLow;
        this.redHigh = redHigh;
        this.greenLow = greenLow;
        this.greenHigh = greenHigh;
        this.blueLow = blueLow;
        this.blueHigh = blueHigh;
    }

    public static Color valueOf(int r, int g, int b) {
        for (Color color : values()) {
            if (color.contains(r, g, b)) {
                return color;
            }
        }
        return UNDEFINED;
    }

    public boolean contains(int r, int g, int b) {
        boolean r_contains = this.redLow < r && this.redHigh >= r;
        boolean g_contains = this.greenLow < g && this.greenHigh >= g;
        boolean b_contains = this.blueLow < b && this.blueHigh >= b;

        return r_contains && g_contains && b_contains;
    }

    public static Color randomColor() {
        return values()[(int)Math.floor(Math.random() * values().length)];
    }
}
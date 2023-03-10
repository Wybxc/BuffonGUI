package cc.wybxc.buffongui;

public class Buffon {

    /**
     * 针
     */
    public record Needle(double x1, double y1, double x2, double y2) {
        /**
         * 检测针是否穿过线
         *
         * @param height    高度
         * @param lineSpace 两条平行线的间距
         * @return 是否穿过线
         */
        public boolean detectCrossLine(double height, double lineSpace) {
            for (double lineY = lineSpace / 2; lineY < height; lineY += lineSpace) {
                if ((y1 < lineY && y2 > lineY) || (y1 > lineY && y2 < lineY)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 进行 Buffon 投针实验
     *
     * @return 针的位置
     */
    public static Needle[] experiment(double width, double height, double lineSpace, int numOfNeedles) {
        var lines = new Needle[numOfNeedles];
        for (int i = 0; i < numOfNeedles; i++) {
            var x = Math.random() * width;
            var y = Math.random() * height;
            var angle = Math.random() * Math.PI;
            var x1 = x + Math.cos(angle) * lineSpace / 4;
            var y1 = y + Math.sin(angle) * lineSpace / 4;
            var x2 = x - Math.cos(angle) * lineSpace / 4;
            var y2 = y - Math.sin(angle) * lineSpace / 4;
            lines[i] = new Needle(x1, y1, x2, y2);
        }

        return lines;
    }

    /**
     * 计算 pi
     *
     * @param width     宽度
     * @param height    高度
     * @param lineSpace 两条平行线的间距
     * @param needles   针的位置
     * @return pi
     */
    public static double calculatePi(double width, double height, double lineSpace, Needle[] needles) {
        int numOfNeedles = needles.length;
        int numOfCrossLines = 0;
        for (Needle needle : needles) {
            if (needle.detectCrossLine(height, lineSpace)) {
                numOfCrossLines++;
            }
        }
        if (numOfCrossLines == 0) {
            return 0;
        }
        return (double) numOfNeedles / numOfCrossLines;
    }
}

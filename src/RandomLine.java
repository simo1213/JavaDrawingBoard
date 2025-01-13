import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RandomLine extends Graphical{
    static List<Line> drawList = new ArrayList<Line>();
    public static void addDrawList(Line line) {
        drawList.add(line);
    }
}

import java.awt.*;

public class Circle extends Graphical {     //圆圈类
    Circle(Color color,BasicStroke bs)
    {
        this.bs=bs;
        this.color=color;
        this.isFill=false;
    }
    public void draw(Graphics g) {      //画圆
        int r = Math.abs(x1 - x2) ;     //半径
        g.drawOval(x1, y1, r, r);
    }
    public void fill(Graphics g) {      //画圆
        int r = Math.abs(x1 - x2) ;     //半径
        g.fillOval(x1, y1, r, r);
    }
}

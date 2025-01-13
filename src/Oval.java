import java.awt.*;

public class Oval extends Graphical {     //椭圆类
    Oval(Color color,BasicStroke bs)
    {
        this.bs=bs;
        this.color=color;
        this.isFill=false;
    }
    public void draw(Graphics g) {      //画椭圆
        int height = Math.abs(y1 - y2) ;    //长，要取绝对值保证大于等于0
        int width = Math.abs(x1 - x2) ;     //宽
        g.drawOval(x1, y1, width, height);
    }
    public void fill(Graphics g){
        int height = Math.abs(y1 - y2) ;    //长，要取绝对值保证大于等于0
        int width = Math.abs(x1 - x2) ;     //宽
        g.fillOval(x1, y1, width, height);
    }
}
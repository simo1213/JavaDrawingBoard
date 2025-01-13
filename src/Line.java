import java.awt.*;

public class Line extends Graphical {   //直线类
    int lineType;//画笔类2or橡皮类1or图形类0
    Line(Color color,BasicStroke bs)
    {
        this.bs=bs;
        this.color=color;
        this.isFill=false;
        this.lineType=0;
    }
    Line(Color color,BasicStroke bs,int lineType)
    {
        this.bs=bs;
        this.color=color;
        this.isFill=false;
        this.lineType=lineType;
    }
    public void draw(Graphics g) {      //画直线
        g.drawLine(x1, y1, x2, y2);
    }
}
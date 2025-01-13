import java.awt.*;

public class Rectangle extends Graphical{   //矩形框类
    Rectangle(Color color,BasicStroke bs)
    {
        this.bs=bs;
        this.color=color;
        this.isFill=false;
    }
    public void draw(Graphics g) {      //画矩形
        int height = Math.abs(y1 - y2) ;    //长，要取绝对值保证大于等于0
        int width = Math.abs(x1 - x2) ;     //宽
        g.drawRect(x1, y1, width, height);
    }
    public void fill(Graphics g) {      //画矩形
        int height = Math.abs(y1 - y2) ;    //长，要取绝对值保证大于等于0
        int width = Math.abs(x1 - x2) ;     //宽
        g.fillRect(x1, y1, width, height);
    }
}
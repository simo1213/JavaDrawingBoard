import java.awt.*;

public class Text extends Graphical {   //文本字符串类
    String text=null;//文本
    String familyName;//字体
    int size=20;//字号
    Text(String text,int tx,int ty,String familyName,Color color,int size)//构造函数
    {
        this.text=text;
        this.x1=tx;
        this.y1=ty;
        this.x2=tx+text.length()*15;
        this.y2=ty+size/2;
        this.familyName=familyName;
        this.color=color;
        this.size=size;
        this.isFill=false;
        this.bs=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
    }
    public void draw(Graphics g) {      //画字符串
        Font font = new Font(familyName, Font.BOLD, size);
        g.setFont(font);
        g.setColor(color);//获得文字颜色
        g.drawString(text, x1, y1);
    }
    public boolean isChosed(int x, int y) {
        //若鼠标点击的位置在图形范围内就可以选中图形
        return x >= x1 && x <= x2 && y >= y1-size/2 && y <= y2;
    }
}
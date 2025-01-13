import java.awt.*;

public abstract class Graphical {   //图形抽象构件类
    int x1 ;    //起点坐标
    int y1 ;
    int x2 ;    //终点坐标
    int y2 ;
    int move_x1;
    int move_x2;
    int move_y1;
    int move_y2;
    boolean isFill;
    Color color;
    BasicStroke bs;

    public int getX1() {
        return x1;
    }
    public void setX1(int x1) { this.x1 = x1; }
    public int getY1() {
        return y1;
    }
    public void setY1(int y1) {
        this.y1 = y1;
    }
    public int getX2() {
        return x2;
    }
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public int getY2() {
        return y2;
    }
    public void setY2(int y2) {
        this.y2 = y2;
    }
    //添加组件
    public void add(Graphical graphical) {}
    //删除组件
    public void remove(Graphical graphical) {}
    //画图形
    public void draw(Graphics g) {}
    //填充图形
    public void fill(Graphics g){}
    //观察者更新数据
    public void update(int function) {    //更新新的坐标
        if(function == 1) {     //移动
            x1 += move_x2-move_x1 ;      //移动的话起点、终点都要改变
            x2 += move_x2-move_x1 ;
            y1 += move_y2-move_y1 ;
            y2 += move_y2-move_y1 ;
        }
        else {              //放大缩小
            x2 += move_x2-move_x1 ;  //放大的话起点要做支点，改变终点
            y2 += move_y2-move_y1 ;
          //  System.out.println(x1+" "+y1+" "+x2+" "+y2);
        }
    }
    //判断是否选中了该图形，用于移动和放大
    public boolean isChosed(int x, int y) {
        //若鼠标点击的位置在图形范围内就可以选中图形
        return ((x >= x1 && x <= x2)||(x >= x2&&x <= x1) )&& ((y >= y1 && y <= y2)||(y <= y1 && y >= y2));
    }
}
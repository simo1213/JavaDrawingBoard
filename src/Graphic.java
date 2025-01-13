import java.util.ArrayList;
import java.util.List;

public class Graphic extends Graphical{   //图形容器构件类
    //存储结点的集合，可放各类图形
    static List<Graphical> graphicalList = new ArrayList<Graphical>();
    public Graphic() {
    }
    //添加子结点
    public void add(Graphical graphical) {
        graphicalList.add(graphical);
    }
    //删除子结点
    public void remove(Graphical graphical) {
        graphicalList.remove(graphical);
    }
    //判断是否选中了该图形，用于移动和放大
    public int  isGraphicChosed(int x,int y)
    {
        int i=0;
        boolean result;
        for(i=graphicalList.size()-1;i>=0;i--){
            result=graphicalList.get(i).isChosed(x,y);
            if(result) {return i;}
        }
        return -1;

    }
}

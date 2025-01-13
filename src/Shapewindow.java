import javax.naming.directory.SearchResult;
import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.io.File;

import static java.lang.System.exit;

public class Shapewindow extends JWindow{
    DrawPicture frame;// 父窗体
    private Container c=getContentPane();//窗体主容器
    private JTextField search;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;
    private JButton line = new JButton("line");;
    private JButton rectangle = new JButton("rectangle");;
    private JButton circle = new JButton("circle");
    private JButton oval = new JButton("oval");
    private JButton searchButton;// 搜索按钮
    private JButton hiddenButton;// 隐藏按钮
    private JButton back;//恢复按钮
    private JLabel lineLabel;//直线文字
    private JLabel rectangleLabel;//矩形文字
    private JLabel circleLabel;//圆形文字
    private JLabel nullword1;//占位
    private JLabel nullword2;//占位
    private String linestr=new String("line");
    private String rectanglestr=new String("rectangle");
    private String circlestr=new String("circle");
    private String ovalstr=new String("oval");
    public Shapewindow(DrawPicture frame)
    {
        this.frame = frame;
        setSize(400, 150);
        init();// 初始化窗体组件
        addListener();// 给组件添加加监听
    }
    private void init()
    {
        northPanel = new JPanel();
        southPanel = new JPanel();
        searchButton = new JButton("查找要绘制的图形");
        c.add(northPanel,BorderLayout.NORTH);
        northPanel.add(searchButton);
        centerPanel = new JPanel();
        setCenterPanel();
        FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);// 创建居中对齐的流布局
        flow.setHgap(50);// 水平间隔20像素
        southPanel.setLayout(flow);// 南部面板使用刚才创建好的流布局
        hiddenButton = new JButton("隐藏");
        southPanel.add(hiddenButton);
        back = new JButton("恢复");
        southPanel.add(back);
        c.add(southPanel, BorderLayout.SOUTH);// 南部面板放到主容器的南部位置
        c.repaint();
    }
    private void addListener()
    {
        line.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.drawShape=true;
                frame.graphical=new Line(frame.return_ForeColor(),frame.return_BasicStroke());
                Graphic.graphicalList.add(frame.graphical);
            }
        });
        rectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.drawShape=true;
                frame.graphical=new Rectangle(frame.return_ForeColor(),frame.return_BasicStroke());
                Graphic.graphicalList.add(frame.graphical);
            }
        });
        circle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.drawShape=true;
                frame.graphical=new Circle(frame.return_ForeColor(),frame.return_BasicStroke());
                Graphic.graphicalList.add(frame.graphical);
            }
        });
        oval.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.drawShape=true;
                frame.graphical=new Oval(frame.return_ForeColor(),frame.return_BasicStroke());
                Graphic.graphicalList.add(frame.graphical);
            }
        });
        hiddenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                frame.initShapeButton();// 父类窗体还原简笔画按钮的文本内容
            }
        });
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                centerPanel.removeAll();
                setCenterPanel();
                setVisible(true);
                centerPanel.repaint();
            }
        });
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = JOptionPane.showInputDialog(null, "请输入图形名称");
                text=text.toLowerCase();
                int isline=0;
                int isrect=0;
                int iscircle=0;
                int isoval=0;
                if(linestr.contains(text)) isline=1;
                if(rectanglestr.contains(text)) isrect=1;
                if(circlestr.contains(text)) iscircle=1;
                if(ovalstr.contains(text)) isoval=1;
                if(isline==1||isrect==1||iscircle==1||isoval==1)
                {
                    centerPanel.removeAll();
                    if(isline==1) centerPanel.add(line);
                    if(isrect==1) centerPanel.add(rectangle);
                    if(iscircle==1) centerPanel.add(circle);
                    if(isoval==1) centerPanel.add(oval);
                    isline=0;isrect=0;iscircle=0;isoval=0;
                    centerPanel.repaint();
                }
            }
        });
    }
    private void setCenterPanel()
    {
        centerPanel.setLayout(new GridLayout(3,3));
        c.add(centerPanel, BorderLayout.CENTER);// 面板放到主容器中部
        lineLabel = new JLabel("线");
        nullword1 = new JLabel(" ");
        centerPanel.add(lineLabel);
        centerPanel.add(line);
        centerPanel.add(nullword1);
        rectangleLabel = new JLabel("矩形");
        nullword2 = new JLabel(" ");
        centerPanel.add(rectangleLabel);
        centerPanel.add(rectangle);
        centerPanel.add(nullword2);
        circleLabel = new JLabel("圆形");
        centerPanel.add(circleLabel);
        centerPanel.add(circle);
        centerPanel.add(oval);
    }
}

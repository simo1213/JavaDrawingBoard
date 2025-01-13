import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

//画图主窗体
public class DrawPicture extends JFrame {
    DrawPictureCanvas canvas = new DrawPictureCanvas();//创建画布对象
    private Shapewindow shapeWindow;// 图形展示窗体
    private BasicStroke bs;//线型

    BufferedImage image = new BufferedImage(570, 390, BufferedImage.TYPE_INT_BGR);// 创建一个8位BGR颜色分量的图像
    Graphics gs = image.getGraphics();// 获得图像的绘图对象
    Graphics2D g = (Graphics2D) gs;// 将绘图图像转换为Graphics2D类型
    Color backgroundColor = Color.WHITE;// 定义背景色
    Color foreColor = Color.BLACK;// 定义画笔颜色

    boolean rubber = false;// 橡皮标识变量
    boolean select = false;//选取标识变量
    boolean addText = false; //添加文本标识变量
    //文本坐标x，y
    int tx=0;
    int ty=0;
    private String text=null;//文本内容
    private String familyName="楷体";//字体
    private int size=20;//字号
    //画图形
    public boolean drawShape = false;// 画图形标识变量
    Graphical graphical ;           //当前要处理的临时图形
    Graphic graphic =new Graphic();               //当前要处理的临时容器构件
    int tempGraphicalNumber;        //当前要处理的图形在list中的位置
    private boolean fillShape=false;      //是否填充图像
    private boolean changeShape=false;    //改变图形大小
    int x = -1, y = -1;// 上一次鼠标绘制点的横、纵坐标
    private String shuiyin = "";// 水印字符内容

    JFrame frame = new JFrame();
    //按钮
    private JToolBar toolBar;// 工具栏
    private JToggleButton eraserButton;// 橡皮擦按钮
    private JButton clearButton;// 清除按钮
    private JToggleButton addWordButton;// 添加文字按钮
    private JToggleButton drawButton;// 画笔按钮
    private JToggleButton selectButton;// 选取按钮
    private JToggleButton fillButton;//填充图形按钮
    private JToggleButton changeButton;//放大缩小按钮
    private JButton wordColorButton;// 文字颜色按钮
    private JButton wordStyleButton;// 文字风格按钮
    private JButton wordSizeButton;//文字字号按钮
    private JButton shapeButton;//图形按钮

    //菜单
    //系统
    private JMenuItem newFileMenuItem;//新建文件菜单
    private JMenuItem saveMenuItem;// 保存菜单
    private JMenuItem openMenuItem;//打开文件菜单
    private JMenuItem exitMenuItem;// 退出菜单
    private JMenuItem shuiyinMenuItem;// 水印菜单
    //线型
    private JMenuItem strokeMenuItem1;// 细线菜单
    private JMenuItem strokeMenuItem2;// 粗线菜单
    private JMenuItem strokeMenuItem3;// 较粗菜单
    //颜色
    private JMenuItem foregroundMenuItem;// 前景色菜单
    private JMenuItem backgroundMenuItem;// 背景色菜单
    //帮助
    private JMenuItem helpMenuItem;// 帮助菜单

    public DrawPicture() {
        ImageIcon imageIcon = new ImageIcon("src/img/logo.png");
        Image image = imageIcon.getImage();
        setIconImage(image);// 设置窗体图标
        setResizable(false);// 窗体不能改变大小
        setTitle("画板");// 设置标题，添加水印内容提示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 150, 574, 460);
        initialize();
        addListener();
    }

    //程序开始
    public static void main(String[] args) {
        DrawPicture p = new DrawPicture();
        p.setVisible(true);
    }

    //初始化界面
    public void initialize()
    {
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 570, 390);// 用背景色填充整个画布
        g.setColor(foreColor);
        canvas.setImage(image);
        getContentPane().add(canvas);// 将画布添加到窗体容器默认布局的中部位置

        toolBar = new JToolBar();// 初始化工具栏
        toolBar.setFloatable(false);//不能移动
        getContentPane().add(toolBar, BorderLayout.NORTH);// 工具栏添加到窗体最北位置

        shapeButton = new JButton("图形");
        toolBar.add(shapeButton);// 工具栏添加按钮
        toolBar.addSeparator();// 添加分割条

        eraserButton = new JToggleButton("橡皮擦");
        toolBar.add(eraserButton);
        drawButton = new JToggleButton("画笔");
        drawButton.setSelected(true);// 画笔按钮处于被选中状态
        toolBar.add(drawButton);
        toolBar.addSeparator();// 添加分割条

        addWordButton = new JToggleButton("添加文字");
        toolBar.add(addWordButton);
        wordColorButton = new JButton("文字颜色");
        toolBar.add(wordColorButton);
        wordStyleButton = new JButton("字体");
        toolBar.add(wordStyleButton);
        wordSizeButton = new JButton("字号");
        toolBar.add(wordSizeButton);
        toolBar.addSeparator();// 添加分割条

        selectButton = new JToggleButton("选取");
        toolBar.add(selectButton);
        fillButton = new JToggleButton("填充");
        toolBar.add(fillButton);
        changeButton = new JToggleButton("放大缩小");
        toolBar.add(changeButton);
        toolBar.addSeparator();// 添加分割条

        clearButton = new JButton("清除");
        toolBar.add(clearButton);

        ButtonGroup buttonGroup = new ButtonGroup();// 橡皮擦、画笔、选取、填充按钮，保证同时只有一个按钮被选中
        buttonGroup.add(eraserButton);
        buttonGroup.add(drawButton);
        buttonGroup.add(selectButton);
        buttonGroup.add(fillButton);
        buttonGroup.add(changeButton);
        buttonGroup.add(addWordButton);

        JMenuBar menuBar = new JMenuBar();// 创建菜单栏
        setJMenuBar(menuBar);// 窗体载入菜单栏

        JMenu systemMenu = new JMenu("系统");// 初始化菜单
        menuBar.add(systemMenu);// 菜单栏添加菜单对象

        shuiyinMenuItem = new JMenuItem("设置水印");
        systemMenu.add(shuiyinMenuItem);
        newFileMenuItem = new JMenuItem("新建");
        systemMenu.add(newFileMenuItem);
        openMenuItem = new JMenuItem("打开");
        systemMenu.add(openMenuItem);
        saveMenuItem = new JMenuItem("保存");// 初始化菜单项
        systemMenu.add(saveMenuItem);// 菜单添加菜单项
        systemMenu.addSeparator();// 添加分割条
        exitMenuItem = new JMenuItem("退出");
        systemMenu.add(exitMenuItem);

        JMenu strokeMenu = new JMenu("线型");
        menuBar.add(strokeMenu);
        strokeMenuItem1 = new JMenuItem("细线");
        strokeMenu.add(strokeMenuItem1);
        strokeMenuItem2 = new JMenuItem("粗线");
        strokeMenu.add(strokeMenuItem2);
        strokeMenuItem3 = new JMenuItem("较粗");
        strokeMenu.add(strokeMenuItem3);

        JMenu colorMenu = new JMenu("颜色");
        menuBar.add(colorMenu);
        backgroundMenuItem = new JMenuItem("背景颜色");
        colorMenu.add(backgroundMenuItem);
        foregroundMenuItem = new JMenuItem("画笔颜色");
        colorMenu.add(foregroundMenuItem);

        JMenu helpMenu=new JMenu("帮助");
        menuBar.add(helpMenu);
        helpMenuItem =new JMenuItem("帮助");
        helpMenu.add(helpMenuItem);
        shapeWindow = new Shapewindow(DrawPicture.this);
    }
    //添加监听
    private void addListener() {
        // 画板添加鼠标移动事件监听
        canvas.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(final MouseEvent e) //当鼠标拖拽时
            {
                if (x > 0 && y > 0) {// 如果X和Y存在鼠标记录
                    if (!(drawShape||select||fillShape||changeShape)) {
                        if (rubber) {//表示使用橡皮
                            save_randomLine(backgroundColor,e,1);
                            repaint_Shape();
                        } else {// 用画笔画图
                            save_randomLine(foreColor,e,2);
                        }
                    }
                }
                x = e.getX();
                y = e.getY();
                canvas.repaint();// 更新画布
            }

            //设置鼠标光标：橡皮图像
            @Override
            public void mouseMoved(MouseEvent e) {
                if (rubber) {
                    // 设置鼠标指针的形状为图片
                    Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
                    // 利用工具包获取图片
                    Image img = kit.createImage("src/img/鼠标橡皮.png");
                    // 封装鼠标光标
                    // 利用工具包创建一个自定义的光标对象，参数为图片、光标热点和光标描述字符串
                    Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
                    setCursor(c);// 使用自定义的光标
                }
                else if(select)// 如果选取标识为true,表示使用选取
                {
                    // 设置鼠标指针的形状为图片
                    Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
                    // 利用工具包获取图片
                    Image img = kit.createImage("src/img/抓手.png");
                    // 封装鼠标光标
                    // 利用工具包创建一个自定义的光标对象，参数为图片、光标热点和光标描述字符串
                    Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
                    setCursor(c);// 使用自定义的光标
                }
                else if(addText)// 如果文本标识为true,表示添加文本
                {
                    // 设置鼠标指针的形状为图片
                    Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
                    // 利用工具包获取图片
                    Image img = kit.createImage("src/img/文本.png");
                    // 封装鼠标光标
                    // 利用工具包创建一个自定义的光标对象，参数为图片、光标热点和光标描述字符串
                    Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
                    setCursor(c);// 使用自定义的光标
                }
                else if(fillShape)//填充
                {
                    // 设置鼠标指针的形状为图片
                    Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
                    // 利用工具包获取图片
                    Image img = kit.createImage("src/img/油漆桶.png");
                    // 封装鼠标光标
                    // 利用工具包创建一个自定义的光标对象，参数为图片、光标热点和光标描述字符串
                    Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
                    setCursor(c);// 使用自定义的光标
                }
                else if(changeShape)//放大
                {
                    // 设置鼠标指针的形状为图片
                    Toolkit kit = Toolkit.getDefaultToolkit();// 获得系统默认的组件工具包
                    // 利用工具包获取图片
                    Image img = kit.createImage("src/img/放大.png");
                    // 封装鼠标光标
                    // 利用工具包创建一个自定义的光标对象，参数为图片、光标热点和光标描述字符串
                    Cursor c = kit.createCustomCursor(img, new Point(0, 0), "clear");
                    setCursor(c);// 使用自定义的光标
                }
                else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));//获取预定义光标
                }
            }
        });
        // 画板添加鼠标按键事件监听
        canvas.addMouseListener(new MouseAdapter() {
            // 当按键松开时
            @Override
            public void mouseReleased(MouseEvent e) {
                if(drawShape) {
                    if (graphical != null) {
                        graphical.setX2(e.getX());  //终点坐标
                        graphical.setY2(e.getY());
                        graphical.color=foreColor;
                        graphical.bs=bs;
                        g.setStroke(bs);
                        g.setColor(foreColor);
                        graphical.draw(g);
                        canvas.repaint();     //获得起点终点后就可以画图了
                        drawShape=false;
                    }
                }
                else if(select||changeShape){
                    if(tempGraphicalNumber>=0)
                    {
                        String className=Graphic.graphicalList.get(tempGraphicalNumber).getClass().getSimpleName();
                        if(!(changeShape&&(className.equals("Text")))){
                            Graphic.graphicalList.get(tempGraphicalNumber).move_x2=e.getX();
                            Graphic.graphicalList.get(tempGraphicalNumber).move_y2=e.getY();
                            if(select){
                                Graphic.graphicalList.get(tempGraphicalNumber)
                                        .update(1);
                            }
                            else{
                                Graphic.graphicalList.get(tempGraphicalNumber)
                                        .update(0);
                            }
                            graphical=Graphic.graphicalList.get(tempGraphicalNumber);
                            Graphic.graphicalList.remove(graphical);
                            Graphic.graphicalList.add(graphical);
                            g.setColor(backgroundColor);// 绘图工具使用背景色
                            g.fillRect(0, 0, 570, 390);// 画一个背景色的方形，填满整个画布
                            repaint_randomLine();
                            repaint_Shape();
                            g.setColor(foreColor);// 绘图工具使用前景色
                            canvas.repaint();
                        }

                    }
                }
                // 将记录上一次鼠标绘制点的横纵坐标恢复成-1
                x = -1;
                y = -1;
            }
            // 当按键按下时
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawShape)//如果此时鼠标画的是图形
                {
                    //绘制图像
                    if(graphical != null) {     //只有当前有对象才能存起始位置
                        graphical.setX1(e.getX());
                        graphical.setY1(e.getY());
                    }
                }
                else if(addText){
                        tx=e.getX();
                        ty=e.getY();
                        addText(text);
                        addText=false;
                        drawButton.doClick();
                }
                else if(select||changeShape)
                {
                    tempGraphicalNumber=graphic.isGraphicChosed(e.getX(),e.getY());
                    if(tempGraphicalNumber>=0)
                    {
                        String className=Graphic.graphicalList.get(tempGraphicalNumber).getClass().getSimpleName();
                        if(!(changeShape&&(className.equals("Text")))){
                            Graphic.graphicalList.get(tempGraphicalNumber).move_x1=e.getX();
                            Graphic.graphicalList.get(tempGraphicalNumber).move_y1=e.getY();
                        }
                    }
                }
                else if(fillShape)
                {
                    tempGraphicalNumber=graphic.isGraphicChosed(e.getX(),e.getY());
                    if(tempGraphicalNumber>=0)
                    {
                        String className=Graphic.graphicalList.get(tempGraphicalNumber).getClass().getSimpleName();
                        if(!(className.equals("Text")||className.equals("Line"))){
                            g.setColor(foreColor);
                            Graphic.graphicalList.get(tempGraphicalNumber).color=foreColor;
                            Graphic.graphicalList.get(tempGraphicalNumber).fill(g);
                            Graphic.graphicalList.get(tempGraphicalNumber).isFill=true;
                            canvas.repaint();
                        }
                        drawButton.doClick();
                    }
                }
            }

        });
        // 橡皮擦按钮添加动作监听
        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    drawShape=false;//目的是让选取、画笔、橡皮擦在同一时刻只能有一个生效
                    select=false;
                    rubber = true;
                fillShape=false;
                changeShape=false;
                addText=false;
            }
        });
        // 画笔按钮添加动作监听
        drawButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    rubber=false;
                    select=false;
                    drawShape=false;
                    fillShape=false;
                changeShape=false;
                addText=false;
            }
        });
        // 添加文本按钮添加动作监听
        addWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rubber=false;
                select=false;
                drawShape=false;
                fillShape=false;
                changeShape=false;
                addText=true;
                text = JOptionPane.showInputDialog(DrawPicture.this, "添加文本内容：");
                if (text == null) {//文本为空
                    text = "";
                } else {
                    addText=true;
                    drawShape=false;
                }
            }
        });
        //文字颜色
        wordColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color fColor = JColorChooser.showDialog(DrawPicture.this, "选择文本", Color.CYAN);
                if (fColor != null) {
                    foreColor = fColor;// 将选择的颜色赋值给前景色变量
                }
                wordColorButton.setForeground(foreColor);
                g.setColor(foreColor);// 绘图工具使用前景色
            }
        });
        //文字风格
        wordStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                familyName=JOptionPane.showInputDialog(DrawPicture.this, "输入字体（楷体，宋体，仿宋，Times New Roman）：");
                if(familyName!=null&&((!(familyName.equals("楷体")||familyName.equals("宋体")||familyName.equals("仿宋")))
                        ||familyName.equals("Times New Roman")))
                { familyName="楷体";}//不符合输入,恢复为默认字体
            }
        });
        //设置字号
        wordSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sizeString=JOptionPane.showInputDialog(DrawPicture.this,"输入字号（数字）：");
                if(sizeString!=null&&!sizeString.equals("")&&isNumeric(sizeString))
                {
                    size=Integer.parseInt(sizeString);
                }
                else{
                    size=20;
                }
            }
        });
        //帮助文档
        helpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "" + "##################\r\n" + "#画图软件使用说明书#\r\n"
                        + "####################\r\n"
                        + "1.本软件可以实现以下功能：\r\n"
                        + "（1）打开文件，新建、保存文件,退出软件，在画布上绘制直线、矩形、圆形等图形\r\n"
                        + "（2）设置画笔的颜色和粗细\r\n"
                        + "（3）添加水印\r\n"
                        + "（4）依据鼠标轨迹绘制任意线\r\n"
                        + "（5）支持橡皮擦功能\r\n"
                        + "（6）图形选取和移动\r\n"
                        + "（7）文字添加与文字风格设置\r\n"
                        + "（8）实现对基本图形命名，支持按照名称搜索特定图形，并定位，支持模糊搜索\r\n"
                        + "（9）支持按照图形类别浏览图形名称列表的功能\r\n"
                        + "（10）放大缩小基本图形\r\n"
                        + "（11）填充基本图形\r\n"
                        + "2.本软件主要分为3个模块：菜单、工具栏和画布\r\n"
                        + "（1）菜单栏的系统子菜单包括设置水印，打开、新建、保存图片以及退出程序功能\r\n"
                        + "	   线型子菜单包括设置画笔的粗细功能；\r\n"
                        + "	   颜色子菜单包括设置前景、后景颜色功能（对应设置画笔、背景颜色）；\r\n"
                        + "	   帮助子菜单包括说明文档功能；\r\n"
                        + "（2）工具栏主要包括图形选择、橡皮擦、画笔、添加文字、文字颜色设置、字体字号设置\r\n"
                        + "    选取移动、填充、放大缩小、清空画板功能；\n"
                        + "（3）画布用于图形绘制，使用鼠标选中要绘制的图形即可进行绘制。", "使用说明", JOptionPane.PLAIN_MESSAGE);
            }
        });
        // 清除按钮添加动作监听
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setColor(backgroundColor);// 绘图工具使用背景色
                g.fillRect(0, 0, 570, 390);// 画一个背景色的方形，填满整个画布
                g.setColor(foreColor);// 绘图工具使用前景色
                Graphic.graphicalList.clear();
                RandomLine.drawList.clear();
                canvas.repaint();
            }
        });

        //新建菜单添加动作监听
        newFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_newFile_actionPerformed();
            }
        });
        //保存菜单添加动作监听
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_save_actionPerformed();
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    do_open_actionPerformed();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        shapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isVisible = shapeWindow.isVisible();// 获取简笔画展示窗体的可见状态
                if (isVisible) {
                    shapeButton.setText("展开");// 修改按钮的文本
                    shapeWindow.setVisible(false);// 隐藏图形展示窗体
                } else {
                    shapeButton.setText("隐藏");
                    // 重新指定简笔画展示窗体的显示位置
                    shapeWindow.setLocation(getX() - shapeWindow.getWidth() - 5, getY());
                    shapeWindow.setVisible(true);// 图形展示窗体可见
                }
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    drawShape=false;//目的是让选取、画笔、橡皮擦在同一时刻只能有一个生效
                    select=true;
                    rubber = false;
                    fillShape = false;
                changeShape=false;
                addText=false;
            }
        });
        //填充图形
        fillButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //填充有效，其他无效
                fillShape=true;
                drawShape=false;
                select=false;
                rubber = false;
                changeShape=false;
                addText=false;
            }
        });
        //放大缩小图形
        changeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillShape=false;
                drawShape=false;
                select=false;
                rubber = false;
                changeShape=true;
                addText=false;
            }
        });
        //细线菜单添加动作监听
        strokeMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_stroke1_actionPerformed();
            }
        });
        // 粗线菜单添加动作监听
        strokeMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_stroke2_actionPerformed();
            }
        });
        // 较粗线菜单添加动作监听
        strokeMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_stroke3_actionPerformed();
            }
        });
        // 前景色菜单添加动作监听
        foregroundMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_foreground_actionPerformed();
            }
        });
        // 背景色菜单添加动作监听
        backgroundMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                do_background_actionPerformed();
            }
        });
        // 水印按钮添加动作监听
        shuiyinMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuiyin = JOptionPane.showInputDialog(DrawPicture.this, "你想添加什么水印?");
                if (null == shuiyin) {
                    shuiyin = "";
                } else {
                    setTitle("画图(水印内容:“" + shuiyin + "”)");// 设置标题，添加水印内容提示
                }
            }
        });
    }

    //新建文件
    private void do_newFile_actionPerformed() {
        int value=JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
        if(value==0){
            do_save_actionPerformed();
        }
        else if(value==1){
        }
        g.setColor(backgroundColor);// 绘图工具使用背景色
        g.fillRect(0, 0, 570, 390);// 画一个背景色的方形，填满整个画布
        g.setColor(foreColor);// 绘图工具使用前景色
        canvas.repaint();
    }
    //保存文件
    public void do_save_actionPerformed() {
        addWatermark();
        imageFile.saveImage(DrawPicture.this, image);// 打印图片
    }
    //添加水印
    private void addWatermark() {
        if (!"".equals(shuiyin.trim())) {
            g.rotate(Math.toRadians(-30));// 将图片旋转-30度
            Font font = new Font("楷体", Font.BOLD, 32);
            g.setFont(font);
            g.setColor(Color.GRAY);
            AlphaComposite alphaComposite = AlphaComposite.SrcOver.derive(0.4f);// 设置透明效果
            g.setComposite(alphaComposite);// 使用透明效果
            g.drawString(shuiyin, 150, 500);// 绘制文字
            canvas.repaint();
            g.rotate(Math.toRadians(30));// 将旋转的图片再转回来
            alphaComposite = AlphaComposite.SrcOver.derive(1f);// 不透明效果
            g.setComposite(alphaComposite);
            g.setColor(foreColor);// 画笔恢复之前颜色
        }
    }
    //打开文件
    private void do_open_actionPerformed() throws IOException {
        int value=JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
        if(value==0){
            do_save_actionPerformed();
            imageFile.openFile(this);
        }
        else if(value==1){
            imageFile.openFile(this);
        }
    }
    public void initShapeButton() {
        shapeButton.setText("图形");
    }
    // 细线动作事件
    private void do_stroke1_actionPerformed() {
        //  声明画笔的属性粗细为1像素，线条末端无修饰，折线处呈尖角
        bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        g.setStroke(bs);
        strokeMenuItem1.setSelected(true);
    }
    // 粗线动作事件
    private void do_stroke2_actionPerformed() {
        //  声明画笔的属性粗细为2像素，线条末端无修饰，折线处呈尖角
        bs = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        g.setStroke(bs);
        strokeMenuItem2.setSelected(true);
    }
    // 较粗动作事件
    private void do_stroke3_actionPerformed() {
        //  声明画笔的属性粗细为4像素，线条末端无修饰，折线处呈尖角
       bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
        g.setStroke(bs);
        strokeMenuItem3.setSelected(true);
    }
    // 背景色按钮及其菜单项所触发的动作事件
    private void do_background_actionPerformed() {
        // 打开选择颜色对话框，参数依次为父窗体，标题、默认选中的颜色
        Color bgColor = JColorChooser.showDialog(DrawPicture.this, "选择背景色", Color.CYAN);
        if (bgColor != null) {
            backgroundColor = bgColor;// 将选中的颜色赋给背景色变量
        }
        backgroundMenuItem.setBackground(backgroundColor);
        g.setColor(backgroundColor);// 绘图工具使用背景色
        g.fillRect(0, 0, 570, 390);// 画一个背景颜色的方形，填满整个画布
        g.setColor(foreColor);// 绘图工具使用前景色
        repaint_randomLine();
        repaint_Shape();
        canvas.repaint();
    }
    // 前景色按钮及其菜单项所触发的动作事件
    private void do_foreground_actionPerformed() {
        Color fColor = JColorChooser.showDialog(DrawPicture.this, "选择前景色", Color.CYAN);
        if (fColor != null) {
            foreColor = fColor;// 将选择的颜色赋值给前景色变量
            wordColorButton.setForeground(foreColor);
        }
        foregroundMenuItem.setForeground(foreColor);
        g.setColor(foreColor);// 绘图工具使用前景色
    }
    //添加文本
    private void addText(String text){
        if(text!=null&&!"".equals(text.trim())){
            Font font = new Font(familyName, Font.BOLD, size);
            g.setFont(font);
            g.setColor(foreColor);//获得文字颜色
            g.drawString(text,tx,ty);// 绘制文字
            Text t=new Text(text,tx,ty,familyName,foreColor,size);//记录该文本
            Graphic.graphicalList.add(t);//加入文本列表
            canvas.repaint();
        }
    }
    public BasicStroke return_BasicStroke()
    {
        return this.bs;
    }
    public Color return_ForeColor()
    {
        return this.foreColor;
    }
    //保存画笔记录
    private void save_randomLine(Color color,final MouseEvent e,int i){
        Line tempLine=new Line(color,return_BasicStroke(),i);
        g.setColor(color);
        g.setStroke(return_BasicStroke());
        tempLine.setX1(x);
        tempLine.setY1(y);
        tempLine.setX2(e.getX());
        tempLine.setY2(e.getY());
        tempLine.draw(g);
        RandomLine.addDrawList(tempLine);
    }
    //重绘画笔
    private void repaint_randomLine()
    {
        for(int i=0;i<RandomLine.drawList.size();i++)
        {
            Line tempLine=RandomLine.drawList.get(i);
            if(tempLine.lineType==1)
            {
                g.setColor(backgroundColor);
                g.setStroke(tempLine.bs);
                tempLine.draw(g);
            }
            else{
                g.setColor(tempLine.color);
                g.setStroke(tempLine.bs);
                tempLine.draw(g);
            }
        }
    }
    //重绘图形
    private void repaint_Shape()
    {
        for(int i=0;i<Graphic.graphicalList.size();i++)
        {
            graphical=Graphic.graphicalList.get(i);
            g.setStroke(graphical.bs);
            g.setColor(graphical.color);
            String className=graphical.getClass().getSimpleName();
            if(!(className.equals("Text")||className.equals("Line"))){
                if(graphical.isFill)
                {
                    graphical.fill(g);
                }
                else graphical.draw(g);
            }
            else{
                graphical.draw(g);
            }
        }
    }
    //判断字符串是不是数字
    private boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
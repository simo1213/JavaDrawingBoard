import java.awt.*;
//画布
public class DrawPictureCanvas extends Canvas{
    private Image image = null;
    //设置画板中的图片
    public void setImage(Image image) {
        this.image = image;
    }
    //重写paint方法
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
    //重写update()方法，这样可以解决屏幕闪烁的问题
    public void update(Graphics g) {
        paint(g);
    }
}

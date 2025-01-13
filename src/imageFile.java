import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class imageFile {
    public imageFile() {
    }
    /*
     * 打开文件
     */
    public static void openFile(DrawPicture frame) throws IOException {
        String imgPath = showFileOpenDialog(frame);
        if(imgPath!=null){
            BufferedImage read = ImageIO.read(Files.newInputStream(Paths.get(imgPath)));
            Image img = read.getScaledInstance(570 , 390, Image.SCALE_DEFAULT);
            frame.image = new BufferedImage(570, 390, BufferedImage.TYPE_INT_RGB);
            frame.gs = frame.image.getGraphics();// 获得图像的绘图对象
            frame.g = (Graphics2D) frame.gs;// 将绘图图像转换为Graphics2D类型
            frame.g.drawImage(img, 0, 0,null);
            frame.g.setColor(frame.foreColor);
            frame.canvas.setImage(frame.image);
            frame.canvas.repaint();
        }
    }
    //文件选取
    private static String showFileOpenDialog(Component parent) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }
    public static void saveImage(JFrame frame, BufferedImage saveImage) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("保存图片");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", new String[]{"jpg"});
        jfc.setFileFilter(filter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
        String fileName = sdf.format(new Date());
        FileSystemView view = FileSystemView.getFileSystemView();
        File filePath = view.getHomeDirectory();
        File showFile = new File(filePath, fileName + ".jpg");
        jfc.setSelectedFile(showFile);
        int flag = jfc.showSaveDialog((Component)null);
        if (flag == 0) {
            try {
                File saveFile = jfc.getSelectedFile();
                if (saveFile == null) {
                    throw new IOException();
                }

                ImageIO.write(saveImage, "jpg", saveFile);
            } catch (IOException var11) {
                var11.printStackTrace();
                JOptionPane.showMessageDialog(frame, "文件无法保存！", "错误", 64);
            }
        }

    }
}
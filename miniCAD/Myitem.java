package miniCAD;

import java.awt.*;
import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;


/**
 * 该类用于定义minicad中的所有图形类，
 */
public class Myitem implements Serializable{


    private int i_type = 0;  //用于记录该图形的类型

    // private boolean i_chosed = false;

    private Color i_color = new Color(0, 0, 0); // 颜色
    private Font i_font; // 字体
    private float i_width; // 设置字体粗细
    // private Graphics2D i_2D; //画笔
    private String i_content; //主要用于存储文本的内容，线段，椭圆和矩形自动设置为空字符串

    //图形的坐标1
    private int x1;
    private int x2;
    //图形的坐标2
    private int y1;
    private int y2;

    // 用于记录图形初始化后的长和宽，用于放大缩小
    private int initwidth = 0;
    private int initheight = 0;

    // private Rectangle2D t_bound; // 设置边界

    public Color getColor() {
        return i_color;
    }

    public void setColor(Color color) {
        this.i_color = color;
    }

    // 初始化Myitem
    public Myitem(Color color, int type, int x1, int y1, int x2, int y2, float width, String content) {
        // i_2D = g2d;
        i_width = width;
        i_color = color;
        // i_stroke = stroke;
        // i_font = new Font("Serif", Font.PLAIN, 20);
        i_content = content;
        i_type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.x2 = y2;

    }

    // 绘制图形方法，在frame或者JPanel的repaint时自动调用
    public void draw(Graphics g) {
        Graphics2D i_2D = (Graphics2D) g;
        i_2D.setColor(i_color);
        i_2D.setStroke(new BasicStroke(i_width));

        switch (i_type) {
            case Itemmanage.LINE_TYPE: {
                i_2D.drawLine(x1, y1, x2, y2);
                break;
            }
            case Itemmanage.CIRCLE_TYPE: {
                int xmin = Math.min(x1, x2);
                int xmax = Math.max(x1, x2);
                int ymin = Math.min(y1, y2);
                int ymax = Math.max(y1, y2);
                Ellipse2D ellispse = new Ellipse2D.Double(xmin, ymin, xmax - xmin, ymax - ymin);
                i_2D.draw(ellispse);
                break;
            }
            case Itemmanage.REC_TYPE: {
                int xmin = Math.min(x1, x2);
                int xmax = Math.max(x1, x2);
                int ymin = Math.min(y1, y2);
                int ymax = Math.max(y1, y2);

                i_2D.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);
                break;
            }

            case Itemmanage.TEXT_TYPE: {
                int xmin = Math.min(x1, x2);
                int ymax = Math.max(y1, y2);
                int ymin = Math.min(y1, y2);
                i_font = new Font("Serif", Font.PLAIN, ymax - ymin);
                i_2D.setFont(i_font);
                i_2D.drawString(i_content, xmin, ymax);
            }
        }
    }

    // 此方法用于判断当前图形是否可以被光标选中，如果图形有重叠，优先选中更早画的图形
    public boolean canselect(int x, int y) {
        boolean result = false;
        // reset();
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2))
            result = true;
        return result;
    }

    // 用于拖动移动图形
    public void move(int dx, int dy) {
        x1 += dx;
        x2 += dx;
        y1 += dy;
        y2 += dy;
        return;
    }

    // 设置图形初始化后的边用于放大缩小
    public void initedge() {
        initwidth = Math.max(x1, x2) - Math.min(x1, x2);
        initheight = Math.max(y1, y2) - Math.min(y1, y2);
    }


    public void setx2(int x2) {
        this.x2 = x2;
    }

    public void sety2(int y2) {
        this.y2 = y2;
    }

    public int getx2() {
        return x2;
    }

    public int gety2() {
        return y2;
    }



    // 此函数用于放大图形
    public void setbigger() {
        if (x1 < x2 && y1 < y2) {
            x1 -= initwidth / 10;
            x2 += initwidth / 10;
            y1 -= initheight / 10;
            y2 += initheight / 10;
        }
        if (x1 > x2 && y1 < y2) {
            x1 += initwidth / 10;
            x2 -= initwidth / 10;
            y1 -= initheight / 10;
            y2 += initheight / 10;
        }
        if (x1 < x2 && y1 > y2) {
            x1 -= initwidth / 10;
            x2 += initwidth / 10;
            y1 += initheight / 10;
            y2 -= initheight / 10;
        }
        if (x1 > x2 && y1 > y2) {
            x1 += initwidth / 10;
            x2 -= initwidth / 10;
            y1 += initheight / 10;
            y2 -= initheight / 10;
        }
    }

    public void setsmaller() {
        if (x1 < x2 && y1 < y2) {
            x1 += initwidth / 10;
            x2 -= initwidth / 10;
            y1 += initheight / 10;
            y2 -= initheight / 10;
        }
        if (x1 > x2 && y1 < y2) {
            x1 -= initwidth / 10;
            x2 += initwidth / 10;
            y1 += initheight / 10;
            y2 -= initheight / 10;
        }
        if (x1 < x2 && y1 > y2) {
            x1 += initwidth / 10;
            x2 -= initwidth / 10;
            y1 -= initheight / 10;
            y2 += initheight / 10;
        }
        if (x1 > x2 && y1 > y2) {
            x1 -= initwidth / 10;
            x2 += initwidth / 10;
            y1 -= initheight / 10;
            y2 += initheight / 10;
        }
    }

}

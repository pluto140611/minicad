package miniCAD;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

//该类用于Item对象的管理，主要作用是使用arraylist存储所有画布上已有的对象，同时充当事件相应函数与item类之间的接口。
public class Itemmanage {
    // 以下为事件的actioncommand，用于按钮提交时间类型以及做出相应处理。
    public static final int LINE_TYPE = 1;
    public static final int CIRCLE_TYPE = 2;
    public static final int REC_TYPE = 3;
    public static final int TEXT_TYPE = 4;

    public static final int CLEAR_TYPE = 5;
    public static final int SELECT_TYPE = 6;
    public static final int BIGGER_TYPE = 7;
    public static final int SMALLER_TYPE = 8;

    public static final int COLOR_TYPE = 9;
    public static final int PEN_WIDTH = 10;

    public static final int SAVE = 11;
    public static final int OPEN = 12;
    ArrayList<Myitem> Itemlist = new ArrayList<>();
    private Graphics2D i_g2d;

    // 获取画布的画笔
    public void getg2d(Graphics2D g2d) {
        i_g2d = g2d;
    }

    // 创建一个对象
    public Myitem Createitem(Color color, int type, int x1, int y1, int x2, int y2, float width, String content) {
        Myitem newitem = new Myitem(color, type, x1, y1, x2, y2, width, content);
        Itemlist.add(newitem);
        return newitem;
    }

    public void Clearitem() {
        Itemlist.clear();
    }
}

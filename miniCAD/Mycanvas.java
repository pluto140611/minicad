package miniCAD;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 该类定义miniCAD的画布，所有图形item全部绘制在该画布上。
 */
public class Mycanvas extends JPanel {
    private Itemmanage c_itemmanage;
    private Myactionlistener c_actionlistener;
    private boolean ifImage = false;
	private List<BufferedImage> c_imageList = new ArrayList<BufferedImage>();
    // private ArrayList<Myitem> Itemlist = new ArrayList<>();

    public Mycanvas(Itemmanage itemmanage,Myactionlistener actionlistener) {
        this.c_actionlistener = actionlistener;
        this.c_itemmanage = itemmanage;

        this.setBackground(new Color(255, 255, 255));
        this.addMouseListener(c_actionlistener);
        this.addMouseMotionListener(c_actionlistener);
    }

    public void paint(Graphics g){
        super.paint(g);
        for(Myitem m: c_itemmanage.Itemlist){
            m.draw(g);
        }
    }

    public void setImage(BufferedImage image){
		c_imageList.add(image);
		ifImage = true;
	}


}

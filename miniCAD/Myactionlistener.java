package miniCAD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class Myactionlistener implements ActionListener, MouseListener, MouseMotionListener {

    private Point2D presspoint = new Point2D.Double(); // 用于决定鼠标点击的点，基本未使用
    private static int event_type = -1; // 决定当前事件类型

    // 以下用于记录画笔的位置，x1，y1一般记录画笔按下的位置，x2，y2记录画笔拖动后的位置。
    private int x1 = 0;
    private int y1 = 0;
    private int x2 = 0;
    private int y2 = 0;
    private float pen = (float) 2.5; // 记录当前画笔粗细
    private Color m_color = Color.BLACK; // 记录当前画笔颜色
    private String text; // 记录文本框输入的内容

    private Myitem currentitem = null; // 始终为正在处理（包括绘制，拖动，改变颜色的图形对象）
    private Itemmanage a_itemmanage; // item管理类
    private Myframe mainframe = null; // 顶层容器，用于绘制图片

    public Myactionlistener(Itemmanage itemmanage) {
        a_itemmanage = itemmanage;
    }

    public void setframe(Myframe mainframe) {
        this.mainframe = mainframe;
    }

    // 该方法重写所有按钮按下后会发生的事件。
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        int actionCommand = Integer.parseInt(e.getActionCommand());

        switch (actionCommand) {
            case Itemmanage.OPEN: {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(null);
                    chooser.setDialogTitle("请打开cad文件");
                    File file = chooser.getSelectedFile();
                    if (file == null) {
                        JOptionPane.showMessageDialog(null, "文件路径错误或未选择文件！");
                    } else {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                        a_itemmanage.Itemlist = (ArrayList<Myitem>) (in.readObject());
                        mainframe.repaint();
                        in.close();
                    }
                    break;
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "文件打开失败！");
                }
            }

            case Itemmanage.SAVE: {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.showSaveDialog(null);
                    chooser.setDialogTitle("保存文件");
                    File file = chooser.getSelectedFile();
                    if (file == null) {
                        JOptionPane.showMessageDialog(null, "文件路径错误！");
                    } else {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                        out.writeObject(a_itemmanage.Itemlist);
                        out.close();
                        JOptionPane.showMessageDialog(null, "保存成功！");
                    }
                    break;
                } catch (Exception e1) {
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(null, "保存失败！");
                }

            }

            case Itemmanage.LINE_TYPE: {

            }

            case Itemmanage.CIRCLE_TYPE: {

            }

            case Itemmanage.REC_TYPE: {
                event_type = actionCommand; // 根据按钮事件切换接下来的事件
                // System.out.println(event_type);
                break;
            }

            case Itemmanage.TEXT_TYPE: {
                event_type = actionCommand;
                text = JOptionPane.showInputDialog("请输入要输入的文本:");
                break;
            }

            // 清除画布中的内容
            case Itemmanage.CLEAR_TYPE: {
                a_itemmanage.Clearitem();
                mainframe.repaint();
                break;
            }

            // 放大currentitem
            case Itemmanage.BIGGER_TYPE: {
                if (currentitem != null) {
                    currentitem.setbigger();
                    mainframe.repaint();
                }
                break;
            }

            // 缩小currentitem
            case Itemmanage.SMALLER_TYPE: {
                if (currentitem != null) {
                    currentitem.setsmaller();
                    mainframe.repaint();
                }
                break;
            }

            // 选中某个item，可以实现拖动
            case Itemmanage.SELECT_TYPE: {
                event_type = actionCommand;
                // System.out.println(event_type);
                break;
            }

            // 改变画笔颜色，自动改变currentitem的颜色
            case Itemmanage.COLOR_TYPE: {
                m_color = JColorChooser.showDialog((Component) source, "Choose Color", Color.BLACK);
                if (currentitem != null) {
                    currentitem.setColor(m_color);
                    mainframe.repaint();
                }
                break;
            }

            // 改变画笔粗细
            case Itemmanage.PEN_WIDTH: {
                String penwidth = JOptionPane.showInputDialog("请输入画笔粗细:（默认为2.5）");
                pen = Float.parseFloat(penwidth);
            }
        }
    }

    // 该方法重写所有光标拖动的相应函数，主要用于绘制，拖动
    @Override
    public void mouseDragged(MouseEvent e) {
        Component source = e.getComponent();
        x2 = e.getX();
        y2 = e.getY();
        switch (event_type) {
            case Itemmanage.LINE_TYPE: {

            }
            case Itemmanage.CIRCLE_TYPE: {

            }
            case Itemmanage.TEXT_TYPE: {

            }

            case Itemmanage.REC_TYPE: {
                currentitem.setx2(x2);
                currentitem.sety2(y2);
                currentitem.initedge();
                source.repaint();
                break;
            }

            case Itemmanage.SELECT_TYPE: {
                if (currentitem == null)
                    break;
                currentitem.move(x2 - x1, y2 - y1);
                source.repaint();
                x1 = x2;
                y1 = y2;
                break;
            }
            default: {
                break;
            }
        }
    }

    // 该方法用于重写所有鼠标按下的相应函数，主要用于绘制，
    @Override
    public void mousePressed(MouseEvent e) {
        Component source = e.getComponent();
        presspoint.setLocation(e.getX(), e.getY()); // 设置按下的点
        x1 = e.getX();
        y1 = e.getY();
        // currentitem = null;

        if (source instanceof Mycanvas) {
            switch (event_type) {
                case Itemmanage.LINE_TYPE: {

                }
                case Itemmanage.CIRCLE_TYPE: {

                }
                case Itemmanage.REC_TYPE: {
                    currentitem = a_itemmanage.Createitem(m_color, event_type, x1, y1, x1, y1, pen, "");
                    break;
                }

                case Itemmanage.TEXT_TYPE: {
                    currentitem = a_itemmanage.Createitem(m_color, event_type, x1, y1, x1, y1, pen, text);
                    break;
                }

                case Itemmanage.SELECT_TYPE: {
                    for (Myitem item : a_itemmanage.Itemlist) {
                        if (item.canselect(e.getX(), e.getY()) == true) {
                            currentitem = item;
                            break;
                        }
                    }
                    break;
                }
                default: {
                    break;
                }
            }

        } else {
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // currentitem.setx2(e.getX());
        // currentitem.sety2(e.getY());
        // currentitem = null;

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

}

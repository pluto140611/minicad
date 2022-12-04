package miniCAD;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics2D;
import javax.swing.*;


/**
 * 该类定义图形Gui的顶层容器,主要用于放置工具栏，所有按钮以及画布
 */

public class Myframe extends JFrame {

    private Mycanvas mycanvas;
    private Dimension framesize;
    private Myactionlistener actionlisten;
    private Itemmanage itemmanage;

    public Myframe(Itemmanage itemmanage, Myactionlistener actionlisten) {
        this.actionlisten = actionlisten;
        this.itemmanage = itemmanage;
        InitFrame();
    }

    private void InitFrame() {
        this.setTitle("MiniCAD");
        this.setMinimumSize(new Dimension(400, 400));

        Toolkit mytk = Toolkit.getDefaultToolkit();
        Dimension screenSize = mytk.getScreenSize();
        framesize = new Dimension((int) screenSize.getWidth()*2 / 3, (int) screenSize.getHeight()*2 / 3);
        this.setSize(framesize);

        // 采用流式布局
        this.setLayout(new BorderLayout(10, 10));
        makemenubar();
        maketoolbar();

        mycanvas = new Mycanvas(itemmanage, actionlisten);
        this.add(mycanvas, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    // 该函数用于初始化界面左上角的menu
    private void makemenubar() {
        JMenuBar mymenubar = new JMenuBar();
        JMenu filemenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        openItem.setActionCommand(Itemmanage.OPEN + "");
        openItem.addActionListener(actionlisten);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setActionCommand(Itemmanage.SAVE + "");
        saveItem.addActionListener(actionlisten);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        filemenu.add(openItem);
        filemenu.add(saveItem);
        filemenu.add(quitItem);

        mymenubar.add(filemenu);
        this.setJMenuBar(mymenubar);

        return;
    }

    public void maketoolbar() {

        JPanel northPanel = new JPanel(new FlowLayout());
        JPanel westPanel = new JPanel(new GridLayout(4, 1));
        northPanel.setBackground(new Color(255, 255, 255));
        Image tempimage;
        ImageIcon tempicon;

        // Toolkit mytk = Toolkit.getDefaultToolkit();
        // Dimension screenSize = mytk.getScreenSize();
        Dimension buttonsize1 = new Dimension((int)framesize.getWidth()/10,(int)framesize.getHeight()/8);
        Dimension buttonsize2 = new Dimension((int)framesize.getWidth()/10,(int)framesize.getHeight()/8);

        // JToolBar toolBar = new JToolBar("画图");
        JButton drawline = new JButton("直线");
        // 用于判断哪个按钮发生了事件
        drawline.setActionCommand(Itemmanage.LINE_TYPE + "");
        drawline.addActionListener(actionlisten);
        drawline.setPreferredSize(buttonsize2);
        tempicon = new ImageIcon("icon/直线.png");
        tempimage = tempicon.getImage().getScaledInstance((int)buttonsize2.getWidth(), (int)buttonsize2.getHeight(), Image.SCALE_SMOOTH);
        tempicon = new ImageIcon(tempimage);
        drawline.setIcon(tempicon);

        JButton drawcircle = new JButton("椭圆");
        drawcircle.setActionCommand(Itemmanage.CIRCLE_TYPE + "");
        drawcircle.addActionListener(actionlisten);
        drawcircle.setPreferredSize(buttonsize2);
        tempicon = new ImageIcon("icon/椭圆.png");
        tempimage = tempicon.getImage().getScaledInstance((int)buttonsize2.getWidth(), (int)buttonsize2.getHeight(), Image.SCALE_SMOOTH);
        tempicon = new ImageIcon(tempimage);
        drawcircle.setIcon(tempicon);

        JButton drawrec = new JButton("矩形");
        drawrec.setActionCommand(Itemmanage.REC_TYPE + "");
        drawrec.addActionListener(actionlisten);
        drawrec.setPreferredSize(buttonsize2);
        tempicon = new ImageIcon("icon/矩形.png");
        tempimage = tempicon.getImage().getScaledInstance((int)buttonsize2.getWidth(), (int)buttonsize2.getHeight(), Image.SCALE_SMOOTH);
        tempicon = new ImageIcon(tempimage);
        drawrec.setIcon(tempicon);

        JButton text = new JButton("文本");
        text.setActionCommand(Itemmanage.TEXT_TYPE + "");
        text.addActionListener(actionlisten);
        tempicon = new ImageIcon("icon/文本.png");
        tempimage = tempicon.getImage().getScaledInstance((int)buttonsize2.getWidth(), (int)buttonsize2.getHeight(), Image.SCALE_SMOOTH);
        tempicon = new ImageIcon(tempimage);
        text.setIcon(tempicon);

        JButton clear = new JButton("清屏");
        clear.setActionCommand(Itemmanage.CLEAR_TYPE + "");
        clear.addActionListener(actionlisten);
        clear.setPreferredSize(buttonsize1);

        JButton select = new JButton("选中");
        select.setActionCommand(Itemmanage.SELECT_TYPE + "");
        select.addActionListener(actionlisten);
        select.setPreferredSize(buttonsize1);

        JButton setbig = new JButton("放大");
        setbig.setActionCommand(Itemmanage.BIGGER_TYPE + "");
        setbig.addActionListener(actionlisten);
        setbig.setPreferredSize(buttonsize1);

        JButton setsmall = new JButton("缩小");
        setsmall.setActionCommand(Itemmanage.SMALLER_TYPE + "");
        setsmall.addActionListener(actionlisten);
        setsmall.setPreferredSize(buttonsize1);

        
        JButton color = new JButton("改变颜色");
        color.setActionCommand(Itemmanage.COLOR_TYPE + "");
        color.addActionListener(actionlisten);
        color.setPreferredSize(buttonsize1);

        JButton penwidth = new JButton("画笔粗细");
        penwidth.setActionCommand(Itemmanage.PEN_WIDTH + "");
        penwidth.addActionListener(actionlisten);
        penwidth.setPreferredSize(buttonsize1);

        westPanel.add(drawline);
        westPanel.add(drawcircle);
        westPanel.add(drawrec);
        westPanel.add(text);
        northPanel.add(select);
        northPanel.add(setbig);
        northPanel.add(setsmall);
        northPanel.add(color);
        northPanel.add(penwidth);
        northPanel.add(clear);

        // northPanel.add(toolBar, BorderLayout.CENTER);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        // System.out.println("设置工具箱成功");

    }

    public Graphics2D getg2d() {
        return (Graphics2D) mycanvas.getGraphics();
    }

}
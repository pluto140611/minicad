package miniCAD;

//主函数，完成初始化顶层容器，事件监听以及图形管理
public class MiniCAD  {

    private static Itemmanage itemmanage = new Itemmanage();
    private static Myactionlistener actionlistener = new Myactionlistener(itemmanage);
    private static Myframe myframe = new Myframe(itemmanage, actionlistener);
     public static void main(String[] argv) {
        actionlistener.setframe(myframe);

    }
}

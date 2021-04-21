import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        initControllerPanel();
    }

    public static void initControllerPanel() {
        ControllerJPanel p = new ControllerJPanel();
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(ControllerJPanel.w, ControllerJPanel.h);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Virus spread simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        panelThread.start();//开启画布线程,接着看代码的下一站可以转MyPanel.java
    }

}

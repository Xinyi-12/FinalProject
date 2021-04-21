import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonActionListener implements ActionListener {

    private JButton jButton;
    private ControllerJPanel myJPanel;

    public JButtonActionListener(JButton jButton, ControllerJPanel myJPanel) {
        this.jButton = jButton;
        this.myJPanel = myJPanel;
        jButton.setBackground(Color.GRAY);//初始化一些JButton配置
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();//得到按钮响应
        System.out.println(com);
        myJPanel.text1.getText();
        if (com.equals("reset")) {
            myJPanel.text1.setText("0.8");
            myJPanel.text1.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text1.setForeground(Color.GRAY);

            myJPanel.text2.setText("10");
            myJPanel.text2.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text2.setForeground(Color.GRAY);

            myJPanel.text3.setText("50");
            myJPanel.text3.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text3.setForeground(Color.GRAY);

            myJPanel.text4.setText("0");
            myJPanel.text4.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text4.setForeground(Color.GRAY);

            myJPanel.text5.setText("0");
            myJPanel.text5.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text5.setForeground(Color.GRAY);

            myJPanel.text6.setText("0.99");
            myJPanel.text6.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
            myJPanel.text6.setForeground(Color.GRAY);

//            myJPanel.text7.setText("城市总人口数量，默认：5000");
//            myJPanel.text7.setFont(new Font("宋体", Font.PLAIN, 10* 2));
//            myJPanel.text7.setForeground(Color.GRAY);
//
//            myJPanel.text8.setText("病死率，默认：0.50");
//            myJPanel.text8.setFont(new Font("宋体", Font.PLAIN, 10* 2));
//            myJPanel.text8.setForeground(Color.GRAY);
//
//            myJPanel.text9.setText("死亡时间均值，默认：100");
//            myJPanel.text9.setFont(new Font("宋体", Font.PLAIN, 10* 2));
//            myJPanel.text9.setForeground(Color.GRAY);
//
//            myJPanel.text10.setText("死亡时间方差，默认：1");
//            myJPanel.text10.setFont(new Font("宋体", Font.PLAIN, 10* 2));
//            myJPanel.text10.setForeground(Color.GRAY);
        }

        if (com.equals("start")) {
            Constants constants = new Constants();
            String text1 = myJPanel.text1.getText();
            if (text1 != null && text1.length() > 0 && !VirusBroadcastUtil.isNumeric(text1)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：Virus propagation rate must be entered between 0 and 1, for example: 0.8, default: 0.8", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setBroadRate(Float.valueOf(text1));


            String text2 = myJPanel.text2.getText();
            if (text2 != null && text2.length() > 0 && !VirusBroadcastUtil.isNumeric(text2)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：Medical response time must be entered as a positive integer, e.g., 10, default: 10, unit: days", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setHospitalReceiveTime(Integer.valueOf(text2));


            String text3 = myJPanel.text3.getText();
            if (text3 != null && text3.length() > 0 && !VirusBroadcastUtil.isNumeric(text3)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：Initializing infected must be entered as a positive integer, e.g., 50, default: 50, unit: person", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setOriginalCount(Integer.valueOf(text3));


            String text4 = myJPanel.text4.getText();
            if (text4 != null && text4.length() > 0 && !VirusBroadcastUtil.isNumeric(text4)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：Latency must be entered as a positive integer, e.g., 0, default: 0, unit: days", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setShadowTime(Float.valueOf(text4) * 10);

            String text5 = myJPanel.text5.getText();
            if (text5 != null && text5.length() > 0 && !VirusBroadcastUtil.isNumeric(text5)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：The number of beds must be entered as a positive integer, for example: 0, default: 0, unit: sheets", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setBedCount(Integer.valueOf(text5));

            String text6 = myJPanel.text6.getText();
            if (text6 != null && text6.length() > 0 && !VirusBroadcastUtil.isNumeric(text6)) {
                JOptionPane.showMessageDialog(myJPanel, "ERROR：The turnover rate must be entered between -1 and 1, for example: 0.99, default: 0.99", "Warm Tips", JOptionPane.ERROR_MESSAGE);
                return;
            }
            constants.setU(Float.valueOf(text6));

//            String text7 = myJPanel.text7.getText();
//            if (text7 != null && text7.length() > 0 && !"城市总人口数量，默认：5000".equals(text7) && !VirusBroadcastUtil.isNumeric(text7)) {
//                JOptionPane.showMessageDialog(myJPanel, "ERROR：城市总人口数量必须输入正整数，例如：5000，默认：5000，单位：个", "Warm Tips", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (!"城市总人口数量，默认：5000".equals(text7))
//                constants.setCityPersonSize(Integer.valueOf(text7));
//
//            String text8 = myJPanel.text8.getText();
//            if (text8 != null && text8.length() > 0 && !"病死率，默认：0.50".equals(text8) && !VirusBroadcastUtil.isNumeric(text8)) {
//                JOptionPane.showMessageDialog(myJPanel, "ERROR：病死率必须输入0～1之间的数据，例如：0.50，默认：0.50", "Warm Tips", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (!"病死率，默认：0.50".equals(text8))
//                constants.setFatalityRate(Float.valueOf(text8));
//
//            String text9 = myJPanel.text9.getText();
//            if (text9 != null && text9.length() > 0 && !"死亡时间均值，默认：100".equals(text9) && !VirusBroadcastUtil.isNumeric(text9)) {
//                JOptionPane.showMessageDialog(myJPanel, "ERROR：死亡时间均值必须输入正整数，例如：100，默认：100，单位：天", "Warm Tips", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (!"死亡时间均值，默认：100".equals(text9))
//                constants.setDieTime(Integer.valueOf(text9));
//
//            String text10 = myJPanel.text10.getText();
//            if (text10 != null && text10.length() > 0 && !"死亡时间方差，默认：1".equals(text10) && !VirusBroadcastUtil.isNumeric(text10)) {
//                JOptionPane.showMessageDialog(myJPanel, "ERROR：死亡时间方差必须输入0～1之间的整数，例如：1，默认：1", "Warm Tips", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (!"死亡时间方差，默认：1".equals(text10))
//                constants.setDieVariance(Double.valueOf(text10));

            System.out.println("Start initializing data and executing the program");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int sw = screenSize.width;
            int sh = screenSize.height;
            System.out.println(sw);
            System.out.println(sh);

            int times = 1;//Control magnification according to screen resolution
            if (sw >= 1920) {
                sw = 1920;
                sh = 1200;
                times = 2;
            }

            Long hx = Math.round(sw * 0.75);
            int inHx = hx.intValue();
            int inHy = 80;

            Hospital hospital = new Hospital(constants, inHx, inHy);
            PersonPool personPool = new PersonPool(constants, hospital);
            MyPanel p = new MyPanel(constants, hospital, personPool, inHx - 40, times);
            personPool.setMyPanel(p);
            VirusBroadcastView view = new VirusBroadcastView(constants, hospital, personPool, p, sw, sh - 40, inHx);
            view.initInfected();
            view.initPanel();
        }
    }
}

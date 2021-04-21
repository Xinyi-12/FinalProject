import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField textField;
    private JPanel myJPanel;

    public JTextFieldHintListener(JTextField jTextField, String hintText, JPanel myJPanel) {
        this.textField = jTextField;
        this.hintText = hintText;
        this.myJPanel = myJPanel;
        jTextField.setText(hintText);  //Default direct display
        jTextField.setFont(new Font("宋体", Font.PLAIN, 10 * 2));
        jTextField.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //Clear the prompt when getting focus
        String temp = textField.getText();
        if (temp.equals(hintText)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        //When focus is lost, no input is made and the prompt is displayed
        String temp = textField.getText();
        if (temp.equals("")) {
            textField.setForeground(Color.GRAY);
            textField.setText(hintText);
        } else {
            System.out.println(temp);
//            JOptionPane.showMessageDialog(myJPanel, temp, "温馨提示", JOptionPane.WARNING_MESSAGE);
        }

    }
}

package findingnumber;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.Timer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SinglePlay extends javax.swing.JFrame {
    private JLabel lblNumber;
    private JTextField txtStatus;
    private Timer timer;
    private int timeLeft = 90; // 90 seconds
    private int randomToFind; // Biến lưu số cần tìm

    private JTextField txtToFind;
    private JTextField txtFoundCount;

    /**
     * Creates new form Game
     */
    public SinglePlay() {
        initComponents();
        initGrid();
        startTimer();
    }

    private void startTimer() {
        lblNumber.setText("Time left: " + timeLeft + "s");
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                lblNumber.setText("Time left: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    timer.stop();
                    endGame();
                }
            }
        });
        timer.start();
    }

    private void endGame() {
        int foundCount = Integer.parseInt(txtFoundCount.getText());
        int totalNumbers = 100;
        int missedCount = totalNumbers - foundCount;

        if (foundCount > missedCount) {
            txtStatus.setText("Bạn đã thắng với " + foundCount + " số tìm được!");
        } else if (foundCount < missedCount) {
            txtStatus.setText("Bạn đã thua với " + foundCount + " số tìm được.");
        } else {
            txtStatus.setText("Hòa! Cả hai người đều tìm được " + foundCount + " số.");
        }

        // Vô hiệu hóa các nút số
        for (Component comp : jPanel1.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }
    }

    private void initGrid() {
        jPanel1.setLayout(new GridLayout(10, 10, 5, 5)); // Sử dụng GridLayout cho đồ họa đơn giản hơn

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        randomToFind = numbers.get(0); // Chọn số đầu tiên trong danh sách số đã xáo trộn làm số cần tìm
        txtToFind.setText(String.valueOf(randomToFind));

        for (int i = 0; i < 100; i++) {
            JButton numberButton = new JButton(String.valueOf(numbers.get(i)));
            numberButton.setFont(new Font("Arial", Font.BOLD, 18));
            numberButton.setBackground(getRandomColor());
            numberButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    int clickedNumber = Integer.parseInt(clickedButton.getText());
                    if (clickedNumber == randomToFind) {
                        int foundCount = Integer.parseInt(txtFoundCount.getText()) + 1;
                        txtFoundCount.setText(String.valueOf(foundCount));
                         // Ẩn nút số đã chọn
                        clickedButton.setVisible(false);
                          // Loại bỏ số đã chọn khỏi danh sách
                        numbers.remove(Integer.valueOf(randomToFind));
                        // Chọn ngẫu nhiên số mới để tìm
                        Collections.shuffle(numbers);
                        randomToFind = numbers.get(0);
                        txtToFind.setText(String.valueOf(randomToFind));
                    }
                }
            });
            jPanel1.add(numberButton);
        }
    }

    private Color getRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        JPanel infoPanel = new javax.swing.JPanel();
        lblNumber = new JLabel("90", SwingConstants.CENTER);
        JLabel lblToFind = new JLabel("Số phải tìm:");
        JLabel lblFoundCount = new JLabel("Số lượng tìm được:");
        JLabel lblStatus = new JLabel("Trạng thái:");
        txtToFind = new JTextField("1");
        txtFoundCount = new JTextField("0");
        txtStatus = new JTextField("Đang chơi"); // Đã khai báo đúng cách

        JButton btnEnd = new JButton("tạm dừng");
        btnEnd.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Tạm dừng thời gian
            if (timer.isRunning()) {
                timer.stop();
                btnEnd.setText("Tiếp tục"); 
            } else {
                timer.start();
                btnEnd.setText("Tạm dừng");
            }
        }
});
        
        JButton btnExit = new JButton("Thoát");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Chơi button clicked");
                Main form = new Main();
                form.setVisible(true);
                form.pack();
                form.setLocationRelativeTo(null);
                //close the login form
                dispose(); 
            }
        });
        // Thiết lập font và màu cho nhãn số
        lblNumber.setFont(new Font("Arial", Font.BOLD, 36));
        lblNumber.setForeground(Color.MAGENTA);

        // Thiết lập căn giữa cho các trường văn bản
        txtToFind.setHorizontalAlignment(JTextField.CENTER);
        txtFoundCount.setHorizontalAlignment(JTextField.CENTER);
        txtStatus.setHorizontalAlignment(JTextField.CENTER);

        // Thiết lập layout cho infoPanel
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Thêm các thành phần vào infoPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        infoPanel.add(lblNumber, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        infoPanel.add(lblToFind, gbc);

        gbc.gridx = 1;
        infoPanel.add(txtToFind, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        infoPanel.add(lblFoundCount, gbc);

        gbc.gridx = 1;
        infoPanel.add(txtFoundCount, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        infoPanel.add(lblStatus, gbc);

        gbc.gridx = 1;
        infoPanel.add(txtStatus, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 5);
        infoPanel.add(btnEnd, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 5, 10, 10);
        infoPanel.add(btnExit, gbc);

        // Thiết lập JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Custom Panel");

        // Thêm infoPanel và jPanel1 vào JFrame
        this.setLayout(new BorderLayout());
        this.add(infoPanel, BorderLayout.NORTH);
        this.add(jPanel1, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null); // Center the frame

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SinglePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SinglePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SinglePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SinglePlay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SinglePlay().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel jPanel1;
    // End of variables declaration
}

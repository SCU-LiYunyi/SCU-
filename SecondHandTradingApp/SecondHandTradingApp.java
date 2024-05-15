import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class SecondHandTradingApp extends JFrame {
    private JTextArea displayArea;
    private JTextField usernameField, passwordField, itemNameField, priceField;
    private JButton registerButton, loginButton, sellButton, buyButton, browseButton;
    private String currentUser;

    public SecondHandTradingApp() {
        super("二手交易小程序");
        currentUser = null;

        displayArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane);

        usernameField = new JTextField(10);
        passwordField = new JTextField(10);
        itemNameField = new JTextField(10);
        priceField = new JTextField(10);

        registerButton = new JButton("注册");
        loginButton = new JButton("登录");
        sellButton = new JButton("卖出");
        buyButton = new JButton("买入");
        browseButton = new JButton("浏览");

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                register();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                login();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sell();
            }
        });

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                buy();
            }
        });

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                browse();
            }
        });

        JPanel loginPanel = new JPanel();
        loginPanel.add(new JLabel("用户名:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("密码:"));
        loginPanel.add(passwordField);
        loginPanel.add(registerButton);
        loginPanel.add(loginButton);
        add(loginPanel, "North");

        JPanel sellPanel = new JPanel();
        sellPanel.add(new JLabel("物品名:"));
        sellPanel.add(itemNameField);
        sellPanel.add(new JLabel("价格:"));
        sellPanel.add(priceField);
        sellPanel.add(sellButton);
        add(sellPanel, "South");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(browseButton);
        buttonPanel.add(buyButton);
        add(buttonPanel, "South");
    }

    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + ":" + password + "\n");
            writer.close();
            displayMessage("注册成功");
        } catch (IOException e) {
            displayMessage("注册失败: " + e.getMessage());
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    currentUser = username;
                    displayMessage("登录成功");
                    return;
                }
            }
            displayMessage("用户名或密码错误");
        } catch (IOException e) {
            displayMessage("登录失败: " + e.getMessage());
        }
    }

    private void sell() {
        if (currentUser == null) {
            displayMessage("请先登录");
            return;
        }
        String itemName = itemNameField.getText();
        String price = priceField.getText();
        try {
            FileWriter writer = new FileWriter("items.txt", true);
            writer.write(currentUser + ":" + itemName + ":" + price + "\n");
            writer.close();
            displayMessage("卖出成功");
        } catch (IOException e) {
            displayMessage("卖出失败: " + e.getMessage());
        }
    }

    private void buy() {
        if (currentUser == null) {
            displayMessage("请先登录");
            return;
        }
        displayMessage("暂未实现买入功能");
    }

    private void browse() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            StringBuilder items = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                items.append(line).append("\n");
            }
            displayArea.setText(items.toString());
        } catch (IOException e) {
            displayMessage("浏览失败: " + e.getMessage());
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SecondHandTradingApp app = new SecondHandTradingApp();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(500, 300);
        app.setVisible(true);
    }
}

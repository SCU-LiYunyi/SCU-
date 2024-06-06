import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TradingSystemGUI {
    private TradingSystem tradingSystem;

    public TradingSystemGUI() {
        tradingSystem = new TradingSystem();
        JFrame frame = new JFrame("大学生二手交易系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // 登录面板
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("用户名:");
        JTextField userText = new JTextField(15);
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordText = new JPasswordField(15);
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(registerButton, gbc);

        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);

        // 登录按钮事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                User user = tradingSystem.login(username, password);
                if (user != null) {
                    showUserPanel(user);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "登录失败，用户名或密码错误");
                }
            }
        });

        // 注册按钮事件
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (tradingSystem.register(username, password)) {
                    JOptionPane.showMessageDialog(frame, "注册成功");
                    User user = tradingSystem.login(username, password);
                    showUserPanel(user);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "注册失败，用户名已存在");
                }
            }
        });

        frame.setVisible(true);
    }

    private void showUserPanel(User user) {
        JFrame userFrame = new JFrame("欢迎, " + user.getUsername());
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.setSize(600, 400);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        // 商品列表
        JTextArea productArea = new JTextArea();
        productArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productArea);
        userPanel.add(scrollPane, BorderLayout.CENTER);

        // 刷新商品列表
        refreshProductList(productArea);

        // 商品操作面板
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 2));

        JTextField productName = new JTextField(20);
        JTextField productDescription = new JTextField(20);
        JTextField productPrice = new JTextField(20);
        JTextField productContact = new JTextField(20);
        JTextField searchField = new JTextField(20);
        JButton addButton = new JButton("添加商品");
        JButton removeButton = new JButton("撤销商品");
        JButton searchButton = new JButton("搜索商品");
        JButton showAllButton = new JButton("显示所有商品");

        productPanel.add(new JLabel("商品名:"));
        productPanel.add(productName);
        productPanel.add(new JLabel("描述:"));
        productPanel.add(productDescription);
        productPanel.add(new JLabel("价格:"));
        productPanel.add(productPrice);
        productPanel.add(new JLabel("联系方式:"));
        productPanel.add(productContact);
        productPanel.add(addButton);
        productPanel.add(removeButton);
        productPanel.add(new JLabel("搜索关键字:"));
        productPanel.add(searchField);
        productPanel.add(searchButton);
        productPanel.add(showAllButton);

        userPanel.add(productPanel, BorderLayout.SOUTH);

        // 添加商品按钮事件
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productName.getText();
                String description = productDescription.getText();
                double price = Double.parseDouble(productPrice.getText());
                String contact = productContact.getText();
                tradingSystem.addProduct(name, description, price, user.getUsername(), contact);
                refreshProductList(productArea);
            }
        });

        // 撤销商品按钮事件
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productName.getText();
                Product productToRemove = null;
                for (Product product : tradingSystem.getProducts()) {
                    if (product.getName().equals(name) && product.getOwner().equals(user.getUsername())) {
                        productToRemove = product;
                        break;
                    }
                }
                if (productToRemove != null) {
                    tradingSystem.removeProduct(name, user.getUsername());
                    refreshProductList(productArea);
                } else {
                    JOptionPane.showMessageDialog(userFrame, "商品不存在或您无权撤销此商品");
                }
            }
        });

        // 搜索商品按钮事件
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                ArrayList<Product> results = tradingSystem.searchProducts(keyword);
                refreshProductList(productArea, results);
            }
        });

        // 显示所有商品按钮事件
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProductList(productArea);
            }
        });

        userFrame.getContentPane().add(userPanel);
        userFrame.setVisible(true);
    }

    private void refreshProductList(JTextArea productArea) {
        productArea.setText("");
        for (Product product : tradingSystem.getProducts()) {
            productArea.append("商品名: " + product.getName() + "\n");
            productArea.append("描述: " + product.getDescription() + "\n");
            productArea.append("价格: " + product.getPrice() + "\n");
            productArea.append("卖家: " + product.getOwner() + "\n");
            productArea.append("联系方式: " + product.getContact() + "\n\n");
        }
    }

    private void refreshProductList(JTextArea productArea, ArrayList<Product> products) {
        productArea.setText("");
        for (Product product : products) {
            productArea.append("商品名: " + product.getName() + "\n");
            productArea.append("描述: " + product.getDescription() + "\n");
            productArea.append("价格: " + product.getPrice() + "\n");
            productArea.append("卖家: " + product.getOwner() + "\n");
            productArea.append("联系方式: " + product.getContact() + "\n\n");
        }
    }

    public static void main(String[] args) {
        new TradingSystemGUI();
    }
}

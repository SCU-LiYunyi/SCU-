package com.example.taobao.dao;


import com.alibaba.fastjson.JSONObject;
import com.example.taobao.entity.Commodity;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommodityDao {

    private final String url = "jdbc:mysql://localhost:3306/test";
    private final String username = "root";
    private final String password = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getCommodityRecord(JSONObject param, JSONObject json) {
        System.out.println("[CommodityDao/getCommodityRecord] 执行到这里 param = " + param);

        String sql = "SELECT * FROM commodity";
        List<Map<String, String>> list = new ArrayList<>();

        // 构造 WHERE 子句
        StringBuilder whereClause = new StringBuilder();
        if (param != null) {
            // 示例：根据传入的参数构造 WHERE 子句
            String name = param.getString("name");
            if (name != null && !name.isEmpty()) {
                whereClause.append(" WHERE name = ?");
            }
        }

        sql += whereClause.toString() + " ORDER BY name";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // 设置参数
            if (param != null) {
                String name = param.getString("name");
                if (name != null && !name.isEmpty()) {
                    statement.setString(1, name);
                }
            }

            try (ResultSet re = statement.executeQuery()) {
                System.out.println("[CommodityDao/getCommodityRecord] Connection 连接完毕");
                System.out.println("[CommodityDao/getCommodityRecord] Statement 创建完毕");

                while (re.next()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", re.getString("id"));
                    map.put("name", re.getString("name"));
                    map.put("description", re.getString("description"));
                    map.put("price", re.getString("price"));
                    map.put("seller", re.getString("seller"));
                    map.put("phone", re.getString("phone"));
                    list.add(map);
                }
            }

            json.put("Data", list);
            json.put("result_code", 0);
            json.put("result_msg", "ok");

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "Database error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static List<Commodity> searchCommodityRecord(String name) {
        String sql = "SELECT * FROM commodity WHERE name LIKE ?";
        List<Commodity> list = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + name + "%");

            try (ResultSet re = statement.executeQuery()) {
                while (re.next()) {
                    Commodity commodity = new Commodity();
                    commodity.setId(re.getString("id"));
                    commodity.setName(re.getString("name"));
                    commodity.setDescription(re.getString("description"));
                    commodity.setPrice(re.getString("price"));
                    commodity.setSeller(re.getString("seller"));
                    commodity.setPhone(re.getString("phone"));
                    list.add(commodity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return list;
    }



    public void addCommodityRecord(Commodity commodity, JSONObject json) {
        System.out.println("[CommodityDao/addCommodityRecord] 执行到这里 commodity = " + commodity.toString());

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            System.out.println("[CommodityDao/addCommodityRecord] Connection 连接完毕");
            System.out.println("[CommodityDao/addCommodityRecord] Statement 创建完毕");

            String id = commodity.getId();
            String name = commodity.getName();
            String description = commodity.getDescription();
            String price = commodity.getPrice();
            String seller = commodity.getSeller();
            String phone = commodity.getPhone();

            String sql = "INSERT INTO commodity (id, name, description, price, seller, phone) " +
                    "VALUES ('" + id + "', '" + name + "', '" + description + "', '" + price + "', '" + seller + "', '" + phone + "')";

            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            json.put("result_code", 0);
            json.put("result_msg", "ok");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteCommodityRecord(String id, JSONObject json) {
        System.out.println("[CommodityDao/deleteCommodityRecord] 执行到这里，删除的 ID = " + id);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            System.out.println("[CommodityDao/deleteCommodityRecord] Connection 连接完毕");
            System.out.println("[CommodityDao/deleteCommodityRecord] Statement 创建完毕");

            String sql = "DELETE FROM commodity WHERE id = '" + id + "'";
            System.out.println("[CommodityDao/deleteCommodityRecord] SQL 语句：" + sql);

            int rowsAffected = statement.executeUpdate(sql);
            if (rowsAffected > 0) {
                json.put("result_code", 0);
                json.put("result_msg", "ok");
            } else {
                json.put("result_code", 1);
                json.put("result_msg", "Record not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            json.put("result_code", 1);
            json.put("result_msg", "Database error: " + e.getMessage());
        }
        System.out.println("[CommodityDao/deleteCommodityRecord] 数据库访问结束");
    }

    public void updateCommodityRecord(Commodity commodity, JSONObject json) {
        System.out.println("[CommodityDao/updateCommodityRecord] 执行到这里 commodity = " + commodity.toString());

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            System.out.println("[CommodityDao/updateCommodityRecord] Connection 连接完毕");
            System.out.println("[CommodityDao/updateCommodityRecord] Statement 创建完毕");

            String id = commodity.getId();
            String name = commodity.getName();
            String description = commodity.getDescription();
            String price = commodity.getPrice();
            String seller = commodity.getSeller();
            String phone = commodity.getPhone();

            String sql = "UPDATE commodity SET name = '" + name + "', description = '" + description + "', price = '" + price + "', " +
                    "seller = '" + seller + "', phone = '" + phone + "' WHERE id = '" + id + "'";

            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            json.put("result_code", 0);
            json.put("result_msg", "ok");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}


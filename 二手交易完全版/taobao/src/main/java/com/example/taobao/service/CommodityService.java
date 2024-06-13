package com.example.taobao.service;


import com.alibaba.fastjson.JSONObject;
import com.example.taobao.dao.CommodityDao;
import com.example.taobao.entity.Commodity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class CommodityService {
    public void getCommodityRecord(JSONObject param, JSONObject json) {
        CommodityDao dao = new CommodityDao();
        dao.getCommodityRecord(param, json);
    }

    public void addCommodityRecord(Commodity commodity, JSONObject json) {
        CommodityDao dao = new CommodityDao();
        dao.addCommodityRecord(commodity, json);
    }

    public void deleteCommodityRecord(String id, JSONObject json) {
        CommodityDao dao = new CommodityDao();
        dao.deleteCommodityRecord(id, json);
    }

    public void updateCommodityRecord(Commodity commodity, JSONObject json) {
        CommodityDao dao = new CommodityDao();
        dao.updateCommodityRecord(commodity, json);
    }

    public void searchCommodityRecord(JSONObject param, JSONObject json) {
        if (param == null) {
            json.put("status", "error");
            json.put("message", "参数 'param' 不能为空");
            return;
        }

        String name = param.getString("name");
        if (name == null || name.trim().isEmpty()) {
            json.put("status", "error");
            json.put("message", "名称参数缺失或为空");
            return;
        }

        try {
            List<Commodity> commodities = CommodityDao.searchCommodityRecord(name);
            json.put("Data", commodities);
            json.put("result_code", 0);
            json.put("result_msg", "ok");
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "搜索商品记录时发生错误: " + e.getMessage());
        }
    }
    }



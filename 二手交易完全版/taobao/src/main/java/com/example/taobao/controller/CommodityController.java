package com.example.taobao.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.taobao.entity.Commodity;
import com.example.taobao.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    public CommodityService commodityService;

    @RequestMapping("/get_record")
    public JSONObject getCommodityRecord(@RequestBody(required = false) JSONObject param){
        JSONObject json = new JSONObject();
        //if(!(param ==null)) {
        commodityService.getCommodityRecord(param, json);
        //}
        return json;
    }

    @RequestMapping(value = "/search_record", method = RequestMethod.POST)
    public JSONObject searchCommodityRecord(@RequestBody(required = false) JSONObject param) {
        JSONObject json = new JSONObject();
        try {
            System.out.println("[CommodityController/searchCommodityRecord] " + param.toString());
            commodityService.searchCommodityRecord(param, json);
        } catch (Exception e) {
            json.put("result_code", 1);
            json.put("result_msg", "搜索商品记录时发生错误: " + e.getMessage());
        }
        return json;
    }
    @RequestMapping("/add_record")
    public JSONObject addCommodityRecord(@RequestBody(required = false) Commodity commodity){
        JSONObject json = new JSONObject();
        System.out.println("[CommodityController/addCommodityRecord] " + commodity.toString());
        commodityService.addCommodityRecord(commodity, json);
        return json;
    }

    @RequestMapping("/delete_record")
    public JSONObject deleteCommodityRecord(@RequestBody Map<String, String> request) {
        JSONObject json = new JSONObject();
        String id = request.get("id");
        System.out.println("[CommodityController/deleteCommodityRecord] Deleting commodity with ID: " + id);
        commodityService.deleteCommodityRecord(id, json);
        return json;
    }

    @RequestMapping("/update_record")
    public JSONObject updateCommodityRecord(@RequestBody(required = false) Commodity commodity) {
        JSONObject json = new JSONObject();
        System.out.println("[CommodityController/updateCommodityRecord] " + commodity.toString());
        commodityService.updateCommodityRecord(commodity, json);
        return json;
    }
}

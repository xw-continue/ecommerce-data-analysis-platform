package com.ecommerce.analysis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/mock-data")
public class MockDataController {

    // 销售趋势数据
    @GetMapping("/sales-trend")
    public Map<String, Object> getSalesTrend() {
        List<String> dates = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        List<Integer> sales = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();
        
        for (int i = 0; i < dates.size(); i++) {
            sales.add(ThreadLocalRandom.current().nextInt(100000, 500000));
            orders.add(ThreadLocalRandom.current().nextInt(500, 2000));
        }
        
        return Map.of(
            "dates", dates,
            "sales", sales,
            "orders", orders
        );
    }

    // 地区销售分布数据
    @GetMapping("/region-sales")
    public Map<String, Object> getRegionSales() {
        List<String> regions = Arrays.asList("华东", "华北", "华南", "西南", "西北");
        List<Integer> sales = new ArrayList<>();
        
        for (int i = 0; i < regions.size(); i++) {
            sales.add(ThreadLocalRandom.current().nextInt(1000000, 5000000));
        }
        
        return Map.of(
            "regions", regions,
            "sales", sales
        );
    }

    // 热门商品数据
    @GetMapping("/hot-products")
    public Map<String, Object> getHotProducts() {
        List<String> products = Arrays.asList(
            "智能手机 Pro Max", "无线蓝牙耳机", "运动休闲鞋", "智能手表", "纯棉T恤",
            "坚果礼盒", "智能音箱", "护肤套装", "厨房小家电", "儿童玩具"
        );
        List<Integer> sales = new ArrayList<>();
        
        for (int i = 0; i < products.size(); i++) {
            sales.add(ThreadLocalRandom.current().nextInt(30000, 120000));
        }
        
        return Map.of(
            "products", products,
            "sales", sales
        );
    }

    // 商品分类数据
    @GetMapping("/product-category")
    public Map<String, Object> getProductCategory() {
        List<String> categories = Arrays.asList("电子产品", "服装鞋包", "家居用品", "食品饮料", "美妆护肤");
        List<Integer> sales = new ArrayList<>();
        
        for (int i = 0; i < categories.size(); i++) {
            sales.add(ThreadLocalRandom.current().nextInt(500000, 2000000));
        }
        
        return Map.of(
            "categories", categories,
            "sales", sales
        );
    }

    // 销售预测数据
    @GetMapping("/sales-prediction")
    public Map<String, Object> getSalesPrediction() {
        List<String> dates = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
        List<Integer> actual = new ArrayList<>();
        List<Integer> predicted = new ArrayList<>();
        
        for (int i = 0; i < dates.size(); i++) {
            actual.add(ThreadLocalRandom.current().nextInt(100000, 300000));
            predicted.add(actual.get(i) + ThreadLocalRandom.current().nextInt(-20000, 30000));
        }
        
        return Map.of(
            "dates", dates,
            "actual", actual,
            "predicted", predicted
        );
    }

    // 支付分析数据
    @GetMapping("/payment-analysis")
    public Map<String, Object> getPaymentAnalysis() {
        List<String> methods = Arrays.asList("支付宝", "微信支付", "银行卡", "货到付款");
        List<Integer> counts = new ArrayList<>();
        
        for (int i = 0; i < methods.size(); i++) {
            counts.add(ThreadLocalRandom.current().nextInt(500, 3000));
        }
        
        return Map.of(
            "methods", methods,
            "counts", counts
        );
    }

    // 客户分析数据
    @GetMapping("/customer-analysis")
    public Map<String, Object> getCustomerAnalysis() {
        List<String> ages = Arrays.asList("18-25", "26-35", "36-45", "46-55", "55+");
        List<Integer> counts = new ArrayList<>();
        
        for (int i = 0; i < ages.size(); i++) {
            counts.add(ThreadLocalRandom.current().nextInt(100, 1000));
        }
        
        // 生成客户数据明细
        List<Map<String, Object>> customerData = new ArrayList<>();
        String[] names = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十", "郑十一", "王十二"};
        String[] regions = {"北京", "上海", "广州", "深圳", "成都", "杭州", "武汉", "西安", "南京", "重庆"};
        String[] genderList = {"男", "女"};
        
        for (int i = 0; i < 10; i++) {
            Map<String, Object> customer = new HashMap<>();
            customer.put("id", i + 1);
            customer.put("name", names[i]);
            customer.put("region", regions[i]);
            customer.put("age", ThreadLocalRandom.current().nextInt(18, 60));
            customer.put("gender", genderList[i % 2]);
            customer.put("orders", ThreadLocalRandom.current().nextInt(5, 50));
            customer.put("totalAmount", ThreadLocalRandom.current().nextInt(5000, 50000));
            customer.put("avgAmount", ThreadLocalRandom.current().nextDouble(500.0, 2000.0));
            customerData.add(customer);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("ages", ages);
        result.put("counts", counts);
        result.put("totalCustomers", ThreadLocalRandom.current().nextInt(50000, 150000));
        result.put("activeCustomers", ThreadLocalRandom.current().nextInt(30000, 100000));
        result.put("averageOrders", ThreadLocalRandom.current().nextDouble(3.0, 8.0));
        result.put("averageOrderValue", ThreadLocalRandom.current().nextDouble(500.0, 1500.0));
        result.put("customersChange", ThreadLocalRandom.current().nextDouble(5.0, 20.0));
        result.put("activeChange", ThreadLocalRandom.current().nextDouble(3.0, 15.0));
        result.put("avgOrdersChange", ThreadLocalRandom.current().nextDouble(1.0, 10.0));
        result.put("avgOrderValueChange", ThreadLocalRandom.current().nextDouble(0.5, 5.0));
        result.put("customerData", customerData);
        return result;
    }

    // 数据概览数据
    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        return Map.of(
            "totalSales", ThreadLocalRandom.current().nextInt(5000000, 10000000),
            "totalOrders", ThreadLocalRandom.current().nextInt(10000, 30000),
            "totalCustomers", ThreadLocalRandom.current().nextInt(5000, 15000),
            "conversionRate", ThreadLocalRandom.current().nextDouble(2.0, 5.0),
            "salesGrowth", ThreadLocalRandom.current().nextDouble(5.0, 20.0),
            "orderGrowth", ThreadLocalRandom.current().nextDouble(3.0, 15.0)
        );
    }
}

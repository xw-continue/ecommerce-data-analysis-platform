package com.ecommerce.analysis.controller;

import com.ecommerce.analysis.service.SalesDataService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    private SalesDataService salesDataService;

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response, 
                           @RequestParam(required = false) String startDate, 
                           @RequestParam(required = false) String endDate) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=sales_data.xlsx");
            
            // 获取数据
            List<com.ecommerce.analysis.entity.SalesData> data = salesDataService.getSalesDataByDateRange(startDate, endDate);
            
            // 创建Excel工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("销售数据");
            
            // 创建表头
            String[] headers = {"订单ID", "商品名称", "分类", "价格", "数量", "销售额", "地区", "订单日期"};
            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // 填充数据
            for (int i = 0; i < data.size(); i++) {
                com.ecommerce.analysis.entity.SalesData item = data.get(i);
                XSSFRow row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(item.getOrderId());
                row.createCell(1).setCellValue(item.getProductName());
                row.createCell(2).setCellValue(item.getCategory());
                row.createCell(3).setCellValue(item.getPrice().doubleValue());
                row.createCell(4).setCellValue(item.getQuantity());
                row.createCell(5).setCellValue(item.getSales().doubleValue());
                row.createCell(6).setCellValue(item.getRegion());
                row.createCell(7).setCellValue(item.getOrderDate().toString());
            }
            
            // 写入响应
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
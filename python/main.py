import os
import sys

# 创建必要的目录
os.makedirs('../data/raw', exist_ok=True)
os.makedirs('../data/cleaned', exist_ok=True)
os.makedirs('../data/analysis', exist_ok=True)
os.makedirs('../data/prediction', exist_ok=True)

# 导入模块
from data_collection import generate_synthetic_data
from data_cleaning import clean_data
from data_analysis import analyze_sales_trend, analyze_product_category, analyze_region, analyze_hot_products
from data_prediction import predict_sales
from db_connection import connect_to_db, create_tables, insert_sales_data, insert_sales_trend, insert_product_category, insert_region_analysis, insert_hot_products, insert_sales_prediction

def main():
    """主函数"""
    print("=== 电商销售数据分析与可视化平台 ===")
    print("开始执行数据处理流程...")
    
    # 1. 数据采集
    print("\n1. 数据采集")
    raw_data = generate_synthetic_data()
    raw_data_path = '../data/raw/sales_data_raw.csv'
    raw_data.to_csv(raw_data_path, index=False, encoding='utf-8-sig')
    print(f"生成了 {len(raw_data)} 条原始数据")
    
    # 2. 数据清洗
    print("\n2. 数据清洗")
    cleaned_data = clean_data(raw_data_path)
    cleaned_data_path = '../data/cleaned/sales_data_cleaned.csv'
    print(f"清洗后剩余 {len(cleaned_data)} 条数据")
    
    # 3. 数据多维度分析
    print("\n3. 数据多维度分析")
    sales_trend_daily, sales_trend_monthly = analyze_sales_trend(cleaned_data)
    category_analysis = analyze_product_category(cleaned_data)
    region_analysis = analyze_region(cleaned_data)
    hot_products = analyze_hot_products(cleaned_data)
    
    # 4. 销售预测
    print("\n4. 销售预测")
    predictions = predict_sales(cleaned_data)
    
    # 5. 数据存储到MySQL
    print("\n5. 数据存储到MySQL")
    conn = connect_to_db()
    if conn:
        create_tables(conn)
        insert_sales_data(conn, cleaned_data)
        insert_sales_trend(conn, sales_trend_daily)
        insert_product_category(conn, category_analysis)
        insert_region_analysis(conn, region_analysis)
        insert_hot_products(conn, hot_products)
        insert_sales_prediction(conn, predictions)
        conn.close()
        print("数据成功存储到MySQL数据库")
    else:
        print("数据库连接失败，数据未存储到MySQL")
    
    print("\n=== 数据处理流程完成 ===")
    print("请启动SpringBoot后端和Vue前端查看可视化结果")

if __name__ == "__main__":
    main()
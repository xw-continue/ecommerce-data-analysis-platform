import pandas as pd
import numpy as np
import random
from datetime import datetime, timedelta
import requests
from bs4 import BeautifulSoup

def generate_synthetic_data():
    """生成模拟电商销售数据"""
    # 生成日期范围
    start_date = datetime(2024, 1, 1)
    end_date = datetime(2024, 12, 31)
    date_range = pd.date_range(start=start_date, end=end_date)
    
    # 商品分类
    categories = ['电子产品', '服装', '食品', '家居用品', '美妆', '运动器材', '图书', '玩具']
    
    # 地区
    regions = ['北京', '上海', '广州', '深圳', '杭州', '成都', '武汉', '西安']
    
    # 生成数据
    data = []
    for date in date_range:
        # 每天生成100-200条记录
        daily_records = random.randint(100, 200)
        for _ in range(daily_records):
            category = random.choice(categories)
            region = random.choice(regions)
            # 生成价格（10-1000元）
            price = round(random.uniform(10, 1000), 2)
            # 生成销量（1-10件）
            quantity = random.randint(1, 10)
            # 计算销售额
            sales = round(price * quantity, 2)
            # 生成用户ID
            user_id = f"user_{random.randint(1000, 9999)}"
            # 生成商品ID
            product_id = f"prod_{random.randint(10000, 99999)}"
            # 生成商品名称
            product_name = f"{category}_{random.randint(1, 100)}"
            
            data.append({
                'order_id': f"order_{random.randint(100000, 999999)}",
                'user_id': user_id,
                'product_id': product_id,
                'product_name': product_name,
                'category': category,
                'price': price,
                'quantity': quantity,
                'sales': sales,
                'region': region,
                'order_date': date.strftime('%Y-%m-%d')
            })
    
    df = pd.DataFrame(data)
    return df

def crawl_data():
    """爬虫采集数据（示例）"""
    # 这里只是示例，实际爬虫需要根据具体网站调整
    url = "https://example.com/products"
    try:
        response = requests.get(url)
        soup = BeautifulSoup(response.text, 'html.parser')
        # 提取数据逻辑
        # ...
        return pd.DataFrame()  # 返回空数据框作为示例
    except Exception as e:
        print(f"爬虫采集失败: {e}")
        return pd.DataFrame()

def import_public_dataset():
    """导入公开数据集"""
    # 这里使用模拟数据作为示例
    return generate_synthetic_data()

def import_data_from_file(file_path):
    """从文件导入数据"""
    try:
        if file_path.endswith('.csv'):
            df = pd.read_csv(file_path, encoding='utf-8-sig')
        elif file_path.endswith('.xlsx'):
            df = pd.read_excel(file_path)
        else:
            raise Exception("不支持的文件格式")
        return df
    except Exception as e:
        print(f"文件导入失败: {e}")
        return None

def main():
    """主函数"""
    print("=== 数据采集模块 ===")
    
    # 生成模拟数据
    print("1. 生成模拟数据...")
    synthetic_data = generate_synthetic_data()
    print(f"生成了 {len(synthetic_data)} 条模拟数据")
    
    # 保存为CSV
    synthetic_data.to_csv('../data/raw/sales_data_raw.csv', index=False, encoding='utf-8-sig')
    print("模拟数据已保存到 ../data/raw/sales_data_raw.csv")
    
    # 爬虫采集（示例）
    print("\n2. 爬虫采集数据...")
    crawled_data = crawl_data()
    print(f"爬虫采集了 {len(crawled_data)} 条数据")
    
    # 导入公开数据集
    print("\n3. 导入公开数据集...")
    public_data = import_public_dataset()
    print(f"导入了 {len(public_data)} 条公开数据")
    
    return synthetic_data

if __name__ == "__main__":
    main()
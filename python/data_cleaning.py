import pandas as pd
import numpy as np

def clean_data(raw_data_path):
    """清洗数据"""
    print("=== 数据清洗模块 ===")
    
    # 读取原始数据
    print("1. 读取原始数据...")
    df = pd.read_csv(raw_data_path, encoding='utf-8-sig')
    print(f"原始数据形状: {df.shape}")
    
    # 1. 去重
    print("\n2. 去重处理...")
    before_duplicates = len(df)
    df = df.drop_duplicates()
    after_duplicates = len(df)
    print(f"去重前: {before_duplicates} 条, 去重后: {after_duplicates} 条, 去除重复: {before_duplicates - after_duplicates} 条")
    
    # 2. 缺失值处理
    print("\n3. 缺失值处理...")
    missing_values = df.isnull().sum()
    print("缺失值统计:")
    print(missing_values[missing_values > 0])
    
    # 填充缺失值
    if 'price' in df.columns and df['price'].isnull().any():
        df['price'] = df['price'].fillna(df['price'].mean())
    if 'quantity' in df.columns and df['quantity'].isnull().any():
        df['quantity'] = df['quantity'].fillna(1)
    if 'sales' in df.columns and df['sales'].isnull().any():
        df['sales'] = df['sales'].fillna(df['price'] * df['quantity'])
    if 'category' in df.columns and df['category'].isnull().any():
        df['category'] = df['category'].fillna('其他')
    if 'region' in df.columns and df['region'].isnull().any():
        df['region'] = df['region'].fillna('未知')
    
    # 3. 异常值处理
    print("\n4. 异常值处理...")
    
    # 价格异常值过滤（保留10-10000元之间的数据）
    if 'price' in df.columns:
        before_price = len(df)
        df = df[(df['price'] >= 10) & (df['price'] <= 10000)]
        after_price = len(df)
        print(f"价格异常值过滤: 去除 {before_price - after_price} 条")
    
    # 销量异常值过滤（保留1-100件之间的数据）
    if 'quantity' in df.columns:
        before_quantity = len(df)
        df = df[(df['quantity'] >= 1) & (df['quantity'] <= 100)]
        after_quantity = len(df)
        print(f"销量异常值过滤: 去除 {before_quantity - after_quantity} 条")
    
    # 销售额异常值过滤（保留10-100000元之间的数据）
    if 'sales' in df.columns:
        before_sales = len(df)
        df = df[(df['sales'] >= 10) & (df['sales'] <= 100000)]
        after_sales = len(df)
        print(f"销售额异常值过滤: 去除 {before_sales - after_sales} 条")
    
    # 4. 格式统一
    print("\n5. 格式统一...")
    
    # 确保日期格式正确
    if 'order_date' in df.columns:
        df['order_date'] = pd.to_datetime(df['order_date']).dt.strftime('%Y-%m-%d')
    
    # 确保数值类型正确
    if 'price' in df.columns:
        df['price'] = df['price'].astype(float)
    if 'quantity' in df.columns:
        df['quantity'] = df['quantity'].astype(int)
    if 'sales' in df.columns:
        df['sales'] = df['sales'].astype(float)
    
    print(f"\n清洗后数据形状: {df.shape}")
    
    # 保存清洗后的数据
    cleaned_data_path = '../data/cleaned/sales_data_cleaned.csv'
    df.to_csv(cleaned_data_path, index=False, encoding='utf-8-sig')
    print(f"清洗后数据已保存到 {cleaned_data_path}")
    
    return df

def main():
    """主函数"""
    raw_data_path = '../data/raw/sales_data_raw.csv'
    cleaned_data = clean_data(raw_data_path)
    return cleaned_data

if __name__ == "__main__":
    main()
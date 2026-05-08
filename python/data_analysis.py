import pandas as pd
import numpy as np
from datetime import datetime

def analyze_sales_trend(df):
    """分析销售趋势"""
    print("=== 销售趋势分析 ===")
    
    # 按日期分组，计算每天的销售额和销量
    df['order_date'] = pd.to_datetime(df['order_date'])
    daily_trend = df.groupby('order_date').agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    daily_trend.columns = ['date', 'total_sales', 'total_quantity', 'order_count']
    
    # 计算月度趋势
    df['month'] = df['order_date'].dt.to_period('M')
    monthly_trend = df.groupby('month').agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    monthly_trend['month'] = monthly_trend['month'].astype(str)
    monthly_trend.columns = ['month', 'total_sales', 'total_quantity', 'order_count']
    
    print(f"\n每日销售趋势数据: {len(daily_trend)} 条")
    print(f"月度销售趋势数据: {len(monthly_trend)} 条")
    
    # 保存结果
    daily_trend.to_csv('../data/analysis/sales_trend_daily.csv', index=False, encoding='utf-8-sig')
    monthly_trend.to_csv('../data/analysis/sales_trend_monthly.csv', index=False, encoding='utf-8-sig')
    
    return daily_trend, monthly_trend

def analyze_by_time_period(df, period='month'):
    """按时间段分析销售数据"""
    print(f"\n=== 按{period}分析 ===")
    
    df['order_date'] = pd.to_datetime(df['order_date'])
    
    if period == 'day':
        df['period'] = df['order_date'].dt.date
    elif period == 'week':
        df['period'] = df['order_date'].dt.isocalendar().week
    elif period == 'month':
        df['period'] = df['order_date'].dt.to_period('M')
    elif period == 'quarter':
        df['period'] = df['order_date'].dt.to_period('Q')
    elif period == 'year':
        df['period'] = df['order_date'].dt.year
    
    period_analysis = df.groupby('period').agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    period_analysis.columns = ['period', 'total_sales', 'total_quantity', 'order_count']
    
    print(f"{period}分析数据: {len(period_analysis)} 条")
    print(f"\nTop 5 {period}销售:")
    print(period_analysis.sort_values('total_sales', ascending=False).head())
    
    # 保存结果
    period_analysis.to_csv(f'../data/analysis/sales_by_{period}.csv', index=False, encoding='utf-8-sig')
    
    return period_analysis

def analyze_product_category(df):
    """分析商品分类"""
    print("\n=== 商品分类分析 ===")
    
    # 按分类分组，计算销售额、销量和订单数
    category_analysis = df.groupby('category').agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    category_analysis.columns = ['category', 'total_sales', 'total_quantity', 'order_count']
    
    # 计算占比
    total_sales = category_analysis['total_sales'].sum()
    category_analysis['sales_percentage'] = round((category_analysis['total_sales'] / total_sales) * 100, 2)
    
    # 按销售额排序
    category_analysis = category_analysis.sort_values('total_sales', ascending=False)
    
    print(f"商品分类分析数据: {len(category_analysis)} 条")
    print("\nTop 5 销售分类:")
    print(category_analysis.head())
    
    # 保存结果
    category_analysis.to_csv('../data/analysis/product_category_analysis.csv', index=False, encoding='utf-8-sig')
    
    return category_analysis

def analyze_region(df):
    """分析地区销售"""
    print("\n=== 地区销售分析 ===")
    
    # 按地区分组，计算销售额、销量和订单数
    region_analysis = df.groupby('region').agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    region_analysis.columns = ['region', 'total_sales', 'total_quantity', 'order_count']
    
    # 计算占比
    total_sales = region_analysis['total_sales'].sum()
    region_analysis['sales_percentage'] = round((region_analysis['total_sales'] / total_sales) * 100, 2)
    
    # 按销售额排序
    region_analysis = region_analysis.sort_values('total_sales', ascending=False)
    
    print(f"地区销售分析数据: {len(region_analysis)} 条")
    print("\nTop 5 销售地区:")
    print(region_analysis.head())
    
    # 保存结果
    region_analysis.to_csv('../data/analysis/region_analysis.csv', index=False, encoding='utf-8-sig')
    
    return region_analysis

def analyze_hot_products(df):
    """分析热销商品"""
    print("\n=== 热销商品分析 ===")
    
    # 按商品分组，计算销售额、销量和订单数
    product_analysis = df.groupby(['product_id', 'product_name']).agg({
        'sales': 'sum',
        'quantity': 'sum',
        'order_id': 'count'
    }).reset_index()
    
    product_analysis.columns = ['product_id', 'product_name', 'total_sales', 'total_quantity', 'order_count']
    
    # 按销售额排序，取前100个热销商品
    hot_products = product_analysis.sort_values('total_sales', ascending=False).head(100)
    
    print(f"热销商品分析数据: {len(hot_products)} 条")
    print("\nTop 10 热销商品:")
    print(hot_products.head(10))
    
    # 保存结果
    hot_products.to_csv('../data/analysis/hot_products.csv', index=False, encoding='utf-8-sig')
    
    return hot_products

def main():
    """主函数"""
    # 读取清洗后的数据
    cleaned_data_path = '../data/cleaned/sales_data_cleaned.csv'
    df = pd.read_csv(cleaned_data_path, encoding='utf-8-sig')
    
    # 执行分析
    sales_trend_daily, sales_trend_monthly = analyze_sales_trend(df)
    category_analysis = analyze_product_category(df)
    region_analysis = analyze_region(df)
    hot_products = analyze_hot_products(df)
    
    # 按不同时间段分析
    analyze_by_time_period(df, 'day')
    analyze_by_time_period(df, 'week')
    analyze_by_time_period(df, 'quarter')
    analyze_by_time_period(df, 'year')
    
    print("\n=== 分析完成 ===")
    return {
        'sales_trend_daily': sales_trend_daily,
        'sales_trend_monthly': sales_trend_monthly,
        'category_analysis': category_analysis,
        'region_analysis': region_analysis,
        'hot_products': hot_products
    }

if __name__ == "__main__":
    main()
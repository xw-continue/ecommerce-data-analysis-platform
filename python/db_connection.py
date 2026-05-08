import mysql.connector
import pandas as pd

def connect_to_db():
    """连接到MySQL数据库"""
    try:
        conn = mysql.connector.connect(
            host='localhost',
            user='root',
            password='root',  # 使用正确的密码
            database='ecommerce_analysis'
        )
        print("成功连接到MySQL数据库")
        return conn
    except Exception as e:
        print(f"数据库连接失败: {e}")
        return None

def create_tables(conn):
    """创建数据库表"""
    if not conn:
        return
    
    cursor = conn.cursor()
    
    # 创建销售数据表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS sales_data (
        id INT AUTO_INCREMENT PRIMARY KEY,
        order_id VARCHAR(20) NOT NULL,
        user_id VARCHAR(20) NOT NULL,
        product_id VARCHAR(20) NOT NULL,
        product_name VARCHAR(100) NOT NULL,
        category VARCHAR(50) NOT NULL,
        price DECIMAL(10,2) NOT NULL,
        quantity INT NOT NULL,
        sales DECIMAL(10,2) NOT NULL,
        region VARCHAR(50) NOT NULL,
        order_date DATE NOT NULL
    )
    ''')
    
    # 创建销售趋势表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS sales_trend (
        id INT AUTO_INCREMENT PRIMARY KEY,
        date DATE NOT NULL,
        total_sales DECIMAL(15,2) NOT NULL,
        total_quantity INT NOT NULL,
        order_count INT NOT NULL
    )
    ''')
    
    # 创建商品分类分析表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS product_category (
        id INT AUTO_INCREMENT PRIMARY KEY,
        category VARCHAR(50) NOT NULL,
        total_sales DECIMAL(15,2) NOT NULL,
        total_quantity INT NOT NULL,
        order_count INT NOT NULL,
        sales_percentage DECIMAL(5,2) NOT NULL
    )
    ''')
    
    # 创建地区销售分析表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS region_analysis (
        id INT AUTO_INCREMENT PRIMARY KEY,
        region VARCHAR(50) NOT NULL,
        total_sales DECIMAL(15,2) NOT NULL,
        total_quantity INT NOT NULL,
        order_count INT NOT NULL,
        sales_percentage DECIMAL(5,2) NOT NULL
    )
    ''')
    
    # 创建热销商品表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS hot_products (
        id INT AUTO_INCREMENT PRIMARY KEY,
        product_id VARCHAR(20) NOT NULL,
        product_name VARCHAR(100) NOT NULL,
        total_sales DECIMAL(15,2) NOT NULL,
        total_quantity INT NOT NULL,
        order_count INT NOT NULL
    )
    ''')
    
    # 创建销售预测表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS sales_prediction (
        id INT AUTO_INCREMENT PRIMARY KEY,
        date DATE NOT NULL,
        predicted_sales DECIMAL(15,2) NOT NULL,
        predicted_quantity INT NOT NULL,
        confidence DECIMAL(5,2) NOT NULL
    )
    ''')
    
    # 创建管理员表
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS admin (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(100) NOT NULL,
        create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
    ''')
    
    # 插入默认管理员账号
    cursor.execute('''
    INSERT IGNORE INTO admin (username, password) VALUES ('admin', '123456')
    ''')
    
    conn.commit()
    cursor.close()
    print("数据库表创建完成")

def insert_sales_data(conn, df):
    """插入销售数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE sales_data')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO sales_data (order_id, user_id, product_id, product_name, category, price, quantity, sales, region, order_date)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        ''', (
            row['order_id'], row['user_id'], row['product_id'], row['product_name'],
            row['category'], row['price'], row['quantity'], row['sales'],
            row['region'], row['order_date']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条销售数据")

def insert_sales_trend(conn, df):
    """插入销售趋势数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE sales_trend')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO sales_trend (date, total_sales, total_quantity, order_count)
        VALUES (%s, %s, %s, %s)
        ''', (
            row['date'], row['total_sales'], row['total_quantity'], row['order_count']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条销售趋势数据")

def insert_product_category(conn, df):
    """插入商品分类数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE product_category')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO product_category (category, total_sales, total_quantity, order_count, sales_percentage)
        VALUES (%s, %s, %s, %s, %s)
        ''', (
            row['category'], row['total_sales'], row['total_quantity'],
            row['order_count'], row['sales_percentage']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条商品分类数据")

def insert_region_analysis(conn, df):
    """插入地区分析数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE region_analysis')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO region_analysis (region, total_sales, total_quantity, order_count, sales_percentage)
        VALUES (%s, %s, %s, %s, %s)
        ''', (
            row['region'], row['total_sales'], row['total_quantity'],
            row['order_count'], row['sales_percentage']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条地区分析数据")

def insert_hot_products(conn, df):
    """插入热销商品数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE hot_products')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO hot_products (product_id, product_name, total_sales, total_quantity, order_count)
        VALUES (%s, %s, %s, %s, %s)
        ''', (
            row['product_id'], row['product_name'], row['total_sales'],
            row['total_quantity'], row['order_count']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条热销商品数据")

def insert_sales_prediction(conn, df):
    """插入销售预测数据"""
    if not conn or df.empty:
        return
    
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute('TRUNCATE TABLE sales_prediction')
    
    # 插入数据
    for _, row in df.iterrows():
        cursor.execute('''
        INSERT INTO sales_prediction (date, predicted_sales, predicted_quantity, confidence)
        VALUES (%s, %s, %s, %s)
        ''', (
            row['date'], row['predicted_sales'], row['predicted_quantity'], row['confidence']
        ))
    
    conn.commit()
    cursor.close()
    print(f"成功插入 {len(df)} 条销售预测数据")

def main():
    """主函数"""
    conn = connect_to_db()
    if conn:
        create_tables(conn)
        conn.close()

if __name__ == "__main__":
    main()
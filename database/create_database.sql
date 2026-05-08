-- 创建数据库
CREATE DATABASE IF NOT EXISTS ecommerce_analysis DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE ecommerce_analysis;

-- 创建销售数据表
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
);

-- 创建销售趋势表
CREATE TABLE IF NOT EXISTS sales_trend (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    total_sales DECIMAL(15,2) NOT NULL,
    total_quantity INT NOT NULL,
    order_count INT NOT NULL
);

-- 创建商品分类分析表
CREATE TABLE IF NOT EXISTS product_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    total_sales DECIMAL(15,2) NOT NULL,
    total_quantity INT NOT NULL,
    order_count INT NOT NULL,
    sales_percentage DECIMAL(5,2) NOT NULL
);

-- 创建地区销售分析表
CREATE TABLE IF NOT EXISTS region_analysis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    region VARCHAR(50) NOT NULL,
    total_sales DECIMAL(15,2) NOT NULL,
    total_quantity INT NOT NULL,
    order_count INT NOT NULL,
    sales_percentage DECIMAL(5,2) NOT NULL
);

-- 创建热销商品表
CREATE TABLE IF NOT EXISTS hot_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(20) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    total_sales DECIMAL(15,2) NOT NULL,
    total_quantity INT NOT NULL,
    order_count INT NOT NULL
);

-- 创建销售预测表
CREATE TABLE IF NOT EXISTS sales_prediction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    predicted_sales DECIMAL(15,2) NOT NULL,
    predicted_quantity INT NOT NULL,
    confidence DECIMAL(5,2) NOT NULL
);

-- 创建管理员表
CREATE TABLE IF NOT EXISTS admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入默认管理员账号
INSERT IGNORE INTO admin (username, password) VALUES ('admin', '123456');

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入测试用户数据
INSERT IGNORE INTO user (username, password, role) VALUES 
('user1', '123456', 'user'),
('admin1', '123456', 'admin');

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS operation_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(255) NOT NULL,
    operator VARCHAR(50) NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入测试操作日志
INSERT IGNORE INTO operation_logs (operation, operator) VALUES 
('系统初始化', 'system'),
('创建默认管理员账号', 'system');

-- 显示创建的表
SHOW TABLES;
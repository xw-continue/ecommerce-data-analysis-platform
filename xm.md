# 电商销售数据分析与可视化平台

## 项目概述

本项目为大数据技术专业融合型毕业设计，以电商销售业务为背景，采用**Python轻量化大数据处理为内核、SpringBoot+Vue可视化开发为载体**的分层架构，实现电商销售数据「采集-清洗-分析-挖掘-可视化落地」全流程闭环。

## 核心技术栈

| 技术层级         | 核心技术选型                         | 核心用途                     |
|----------------|--------------------------------|--------------------------|
| Python大数据层   | Python3.8+、Pandas、Scikit-learn | 数据采集、清洗、分析、预测         |
| JavaWeb后端      | SpringBoot、MyBatis-Plus、MySQL8.0 | 接口开发、数据存储、业务逻辑         |
| JavaWeb前端      | Vue3、Element Plus、ECharts5       | 可视化大屏、交互页面、图表渲染        |
| 辅助工具         | PyCharm、IDEA、VS Code、Postman    | 开发调试、接口测试、数据库管理        |

## 项目结构

```
ecommerce-analysis/
├── backend/            # SpringBoot后端
│   ├── src/           # 源代码
│   └── pom.xml        # Maven配置文件
├── frontend/           # Vue前端
│   ├── src/           # 源代码
│   ├── package.json   # npm配置文件
│   └── vite.config.js # Vite配置文件
├── python/             # Python大数据核心层
│   ├── data_collection.py    # 数据采集模块
│   ├── data_cleaning.py      # 数据清洗模块
│   ├── data_analysis.py      # 数据分析模块
│   ├── data_prediction.py    # 数据预测模块
│   ├── db_connection.py      # 数据库连接模块
│   ├── main.py               # 主脚本
│   └── requirements.txt      # 依赖配置
├── database/           # 数据库脚本
│   └── create_database.sql  # 创建数据库和表结构
└── README.md           # 项目说明文档
```

## 系统运行步骤

### 1. 环境准备

- **硬件**：普通笔记本/台式电脑（CPU≥i5、内存≥8G）
- **软件**：
  - Windows10/11
  - JDK1.8+
  - Python3.8+
  - MySQL8.0+
  - Node.js16+

### 2. 数据库配置

1. **启动MySQL服务**
   - 确保MySQL8.0服务已启动

2. **创建数据库和表结构**
   - 打开MySQL命令行或工具（如Navicat、MySQL Workbench）
   - 执行 `database/create_database.sql` 脚本
   - 脚本会自动创建数据库 `ecommerce_analysis` 和所有必要的表结构

### 3. Python大数据核心层

1. **安装依赖**
   ```bash
   cd python
   pip install -r requirements.txt
   ```

2. **运行数据处理流程**
   ```bash
   python main.py
   ```
   - 该脚本会：
     - 生成模拟电商销售数据
     - 清洗数据（去重、缺失值补全、异常值过滤）
     - 执行多维度分析（销售趋势、商品分类、地区销售、热销商品）
     - 基于线性回归模型进行销售预测
     - 将所有结果存储到MySQL数据库

### 4. SpringBoot后端

1. **构建项目**
   ```bash
   cd backend
   mvn clean install
   ```

2. **启动后端服务**
   ```bash
   mvn spring-boot:run
   ```
   - 后端服务将在 `http://localhost:8080/api` 启动
   - 提供RESTful API接口，支持前端数据查询和分析结果获取

### 5. Vue前端

1. **安装依赖**
   ```bash
   cd frontend
   npm install
   ```

2. **启动前端服务**
   ```bash
   npm run dev
   ```
   - 前端服务将在 `http://localhost:5173` 启动

### 6. 访问系统

- **游客端**：直接访问 `http://localhost:5173`，查看可视化大屏
- **管理员端**：访问 `http://localhost:5173/login`，使用默认账号 `admin`、密码 `123456` 登录

## 核心功能

### Python大数据核心层
- **数据采集**：支持模拟数据生成、爬虫采集、公开数据集导入
- **数据清洗**：基于Pandas实现去重、缺失值补全、异常值过滤、格式统一
- **多维度分析**：从销售趋势、商品分类、用户/地区3大核心维度挖掘数据价值
- **数据预测**：基于Scikit-learn线性回归模型实现下月销量/销售额预测

### JavaWeb后端
- **接口开发**：提供RESTful API接口，支持数据查询、分析结果获取
- **数据存储**：使用MySQL8.0存储清洗后的数据和分析结果
- **业务逻辑**：实现管理员登录、密码修改、数据管理等功能

### Vue前端
- **可视化大屏**：通过ECharts5实现趋势图、占比图、热销商品排行、地区销售分布、销量预测图等
- **交互页面**：支持数据查询、分析报告、数据管理、系统管理等功能
- **响应式设计**：适配不同屏幕尺寸，提供良好的用户体验

## 注意事项

1. **MySQL配置**
   - 默认数据库配置：用户名 `root`，密码 `123456`，数据库名 `ecommerce_analysis`
   - 如需修改配置，请修改 `backend/src/main/resources/application.yml` 文件

2. **端口冲突**
   - 后端默认端口：8080
   - 前端默认端口：5173
   - 如有端口冲突，请修改相应配置文件

3. **数据生成**
   - Python脚本默认生成2024年全年的模拟数据
   - 每次运行脚本都会重新生成数据并覆盖数据库中的内容

4. **性能优化**
   - 对于大规模数据，可能需要调整JVM内存配置和MySQL参数
   - 前端图表渲染可能会在数据量较大时出现性能问题，可考虑分页加载或数据聚合

5. **安全性**
   - 本项目为毕业设计，未实现完整的安全机制
   - 生产环境部署时需添加认证、授权、加密等安全措施

## 故障排查

1. **MySQL连接失败**
   - 检查MySQL服务是否启动
   - 检查用户名、密码和数据库名是否正确
   - 检查网络连接和防火墙设置

2. **Python依赖安装失败**
   - 确保Python版本≥3.8
   - 尝试使用 `pip install --user -r requirements.txt` 安装到用户目录
   - 检查网络连接和pip镜像源设置

3. **前端启动失败**
   - 确保Node.js版本≥16
   - 检查依赖是否安装成功
   - 检查端口是否被占用

4. **后端启动失败**
   - 确保JDK版本≥1.8
   - 检查Maven依赖是否下载成功
   - 检查数据库连接配置是否正确

## 项目价值

1. **专业价值**：完整覆盖大数据专业「采集-清洗-存储-分析-挖掘」核心全流程，实现分析成果工程化落地
2. **核心优势**：专业贴合度高、开发成本低、可行性极强、成果完整
3. **应用价值**：可为电商运营人员提供直观的销售数据展示、多维度分析结果与销量预测，为库存管理、促销规划、区域运营等决策提供数据支撑

## 联系方式

如有问题或建议，请联系项目开发者。
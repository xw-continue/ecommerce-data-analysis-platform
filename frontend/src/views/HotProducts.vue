<template>
  <div class="hot-products">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>热门商品分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>热门商品</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-select v-model="filterCategory" size="small" class="category-select">
            <el-option label="全部分类" value="all" />
            <el-option label="电子产品" value="electronics" />
            <el-option label="服装" value="clothing" />
            <el-option label="食品" value="food" />
            <el-option label="家居用品" value="home" />
          </el-select>
          <el-select v-model="sortBy" size="small" class="sort-select">
            <el-option label="按销量排序" value="quantity" />
            <el-option label="按销售额排序" value="sales" />
            <el-option label="按订单数排序" value="orders" />
          </el-select>
        </div>
        <div class="filter-right">
          <el-button type="primary" size="small" @click="exportData" class="export-button">
            <el-icon><i-ep-download /></el-icon>
            导出数据
          </el-button>
        </div>
      </div>
      
      <!-- 热门商品概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              🏪
            </div>
            <div class="overview-info">
              <div class="overview-title">热门商品数</div>
              <div class="overview-value">{{ hotProductCount }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💰
            </div>
            <div class="overview-info">
              <div class="overview-title">总销售额</div>
              <div class="overview-value">¥{{ totalSales.toLocaleString() }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💳
            </div>
            <div class="overview-info">
              <div class="overview-title">平均销售额</div>
              <div class="overview-value">¥{{ averageSales.toFixed(2) }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              🏆
            </div>
            <div class="overview-info">
              <div class="overview-title">最热销商品</div>
              <div class="overview-value">{{ topProduct }}</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 热门商品图表 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>热门商品销量排行</h3>
          </div>
          <div class="chart-container">
            <div id="hotProductsChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 热门商品详情 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>热门商品详情</h3>
          </div>
          <div class="detail-content">
            <el-table :data="hotProductsData" style="width: 100%">
              <el-table-column type="index" label="排名" width="80" />
              <el-table-column prop="name" label="商品名称" width="250" />
              <el-table-column prop="category" label="分类" width="120" />
              <el-table-column prop="price" label="单价" width="100">
                <template #default="scope">
                  ¥{{ scope.row.price.toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column prop="quantity" label="销量" width="100" />
              <el-table-column prop="sales" label="销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="change" label="环比" width="100">
                <template #default="scope">
                  <span :class="[scope.row.change >= 0 ? 'positive' : 'negative', 'percentage']">
                    {{ scope.row.change >= 0 ? '+' : '' }}{{ scope.row.change.toFixed(1) }}%
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="detail-footer">
            <el-pagination
              v-model:current-page="currentPage"
              v-model:page-size="pageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="hotProductsData.length"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import apiClient from '../utils/api'

export default {
  data() {
    return {
      filterCategory: 'all',
      sortBy: 'quantity',
      currentPage: 1,
      pageSize: 10,
      hotProductCount: 20,
      totalSales: 1234567,
      averageSales: 61728.35,
      topProduct: '智能手机 Pro Max',
      hotProductsData: [
        { name: '智能手机 Pro Max', category: '电子产品', price: 9999, quantity: 5678, sales: 56774322, orders: 5678, change: 25.6 },
        { name: '无线蓝牙耳机', category: '电子产品', price: 1299, quantity: 4567, sales: 5932533, orders: 4567, change: 18.7 },
        { name: '运动休闲鞋', category: '服装', price: 899, quantity: 3456, sales: 3107944, orders: 3456, change: 15.2 },
        { name: '智能手表', category: '电子产品', price: 2999, quantity: 2345, sales: 7032655, orders: 2345, change: 12.8 },
        { name: '纯棉T恤', category: '服装', price: 199, quantity: 1234, sales: 245566, orders: 1234, change: 10.5 },
        { name: '坚果礼盒', category: '食品', price: 299, quantity: 1123, sales: 335777, orders: 1123, change: 8.3 },
        { name: '智能音箱', category: '电子产品', price: 899, quantity: 1012, sales: 909788, orders: 1012, change: 7.7 },
        { name: '护肤套装', category: '化妆品', price: 599, quantity: 901, sales: 539699, orders: 901, change: 6.2 },
        { name: '厨房小家电', category: '家居用品', price: 399, quantity: 876, sales: 349524, orders: 876, change: 5.8 },
        { name: '儿童玩具', category: '其他', price: 199, quantity: 765, sales: 152235, orders: 765, change: 4.5 }
      ],
      chart: null,
      refreshTimer: null
    }
  },
  mounted() {
    this.fetchData()
    this.initChart()
    // 设置定时器，每60秒刷新一次数据
    this.refreshTimer = setInterval(() => {
      this.fetchData()
    }, 60000)
  },
  beforeUnmount() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
      this.refreshTimer = null
    }
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  activated() {
    // 当组件被激活时重新初始化图表
    this.$nextTick(() => {
      this.fetchData()
      this.initChart()
      // 重新设置定时器
      this.refreshTimer = setInterval(() => {
        this.fetchData()
      }, 60000)
    })
  },
  deactivated() {
    // 当组件被停用时销毁图表和清除定时器
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
      this.refreshTimer = null
    }
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  methods: {
    initChart() {
      const chartDom = document.getElementById('hotProductsChart')
      this.chart = echarts.init(chartDom)
      this.updateChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        this.chart.resize()
      })
    },
    updateChart() {
      const products = this.hotProductsData.map(item => item.name)
      const quantities = this.hotProductsData.map(item => item.quantity)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: products,
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        series: [
          {
            name: '销量',
            type: 'bar',
            data: quantities,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.chart.setOption(option)
    },
    handleSizeChange(size) {
      this.pageSize = size
    },
    handleCurrentChange(current) {
      this.currentPage = current
    },
    async fetchData() {
      try {
        // 获取热门商品数据
        const hotProductsResponse = await apiClient.get('/mock-data/hot-products')
        const hotProductsData = hotProductsResponse.data
        
        // 计算总销售额
        this.totalSales = hotProductsData.sales.reduce((sum, value) => sum + value, 0)
        
        // 计算平均销售额
        this.averageSales = this.totalSales / hotProductsData.products.length
        
        // 更新热门商品数
        this.hotProductCount = hotProductsData.products.length
        
        // 找到最热销商品
        const maxSalesIndex = hotProductsData.sales.indexOf(Math.max(...hotProductsData.sales))
        this.topProduct = hotProductsData.products[maxSalesIndex]
        
        // 生成热门商品详情数据
        this.hotProductsData = hotProductsData.products.map((product, index) => ({
          name: product,
          category: this.getCategoryForProduct(product),
          price: 100 + Math.floor(Math.random() * 9900),
          quantity: Math.floor(hotProductsData.sales[index] / 100),
          sales: hotProductsData.sales[index],
          orders: Math.floor(hotProductsData.sales[index] / 200),
          change: Math.random() * 30 - 5
        }))
        
        // 按当前排序方式排序
        this.hotProductsData.sort((a, b) => {
          return b[this.sortBy] - a[this.sortBy]
        })
        
        // 更新图表
        this.updateChart()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    },
    getCategoryForProduct(product) {
      const categories = {
        '智能手机': '电子产品',
        '无线蓝牙耳机': '电子产品',
        '智能手表': '电子产品',
        '智能音箱': '电子产品',
        '运动休闲鞋': '服装',
        '纯棉T恤': '服装',
        '坚果礼盒': '食品',
        '护肤套装': '化妆品',
        '厨房小家电': '家居用品',
        '儿童玩具': '其他'
      }
      
      for (const [key, category] of Object.entries(categories)) {
        if (product.includes(key)) {
          return category
        }
      }
      return '其他'
    },
    filterProductsByCategory() {
      // 实现分类筛选功能
      if (this.filterCategory === 'all') {
        // 显示所有商品
        this.fetchData()
      } else {
        // 根据分类筛选
        const filteredData = this.hotProductsData.filter(item => {
          // 映射前端分类值到后端分类名称
          const categoryMap = {
            'electronics': '电子产品',
            'clothing': '服装',
            'food': '食品',
            'home': '家居用品'
          }
          return item.category === categoryMap[this.filterCategory]
        })
        
        // 如果没有匹配的商品，保持原有数据
        if (filteredData.length > 0) {
          this.hotProductsData = filteredData
        }
      }
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['排名', '商品名称', '分类', '单价', '销量', '销售额', '订单数', '环比']
      const rows = this.hotProductsData.map((item, index) => [
        index + 1,
        item.name,
        item.category,
        `¥${item.price.toFixed(2)}`,
        item.quantity,
        `¥${item.sales.toLocaleString()}`,
        item.orders,
        `${item.change >= 0 ? '+' : ''}${item.change}%`
      ])
      
      // 组合CSV内容
      const csvContent = [
        headers.join(','),
        ...rows.map(row => row.join(','))
      ].join('\n')
      
      // 创建下载链接
      const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' })
      const link = document.createElement('a')
      
      if (link.download !== undefined) {
        const url = URL.createObjectURL(blob)
        link.setAttribute('href', url)
        link.setAttribute('download', `热门商品数据_${new Date().toISOString().split('T')[0]}.csv`)
        link.style.visibility = 'hidden'
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        this.$message.success('数据导出成功')
      } else {
        this.$message.error('浏览器不支持导出功能')
      }
    }
  },
  watch: {
    sortBy() {
      // 模拟排序
      this.hotProductsData.sort((a, b) => {
        return b[this.sortBy] - a[this.sortBy]
      })
      this.updateChart()
    },
    filterCategory() {
      // 实现分类筛选功能
      this.filterProductsByCategory()
      this.updateChart()
    }
  }
}
</script>

<style scoped>
.hot-products {
  width: 100%;
  min-height: 100vh;
  background-color: var(--bg-gray);
  padding: 24px 0;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h1 {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.filter-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 24px 0;
  padding: 16px;
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.filter-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.filter-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.category-select {
  width: 150px;
}

.sort-select {
  width: 150px;
}

.export-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.export-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.overview-section {
  margin-bottom: 24px;
}

.overview-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.overview-card {
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  display: flex;
  align-items: center;
  gap: 16px;
  transition: all 0.2s ease;
}

.overview-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-color);
}

.overview-icon {
  font-size: 32px;
  color: white;
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

.overview-card:nth-child(1) .overview-icon {
  background: linear-gradient(135deg, #6a5acd, #9333ea);
}

.overview-card:nth-child(2) .overview-icon {
  background: linear-gradient(135deg, #10b981, #34d399);
}

.overview-card:nth-child(3) .overview-icon {
  background: linear-gradient(135deg, #f59e0b, #fbbf24);
}

.overview-card:nth-child(4) .overview-icon {
  background: linear-gradient(135deg, #3b82f6, #60a5fa);
}

.overview-card:hover .overview-icon {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

.overview-info {
  flex: 1;
}

.overview-title {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 4px;
}

.overview-value {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.chart-section {
  margin-bottom: 24px;
}

.chart-card {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  margin-bottom: 24px;
}
.chart-header {
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-light);
}

.chart-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.chart-container {
  position: relative;
  height: 400px;
}

.detail-section {
  margin-bottom: 24px;
}

.detail-card {
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.detail-header {
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-light);
}

.detail-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.detail-content {
  margin-bottom: 20px;
}

.detail-footer {
  display: flex;
  justify-content: flex-end;
}

.positive {
  color: var(--secondary-color);
}

.negative {
  color: #ef4444;
}

.percentage {
  font-weight: 600;
  color: #6a5acd;
  font-size: 14px;
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .overview-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .filter-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .filter-left,
  .filter-right {
    width: 100%;
  }
  
  .filter-right {
    justify-content: space-between;
  }
}

@media screen and (max-width: 768px) {
  .overview-grid {
    grid-template-columns: 1fr;
  }
  
  .filter-left {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .filter-right {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .chart-container {
    height: 300px;
  }
  
  .detail-footer {
    justify-content: center;
  }
}
</style>

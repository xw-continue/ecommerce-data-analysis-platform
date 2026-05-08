<template>
  <div class="product-category">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>商品分类分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>商品分类</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-select v-model="sortBy" size="small" class="sort-select">
            <el-option label="按销售额排序" value="sales" />
            <el-option label="按销量排序" value="quantity" />
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
      
      <!-- 分类概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              📊
            </div>
            <div class="overview-info">
              <div class="overview-title">分类数量</div>
              <div class="overview-value">{{ categoryCount }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              🏪
            </div>
            <div class="overview-info">
              <div class="overview-title">商品数量</div>
              <div class="overview-value">{{ productCount }}</div>
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
        </div>
      </div>
      
      <!-- 分类占比图表 -->
      <div class="chart-section">
        <div class="chart-row">
          <div class="chart-card">
            <div class="chart-header">
              <h3>分类销售额占比</h3>
            </div>
            <div class="chart-container">
              <div id="salesPieChart" style="width: 100%; height: 300px;"></div>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>分类销量占比</h3>
            </div>
            <div class="chart-container">
              <div id="quantityPieChart" style="width: 100%; height: 300px;"></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 分类详情 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>分类详情</h3>
          </div>
          <div class="detail-content">
            <el-table :data="categoryData" style="width: 100%">
              <el-table-column prop="category" label="分类名称" width="150" />
              <el-table-column prop="sales" label="销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="quantity" label="销量" width="100" />
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="percentage" label="占比" width="100">
                <template #default="scope">
                  <span class="percentage">{{ scope.row.percentage.toFixed(1) }}%</span>
                </template>
              </el-table-column>
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
              :total="categoryData.length"
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
      sortBy: 'sales',
      currentPage: 1,
      pageSize: 10,
      categoryCount: 8,
      productCount: 120,
      totalSales: 1234567,
      averageSales: 154320.88,
      categoryData: [
        { category: '电子产品', sales: 456789, quantity: 12345, orders: 5678, percentage: 37.0, change: 15.2 },
        { category: '服装', sales: 321456, quantity: 23456, orders: 7890, percentage: 26.0, change: 10.5 },
        { category: '食品', sales: 189765, quantity: 34567, orders: 9876, percentage: 15.4, change: 8.3 },
        { category: '家居用品', sales: 123456, quantity: 18901, orders: 4567, percentage: 9.9, change: 5.7 },
        { category: '化妆品', sales: 87654, quantity: 12345, orders: 3456, percentage: 7.1, change: 12.8 },
        { category: '书籍', sales: 54321, quantity: 9876, orders: 2345, percentage: 4.4, change: 3.2 },
        { category: '运动用品', sales: 32109, quantity: 6543, orders: 1234, percentage: 2.6, change: 7.9 },
        { category: '其他', sales: 19417, quantity: 4321, orders: 987, percentage: 1.6, change: 1.5 }
      ],
      salesChart: null,
      quantityChart: null,
      refreshTimer: null
    }
  },
  mounted() {
    this.fetchData()
    this.initCharts()
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
    if (this.salesChart) {
      this.salesChart.dispose()
      this.salesChart = null
    }
    if (this.quantityChart) {
      this.quantityChart.dispose()
      this.quantityChart = null
    }
  },
  activated() {
    // 当组件被激活时重新初始化图表
    this.$nextTick(() => {
      this.fetchData()
      this.initCharts()
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
    if (this.salesChart) {
      this.salesChart.dispose()
      this.salesChart = null
    }
    if (this.quantityChart) {
      this.quantityChart.dispose()
      this.quantityChart = null
    }
  },
  methods: {
    initCharts() {
      this.initSalesChart()
      this.initQuantityChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (this.salesChart) {
          this.salesChart.resize()
        }
        if (this.quantityChart) {
          this.quantityChart.resize()
        }
      })
    },
    initSalesChart() {
      const chartDom = document.getElementById('salesPieChart')
      this.salesChart = echarts.init(chartDom)
      this.updateSalesChart()
    },
    initQuantityChart() {
      const chartDom = document.getElementById('quantityPieChart')
      this.quantityChart = echarts.init(chartDom)
      this.updateQuantityChart()
    },
    updateSalesChart() {
      const categories = this.categoryData.map(item => item.category)
      const sales = this.categoryData.map(item => item.sales)
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: categories
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: [
          {
            name: '销售额',
            type: 'pie',
            radius: '60%',
            center: ['60%', '50%'],
            data: this.categoryData.map(item => ({
              value: item.sales,
              name: item.category
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      
      this.salesChart.setOption(option)
    },
    updateQuantityChart() {
      const categories = this.categoryData.map(item => item.category)
      const quantity = this.categoryData.map(item => item.quantity)
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: categories
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: [
          {
            name: '销量',
            type: 'pie',
            radius: '60%',
            center: ['60%', '50%'],
            data: this.categoryData.map(item => ({
              value: item.quantity,
              name: item.category
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      
      this.quantityChart.setOption(option)
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['分类名称', '销售额', '销量', '占比', '环比']
      const rows = this.categoryData.map(item => [
        item.category,
        `¥${item.sales.toLocaleString()}`,
        item.quantity,
        `${item.percentage.toFixed(1)}%`,
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
        link.setAttribute('download', `商品分类数据_${new Date().toISOString().split('T')[0]}.csv`)
        link.style.visibility = 'hidden'
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        this.$message.success('数据导出成功')
      } else {
        this.$message.error('浏览器不支持导出功能')
      }
    },
    handleSizeChange(size) {
      this.pageSize = size
    },
    handleCurrentChange(current) {
      this.currentPage = current
    },
    async fetchData() {
      try {
        // 获取商品分类数据
        const categoryResponse = await apiClient.get('/mock-data/product-category')
        const categoryData = categoryResponse.data
        
        // 计算总销售额
        this.totalSales = categoryData.sales.reduce((sum, value) => sum + value, 0)
        
        // 计算平均销售额
        this.averageSales = this.totalSales / categoryData.categories.length
        
        // 更新分类数量
        this.categoryCount = categoryData.categories.length
        
        // 生成分类详情数据
        this.categoryData = categoryData.categories.map((category, index) => ({
          category: category,
          sales: categoryData.sales[index],
          quantity: Math.floor(categoryData.sales[index] / 10),
          orders: Math.floor(categoryData.sales[index] / 100),
          percentage: (categoryData.sales[index] / this.totalSales) * 100,
          change: Math.random() * 20 - 5
        }))
        
        // 按当前排序方式排序
        this.categoryData.sort((a, b) => {
          return b[this.sortBy] - a[this.sortBy]
        })
        
        // 更新图表
        this.updateSalesChart()
        this.updateQuantityChart()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    }
  },
  watch: {
    sortBy() {
      // 模拟排序
      this.categoryData.sort((a, b) => {
        return b[this.sortBy] - a[this.sortBy]
      })
      this.updateSalesChart()
      this.updateQuantityChart()
    }
  }
}
</script>

<style scoped>
.product-category {
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

.sort-select {
  width: 180px;
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

.chart-row {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
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
  height: 300px;
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
  
  .chart-row {
    grid-template-columns: 1fr;
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

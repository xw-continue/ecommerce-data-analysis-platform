<template>
  <div class="sales-analysis">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>销售趋势分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>销售趋势</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-radio-group v-model="timeRange" size="small">
            <el-radio-button label="day">日</el-radio-button>
            <el-radio-button label="week">周</el-radio-button>
            <el-radio-button label="month">月</el-radio-button>
            <el-radio-button label="year">年</el-radio-button>
          </el-radio-group>
        </div>
        <div class="filter-right">
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" size="small" class="date-picker" @change="dateRangeChange" />
          <el-button type="primary" size="small" @click="exportData" class="export-button">
            <el-icon><i-ep-download /></el-icon>
            导出数据
          </el-button>
        </div>
      </div>
      
      <!-- 销售概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              💰
            </div>
            <div class="overview-info">
              <div class="overview-title">总销售额</div>
              <div class="overview-value">¥{{ totalSales.toLocaleString() }}</div>
              <div class="overview-change positive">+12.5%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              📋
            </div>
            <div class="overview-info">
              <div class="overview-title">总订单数</div>
              <div class="overview-value">{{ totalOrders.toLocaleString() }}</div>
              <div class="overview-change positive">+8.2%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              🏪
            </div>
            <div class="overview-info">
              <div class="overview-title">总销量</div>
              <div class="overview-value">{{ totalQuantity.toLocaleString() }}</div>
              <div class="overview-change positive">+5.7%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💳
            </div>
            <div class="overview-info">
              <div class="overview-title">平均客单价</div>
              <div class="overview-value">¥{{ averageOrderValue.toFixed(2) }}</div>
              <div class="overview-change positive">+3.1%</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 销售趋势图表 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>销售趋势</h3>
            <div class="chart-controls">
              <el-select v-model="chartType" size="small" class="chart-type-select">
                <el-option label="折线图" value="line" />
                <el-option label="柱状图" value="bar" />
              </el-select>
            </div>
          </div>
          <div class="chart-container">
            <div id="salesTrendChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 销售详情 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>销售详情</h3>
          </div>
          <div class="detail-content">
            <el-table :data="salesData" style="width: 100%">
              <el-table-column prop="date" label="日期" width="120" />
              <el-table-column prop="sales" label="销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="quantity" label="销量" width="100" />
              <el-table-column prop="average" label="平均客单价" width="120">
                <template #default="scope">
                  ¥{{ scope.row.average.toFixed(2) }}
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
              :total="salesData.length"
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
      timeRange: 'month',
      dateRange: [],
      chartType: 'line',
      currentPage: 1,
      pageSize: 10,
      totalSales: 1234567,
      totalOrders: 12345,
      totalQuantity: 56789,
      averageOrderValue: 99.99,
      salesData: [
        { date: '2026-01-01', sales: 123456, orders: 1234, quantity: 5678, average: 99.99, change: 12.5 },
        { date: '2026-01-02', sales: 134567, orders: 1420, quantity: 6789, average: 94.76, change: 9.0 },
        { date: '2026-01-03', sales: 145678, orders: 1580, quantity: 7890, average: 92.20, change: 8.3 },
        { date: '2026-01-04', sales: 156789, orders: 1650, quantity: 8901, average: 95.03, change: 7.7 },
        { date: '2026-01-05', sales: 167890, orders: 1720, quantity: 9012, average: 97.61, change: 7.1 },
        { date: '2026-01-06', sales: 178901, orders: 1850, quantity: 10123, average: 96.70, change: 6.6 },
        { date: '2026-01-07', sales: 190012, orders: 1980, quantity: 11234, average: 95.96, change: 6.2 },
        { date: '2026-01-08', sales: 201123, orders: 2100, quantity: 12345, average: 95.77, change: 5.8 },
        { date: '2026-01-09', sales: 212234, orders: 2150, quantity: 13456, average: 98.71, change: 5.5 },
        { date: '2026-01-10', sales: 223345, orders: 2200, quantity: 14567, average: 101.52, change: 5.2 }
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
      const chartDom = document.getElementById('salesTrendChart')
      this.chart = echarts.init(chartDom)
      this.updateChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        this.chart.resize()
      })
    },
    updateChart() {
      const dates = this.salesData.map(item => item.date)
      const sales = this.salesData.map(item => item.sales)
      const orders = this.salesData.map(item => item.orders)
      
      // 为订单数添加缩放因子，使其在图表上与销售额的线分开显示
      const scaledOrders = orders.map(order => order * 1000)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          },
          formatter: function(params) {
            let result = params[0].name + '<br/>';
            for (let i = 0; i < params.length; i++) {
              if (params[i].seriesName === '订单数') {
                result += params[i].marker + params[i].seriesName + ': ' + (params[i].value / 1000) + '<br/>';
              } else {
                result += params[i].marker + params[i].seriesName + ': ¥' + params[i].value + '<br/>';
              }
            }
            return result;
          }
        },
        legend: {
          data: ['销售额', '订单数'],
          top: '5%',
          right: 'center'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: dates,
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '销售额',
            min: 0,
            axisLabel: {
              formatter: '¥{value}'
            }
          },
          {
            type: 'value',
            name: '订单数',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '销售额',
            type: this.chartType,
            data: sales,
            itemStyle: {
              color: '#6a5acd'
            }
          },
          {
            name: '订单数',
            type: this.chartType,
            yAxisIndex: 1,
            data: scaledOrders,
            itemStyle: {
              color: '#10b981'
            }
          }
        ]
      }
      
      this.chart.setOption(option)
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['日期', '销售额', '订单数', '销量', '平均客单价', '环比']
      const rows = this.salesData.map(item => [
        item.date,
        `¥${item.sales.toLocaleString()}`,
        item.orders,
        item.quantity,
        `¥${item.average.toFixed(2)}`,
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
        link.setAttribute('download', `销售数据_${new Date().toISOString().split('T')[0]}.csv`)
        link.style.visibility = 'hidden'
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        this.$message.success('数据导出成功')
      } else {
        this.$message.error('浏览器不支持导出功能')
      }
    },
    dateRangeChange() {
      // 实现日期范围选择功能
      if (this.dateRange && this.dateRange.length === 2) {
        this.generateDataByDateRange()
        this.updateChart()
      }
    },
    generateDataByDateRange() {
      // 根据选择的日期范围生成数据
      if (!this.dateRange || this.dateRange.length !== 2) return
      
      const startDate = new Date(this.dateRange[0])
      const endDate = new Date(this.dateRange[1])
      const days = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24)) + 1
      
      // 生成新的数据
      const newData = []
      for (let i = 0; i < days; i++) {
        const date = new Date(startDate)
        date.setDate(startDate.getDate() + i)
        const dateStr = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
        
        const sales = 100000 + Math.floor(Math.random() * 150000)
        const orders = 1000 + Math.floor(Math.random() * 500)
        
        newData.push({
          date: dateStr,
          sales: sales,
          orders: orders,
          quantity: 5000 + Math.floor(Math.random() * 10000),
          average: sales / orders,
          change: Math.random() * 20 - 5
        })
      }
      
      this.salesData = newData
      
      // 更新概览数据
      const totalSales = newData.reduce((sum, item) => sum + item.sales, 0)
      const totalOrders = newData.reduce((sum, item) => sum + item.orders, 0)
      const totalQuantity = newData.reduce((sum, item) => sum + item.quantity, 0)
      
      this.totalSales = totalSales
      this.totalOrders = totalOrders
      this.totalQuantity = totalQuantity
      this.averageOrderValue = totalSales / totalOrders
    },
    handleSizeChange(size) {
      this.pageSize = size
    },
    handleCurrentChange(current) {
      this.currentPage = current
    },
    generateDataByTimeRange() {
      // 根据选择的时间范围生成不同的数据
      const now = new Date()
      let days = 30 // 默认30天
      
      switch (this.timeRange) {
        case 'day':
          days = 7 // 日视图显示7天
          break
        case 'week':
          days = 30 // 周视图显示30天
          break
        case 'month':
          days = 90 // 月视图显示90天
          break
        case 'year':
          days = 365 // 年视图显示365天
          break
      }
      
      // 生成新的数据
      const newData = []
      for (let i = days - 1; i >= 0; i--) {
        const date = new Date(now)
        date.setDate(date.getDate() - i)
        const dateStr = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
        
        const sales = 100000 + Math.floor(Math.random() * 150000)
        const orders = 1000 + Math.floor(Math.random() * 500)
        
        newData.push({
          date: dateStr,
          sales: sales,
          orders: orders,
          quantity: 5000 + Math.floor(Math.random() * 10000),
          average: sales / orders,
          change: Math.random() * 20 - 5
        })
      }
      
      this.salesData = newData
      
      // 更新概览数据
      const totalSales = newData.reduce((sum, item) => sum + item.sales, 0)
      const totalOrders = newData.reduce((sum, item) => sum + item.orders, 0)
      const totalQuantity = newData.reduce((sum, item) => sum + item.quantity, 0)
      
      this.totalSales = totalSales
      this.totalOrders = totalOrders
      this.totalQuantity = totalQuantity
      this.averageOrderValue = totalSales / totalOrders
    },
    async fetchData() {
      try {
        // 获取概览数据
        const overviewResponse = await apiClient.get('/mock-data/overview')
        const overviewData = overviewResponse.data
        this.totalSales = overviewData.totalSales
        this.totalOrders = overviewData.totalOrders
        this.totalQuantity = 56789 + Math.floor(Math.random() * 10000)
        this.averageOrderValue = overviewData.totalSales / overviewData.totalOrders

        // 获取销售趋势数据
        const salesTrendResponse = await apiClient.get('/mock-data/sales-trend')
        const salesTrendData = salesTrendResponse.data
        this.salesData = salesTrendData.dates.map((date, index) => ({
          date: `2026-04-${String(index + 1).padStart(2, '0')}`,
          sales: salesTrendData.sales[index],
          orders: salesTrendData.orders[index],
          quantity: 5000 + Math.floor(Math.random() * 10000),
          average: salesTrendData.sales[index] / salesTrendData.orders[index],
          change: Math.random() * 20 - 5
        }))

        // 更新图表
        this.updateChart()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    }
  },
  watch: {
    chartType() {
      this.updateChart()
    },
    timeRange() {
      // 根据时间范围生成不同的数据
      this.generateDataByTimeRange()
      this.updateChart()
    },
    dateRange() {
      // 模拟数据更新
      this.updateChart()
    }
  }
}
</script>

<style scoped>
.sales-analysis {
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
  background-color: var(--bg-light);
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
  background-color: var(--bg-light);
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

.overview-change {
  font-size: 14px;
  font-weight: 600;
  margin-top: 4px;
}

.positive {
  color: #10b981;
}

.negative {
  color: #ef4444;
}

.percentage {
  font-weight: 600;
  color: #6a5acd;
  font-size: 14px;
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
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.chart-controls {
  display: flex;
  align-items: center;
  gap: 12px;
}

.chart-type-select {
  width: 120px;
}

.chart-container {
  position: relative;
  height: 400px;
}

.detail-section {
  margin-bottom: 24px;
}

.detail-card {
  background-color: var(--bg-light);
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
  color: #10b981;
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

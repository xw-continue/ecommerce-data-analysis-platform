<template>
  <div class="payment-analysis">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>支付分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>支付分析</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-select v-model="timeRange" size="small" class="time-select">
            <el-option label="近7天" value="7d" />
            <el-option label="近30天" value="30d" />
            <el-option label="近90天" value="90d" />
            <el-option label="近1年" value="1y" />
          </el-select>
        </div>
        <div class="filter-right">
          <el-button type="primary" size="small" @click="exportData" class="export-button">
            <el-icon><i-ep-download /></el-icon>
            导出数据
          </el-button>
        </div>
      </div>
      
      <!-- 支付概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              💰
            </div>
            <div class="overview-info">
              <div class="overview-title">总支付金额</div>
              <div class="overview-value">¥{{ totalPayment.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ paymentChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              📋
            </div>
            <div class="overview-info">
              <div class="overview-title">支付订单数</div>
              <div class="overview-value">{{ totalOrders.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ ordersChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💳
            </div>
            <div class="overview-info">
              <div class="overview-title">平均支付金额</div>
              <div class="overview-value">¥{{ averagePayment.toFixed(2) }}</div>
              <div class="overview-change positive">+{{ avgPaymentChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              ✅
            </div>
            <div class="overview-info">
              <div class="overview-title">支付成功率</div>
              <div class="overview-value">{{ successRate.toFixed(1) }}%</div>
              <div class="overview-change positive">+{{ successRateChange.toFixed(1) }}%</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 支付方式占比 -->
      <div class="chart-section">
        <div class="chart-grid">
          <div class="chart-card">
            <div class="chart-header">
              <h3>支付方式占比</h3>
            </div>
            <div class="chart-container">
              <div id="paymentMethodChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>支付渠道分布</h3>
            </div>
            <div class="chart-container">
              <div id="paymentChannelChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 支付趋势 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>支付趋势</h3>
          </div>
          <div class="chart-container">
            <div id="paymentTrendChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 支付时间分布 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>支付时间分布</h3>
          </div>
          <div class="chart-container">
            <div id="paymentTimeChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 支付数据明细 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>支付数据明细</h3>
          </div>
          <div class="detail-content">
            <el-table :data="paymentData" style="width: 100%">
              <el-table-column prop="date" label="日期" width="150" />
              <el-table-column prop="method" label="支付方式" width="120" />
              <el-table-column prop="amount" label="支付金额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.amount.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="successRate" label="成功率" width="100">
                <template #default="scope">
                  <span class="percentage">{{ scope.row.successRate.toFixed(1) }}%</span>
                </template>
              </el-table-column>
              <el-table-column prop="avgAmount" label="平均金额" width="120">
                <template #default="scope">
                  ¥{{ scope.row.avgAmount.toFixed(2) }}
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
              :total="paymentData.length"
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
      timeRange: '30d',
      currentPage: 1,
      pageSize: 10,
      totalPayment: 12345678,
      totalOrders: 12345,
      averagePayment: 999.50,
      successRate: 98.5,
      paymentChange: 15.6,
      ordersChange: 12.3,
      avgPaymentChange: 2.8,
      successRateChange: 0.5,
      paymentData: [
        { date: '2026-04-01', method: '支付宝', amount: 456789, orders: 456, successRate: 99.2, avgAmount: 1001.73 },
        { date: '2026-04-01', method: '微信支付', amount: 321456, orders: 321, successRate: 98.8, avgAmount: 1001.42 },
        { date: '2026-04-01', method: '银行卡', amount: 123456, orders: 123, successRate: 97.5, avgAmount: 1003.71 },
        { date: '2026-04-02', method: '支付宝', amount: 489012, orders: 489, successRate: 99.0, avgAmount: 1000.02 },
        { date: '2026-04-02', method: '微信支付', amount: 345678, orders: 345, successRate: 98.9, avgAmount: 1001.97 },
        { date: '2026-04-02', method: '银行卡', amount: 134567, orders: 134, successRate: 97.8, avgAmount: 1004.23 },
        { date: '2026-04-03', method: '支付宝', amount: 512345, orders: 512, successRate: 99.1, avgAmount: 999.69 },
        { date: '2026-04-03', method: '微信支付', amount: 367890, orders: 367, successRate: 99.0, avgAmount: 1002.43 },
        { date: '2026-04-03', method: '银行卡', amount: 145678, orders: 145, successRate: 97.9, avgAmount: 1004.68 },
        { date: '2026-04-04', method: '支付宝', amount: 498765, orders: 498, successRate: 99.2, avgAmount: 999.53 }
      ],
      paymentMethodChart: null,
      paymentChannelChart: null,
      paymentTrendChart: null,
      paymentTimeChart: null,
      refreshTimer: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchData()
      this.initCharts()
      // 设置定时器，每60秒刷新一次数据
      this.refreshTimer = setInterval(() => {
        this.fetchData()
      }, 60000)
    })
  },
  beforeUnmount() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
      this.refreshTimer = null
    }
    if (this.paymentMethodChart) {
      this.paymentMethodChart.dispose()
      this.paymentMethodChart = null
    }
    if (this.paymentChannelChart) {
      this.paymentChannelChart.dispose()
      this.paymentChannelChart = null
    }
    if (this.paymentTrendChart) {
      this.paymentTrendChart.dispose()
      this.paymentTrendChart = null
    }
    if (this.paymentTimeChart) {
      this.paymentTimeChart.dispose()
      this.paymentTimeChart = null
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
    if (this.paymentMethodChart) {
      this.paymentMethodChart.dispose()
      this.paymentMethodChart = null
    }
    if (this.paymentChannelChart) {
      this.paymentChannelChart.dispose()
      this.paymentChannelChart = null
    }
    if (this.paymentTrendChart) {
      this.paymentTrendChart.dispose()
      this.paymentTrendChart = null
    }
    if (this.paymentTimeChart) {
      this.paymentTimeChart.dispose()
      this.paymentTimeChart = null
    }
  },
  methods: {
    initCharts() {
      this.initPaymentMethodChart()
      this.initPaymentChannelChart()
      this.initPaymentTrendChart()
      this.initPaymentTimeChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (this.paymentMethodChart) {
          this.paymentMethodChart.resize()
        }
        if (this.paymentChannelChart) {
          this.paymentChannelChart.resize()
        }
        if (this.paymentTrendChart) {
          this.paymentTrendChart.resize()
        }
        if (this.paymentTimeChart) {
          this.paymentTimeChart.resize()
        }
      })
    },
    initPaymentMethodChart() {
      const chartDom = document.getElementById('paymentMethodChart')
      this.paymentMethodChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          top: 'center'
        },
        series: [
          {
            name: '支付方式',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 55, name: '支付宝' },
              { value: 35, name: '微信支付' },
              { value: 8, name: '银行卡' },
              { value: 2, name: '其他' }
            ]
          }
        ]
      }
      
      this.paymentMethodChart.setOption(option)
    },
    initPaymentChannelChart() {
      const chartDom = document.getElementById('paymentChannelChart')
      this.paymentChannelChart = echarts.init(chartDom)
      
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
          data: ['移动端', 'PC端', '小程序', 'APP']
        },
        series: [
          {
            name: '支付金额',
            type: 'bar',
            data: [6789012, 3456789, 1234567, 864321],
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.paymentChannelChart.setOption(option)
    },
    initPaymentTrendChart() {
      const chartDom = document.getElementById('paymentTrendChart')
      this.paymentTrendChart = echarts.init(chartDom)
      
      const dates = this.paymentData.filter((item, index) => index % 3 === 0).map(item => item.date)
      const amounts = dates.map(date => {
        const dayData = this.paymentData.filter(item => item.date === date)
        return dayData.reduce((sum, item) => sum + item.amount, 0)
      })
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a5acd'
            }
          }
        },
        legend: {
          data: ['支付金额'],
          top: '5%',
          right: 'center'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: dates
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '支付金额',
            type: 'line',
            stack: 'Total',
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(106, 90, 205, 0.5)' },
                { offset: 1, color: 'rgba(106, 90, 205, 0.1)' }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            lineStyle: {
              color: '#6a5acd',
              width: 3
            },
            data: amounts
          }
        ]
      }
      
      this.paymentTrendChart.setOption(option)
    },
    initPaymentTimeChart() {
      const chartDom = document.getElementById('paymentTimeChart')
      this.paymentTimeChart = echarts.init(chartDom)
      
      const hours = Array.from({ length: 24 }, (_, i) => i + '时')
      const amounts = [
        123456, 89012, 67890, 56789, 45678, 67890, 123456, 234567, 345678, 456789, 567890, 678901,
        789012, 890123, 901234, 876543, 765432, 654321, 543210, 432109, 321098, 210987, 123456, 89012
      ]
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['支付金额'],
          top: '5%',
          right: 'center'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: hours,
          axisLabel: {
            interval: 2
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '支付金额',
            type: 'bar',
            data: amounts,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.paymentTimeChart.setOption(option)
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['日期', '支付方式', '支付金额', '订单数', '成功率', '平均金额']
      const rows = this.paymentData.map(item => [
        item.date,
        item.method,
        `¥${item.amount.toLocaleString()}`,
        item.orders,
        `${item.successRate.toFixed(1)}%`,
        `¥${item.avgAmount.toFixed(2)}`
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
        link.setAttribute('download', `支付分析数据_${new Date().toISOString().split('T')[0]}.csv`)
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
        // 获取支付分析数据
        const paymentResponse = await apiClient.get('/mock-data/payment-analysis')
        const paymentData = paymentResponse.data
        
        // 更新支付概览数据
        this.totalPayment = 12345678 + Math.floor(Math.random() * 1000000)
        this.totalOrders = 12345 + Math.floor(Math.random() * 1000)
        this.averagePayment = this.totalPayment / this.totalOrders
        this.successRate = 98.0 + Math.random() * 2.0
        this.paymentChange = 10.0 + Math.random() * 10.0
        this.ordersChange = 8.0 + Math.random() * 8.0
        this.avgPaymentChange = 0.5 + Math.random() * 5.0
        this.successRateChange = 0.1 + Math.random() * 1.0
        
        // 生成支付数据明细
        const methods = ['支付宝', '微信支付', '银行卡', '其他']
        this.paymentData = []
        for (let i = 1; i <= 4; i++) {
          methods.forEach(method => {
            this.paymentData.push({
              date: `2026-04-${String(i).padStart(2, '0')}`,
              method: method,
              amount: 100000 + Math.floor(Math.random() * 500000),
              orders: 100 + Math.floor(Math.random() * 500),
              successRate: 95.0 + Math.random() * 5.0,
              avgAmount: 900 + Math.random() * 200
            })
          })
        }
        
        // 更新图表
        this.initCharts()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    }
  },
  watch: {
    timeRange() {
      // 模拟数据更新
      this.initCharts()
    }
  }
}
</script>

<style scoped>
.payment-analysis {
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

.time-select {
  width: 120px;
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

.chart-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
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

.chart-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.chart-container {
  position: relative;
  height: 400px;
}

.chart-grid .chart-container {
  height: 350px;
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

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .overview-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .chart-grid {
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
  
  .time-select {
    width: 100%;
  }
  
  .chart-container {
    height: 300px;
  }
  
  .chart-grid .chart-container {
    height: 300px;
  }
  
  .detail-footer {
    justify-content: center;
  }
}
</style>
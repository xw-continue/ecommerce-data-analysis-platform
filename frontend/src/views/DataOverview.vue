<template>
  <div class="data-overview">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>数据概览</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/" class="breadcrumb">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>数据概览</el-breadcrumb-item>
      </el-breadcrumb>
      
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
              <div class="overview-change positive">+{{ salesChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              📋
            </div>
            <div class="overview-info">
              <div class="overview-title">总订单数</div>
              <div class="overview-value">{{ totalOrders.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ ordersChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              🏪
            </div>
            <div class="overview-info">
              <div class="overview-title">商品总数</div>
              <div class="overview-value">{{ totalProducts.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ productsChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              👥
            </div>
            <div class="overview-info">
              <div class="overview-title">用户总数</div>
              <div class="overview-value">{{ totalUsers.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ usersChange.toFixed(1) }}%</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 销售趋势 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>销售趋势</h3>
            <div class="chart-actions">
              <el-select v-model="timeRange" size="small" class="time-select">
                <el-option label="近7天" value="7d" />
                <el-option label="近30天" value="30d" />
                <el-option label="近90天" value="90d" />
                <el-option label="近1年" value="1y" />
              </el-select>
            </div>
          </div>
          <div class="chart-container">
            <div id="salesTrendChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 商品分类占比 -->
      <div class="chart-section">
        <div class="chart-grid">
          <div class="chart-card">
            <div class="chart-header">
              <h3>商品分类占比</h3>
            </div>
            <div class="chart-container">
              <div id="categoryChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>地区销售分布</h3>
            </div>
            <div class="chart-container">
              <div id="regionChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 热门商品 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>热门商品</h3>
            <div class="chart-actions">
              <el-button type="primary" size="small" @click="exportData" class="export-button">
                <el-icon><i-ep-download /></el-icon>
                导出数据
              </el-button>
            </div>
          </div>
          <div class="chart-container">
            <div id="hotProductsChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 销售数据表格 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>销售数据明细</h3>
          </div>
          <div class="detail-content">
            <el-table :data="salesData" style="width: 100%">
              <el-table-column prop="date" label="日期" width="150" />
              <el-table-column prop="sales" label="销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="products" label="商品数" width="100" />
              <el-table-column prop="avgOrder" label="客单价" width="100">
                <template #default="scope">
                  ¥{{ scope.row.avgOrder.toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column prop="change" label="环比">
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
      timeRange: '30d',
      currentPage: 1,
      pageSize: 10,
      totalSales: 12345678,
      totalOrders: 12345,
      totalProducts: 5678,
      totalUsers: 9876,
      salesChange: 15.6,
      ordersChange: 12.3,
      productsChange: 8.9,
      usersChange: 5.2,
      salesData: [],
      originalSalesData: [],
      salesTrendChart: null,
      categoryChart: null,
      regionChart: null,
      hotProductsChart: null,
      refreshTimer: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchData()
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
    if (this.salesTrendChart) {
      this.salesTrendChart.dispose()
      this.salesTrendChart = null
    }
    if (this.categoryChart) {
      this.categoryChart.dispose()
      this.categoryChart = null
    }
    if (this.regionChart) {
      this.regionChart.dispose()
      this.regionChart = null
    }
    if (this.hotProductsChart) {
      this.hotProductsChart.dispose()
      this.hotProductsChart = null
    }
  },
  activated() {
    // 当组件被激活时重新获取数据和初始化图表
    this.$nextTick(() => {
      this.fetchData()
    })
  },
  deactivated() {
    // 当组件被停用时销毁图表和清除定时器
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer)
      this.refreshTimer = null
    }
    if (this.salesTrendChart) {
      this.salesTrendChart.dispose()
      this.salesTrendChart = null
    }
    if (this.categoryChart) {
      this.categoryChart.dispose()
      this.categoryChart = null
    }
    if (this.regionChart) {
      this.regionChart.dispose()
      this.regionChart = null
    }
    if (this.hotProductsChart) {
      this.hotProductsChart.dispose()
      this.hotProductsChart = null
    }
  },
  methods: {
    initCharts() {
      this.initSalesTrendChart()
      this.initCategoryChart()
      this.initRegionChart()
      this.initHotProductsChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (this.salesTrendChart) {
          this.salesTrendChart.resize()
        }
        if (this.categoryChart) {
          this.categoryChart.resize()
        }
        if (this.regionChart) {
          this.regionChart.resize()
        }
        if (this.hotProductsChart) {
          this.hotProductsChart.resize()
        }
      })
    },
    initSalesTrendChart() {
      const chartDom = document.getElementById('salesTrendChart')
      this.salesTrendChart = echarts.init(chartDom)
      
      // 确保salesData有数据
      if (this.salesData.length === 0) {
        // 如果没有数据，先生成30天的数据
        this.generateSalesDataFor30Days()
      }
      
      const dates = this.salesData.map(item => item.date)
      const sales = this.salesData.map(item => item.sales)
      const orders = this.salesData.map(item => item.orders)
      const scaledOrders = orders.map(order => order * 250)
      
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
                result += params[i].marker + params[i].seriesName + ': ' + (params[i].value / 250) + '<br/>';
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
            type: 'line',
            data: sales,
            itemStyle: {
              color: '#6a5acd'
            },
            emphasis: {
              focus: 'series'
            },
            lineStyle: {
              color: '#6a5acd',
              width: 3
            }
          },
          {
            name: '订单数',
            type: 'line',
            yAxisIndex: 1,
            data: scaledOrders,
            itemStyle: {
              color: '#10b981'
            },
            emphasis: {
              focus: 'series'
            },
            lineStyle: {
              color: '#10b981',
              width: 3
            }
          }
        ]
      }
      
      this.salesTrendChart.setOption(option)
    },
    initCategoryChart() {
      const chartDom = document.getElementById('categoryChart')
      this.categoryChart = echarts.init(chartDom)
      
      // 使用从后端获取的商品分类数据
      const categoryData = this.categoryData ? this.categoryData.sales.map((value, index) => ({
        value: value,
        name: this.categoryData.categories[index]
      })) : [
        { value: 35, name: '电子产品' },
        { value: 25, name: '服装' },
        { value: 20, name: '家居用品' },
        { value: 10, name: '食品' },
        { value: 10, name: '其他' }
      ]
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: ¥{c.toLocaleString()} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          top: 'center'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: [
          {
            name: '商品分类',
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
            data: categoryData
          }
        ]
      }
      
      this.categoryChart.setOption(option)
    },
    initRegionChart() {
      const chartDom = document.getElementById('regionChart')
      this.regionChart = echarts.init(chartDom)
      
      // 使用从后端获取的地区销售数据
      const regions = this.regionData ? this.regionData.regions : ['华东', '华北', '华南', '西南', '西北']
      const sales = this.regionData ? this.regionData.sales : [4567890, 3214567, 2890123, 1567890, 987654]
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: function(params) {
            return params[0].name + '<br/>' + params[0].marker + '销售额: ¥' + params[0].value.toLocaleString();
          }
        },
        grid: {
          left: '5%',
          right: '4%',
          top: '5%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          axisLabel: {
            formatter: function(value) {
              return (value / 10000).toFixed(0) + '万';
            }
          }
        },
        yAxis: {
          type: 'category',
          data: regions
        },
        series: [
          {
            name: '销售额',
            type: 'bar',
            data: sales,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.regionChart.setOption(option)
    },
    initHotProductsChart() {
      const chartDom = document.getElementById('hotProductsChart')
      this.hotProductsChart = echarts.init(chartDom)
      
      // 使用从后端获取的热门商品数据
      const products = this.hotProductsData ? this.hotProductsData.products : ['智能手机 Pro Max', '无线蓝牙耳机', '运动休闲鞋', '智能手表', '纯棉T恤', '坚果礼盒', '智能音箱', '护肤套装', '厨房小家电', '儿童玩具']
      const sales = this.hotProductsData ? this.hotProductsData.sales : [123456, 112345, 109876, 98765, 87654, 76543, 65432, 54321, 43210, 32109]
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter: function(params) {
            return params[0].name + '<br/>' + params[0].marker + '销售额: ¥' + params[0].value.toLocaleString();
          }
        },
        grid: {
          left: '10%',
          right: '4%',
          top: '10%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01],
          axisLabel: {
            formatter: function(value) {
              return (value / 10000).toFixed(0) + '万';
            }
          }
        },
        yAxis: {
          type: 'category',
          data: products
        },
        series: [
          {
            name: '销售额',
            type: 'bar',
            data: sales,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.hotProductsChart.setOption(option)
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['日期', '销售额', '订单数', '商品数', '客单价', '环比']
      const rows = this.salesData.map(item => [
        item.date,
        `¥${item.sales.toLocaleString()}`,
        item.orders,
        item.products,
        `¥${item.avgOrder.toFixed(2)}`,
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
        link.setAttribute('download', `销售数据概览_${new Date().toISOString().split('T')[0]}.csv`)
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
    generateSalesDataFor30Days() {
      // 生成近30天的销售趋势数据
      const now = new Date()
      const newData = []
      
      for (let i = 29; i >= 0; i--) {
        const date = new Date(now)
        date.setDate(date.getDate() - i)
        const dateStr = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
        
        const sales = 400000 + Math.floor(Math.random() * 200000)
        const orders = 400 + Math.floor(Math.random() * 200)
        
        newData.push({
          date: dateStr,
          sales: sales,
          orders: orders,
          products: 1200 + Math.floor(Math.random() * 800),
          avgOrder: sales / orders,
          change: Math.random() * 20 - 5
        })
      }
      
      this.salesData = newData
      // 保存初始数据，以便切换回30天时恢复
      this.originalSalesData = JSON.parse(JSON.stringify(newData))
    },
    generateDataByTimeRange() {
      // 根据时间范围生成不同的数据
      const now = new Date()
      let days = 30 // 默认30天
      
      switch (this.timeRange) {
        case '7d':
          days = 7
          break
        case '30d':
          days = 30
          break
        case '90d':
          days = 90
          break
        case '1y':
          days = 365
          break
      }
      
      // 生成新的销售趋势数据
      const newData = []
      for (let i = days - 1; i >= 0; i--) {
        const date = new Date(now)
        date.setDate(date.getDate() - i)
        const dateStr = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
        
        const sales = 400000 + Math.floor(Math.random() * 200000)
        const orders = 400 + Math.floor(Math.random() * 200)
        
        newData.push({
          date: dateStr,
          sales: sales,
          orders: orders,
          products: 1200 + Math.floor(Math.random() * 800),
          avgOrder: sales / orders,
          change: Math.random() * 20 - 5
        })
      }
      
      this.salesData = newData
    },
    async fetchData() {
      try {
        // 获取概览数据
        const overviewResponse = await apiClient.get('/mock-data/overview')
        const overviewData = overviewResponse.data
        this.totalSales = overviewData.totalSales
        this.totalOrders = overviewData.totalOrders
        this.totalProducts = 5678 + Math.floor(Math.random() * 1000)
        this.totalUsers = overviewData.totalCustomers
        this.salesChange = overviewData.salesGrowth
        this.ordersChange = overviewData.orderGrowth
        this.productsChange = 8.9 + Math.random() * 5
        this.usersChange = 5.2 + Math.random() * 3

        // 生成近30天的销售趋势数据
        this.generateSalesDataFor30Days()

        // 获取地区销售数据
        const regionResponse = await apiClient.get('/mock-data/region-sales')
        this.regionData = regionResponse.data

        // 获取热门商品数据
        const hotProductsResponse = await apiClient.get('/mock-data/hot-products')
        this.hotProductsData = hotProductsResponse.data

        // 获取商品分类数据
        const categoryResponse = await apiClient.get('/mock-data/product-category')
        this.categoryData = categoryResponse.data

        // 更新图表
        this.initCharts()
      } catch (error) {
        console.error('Failed to fetch data:', error)
        // 即使API请求失败，也要初始化图表（使用默认数据）
        this.generateSalesDataFor30Days()
        this.initCharts()
      }
    }
  },
  watch: {
    timeRange() {
      // 根据时间范围生成不同的数据
      if (this.timeRange === '30d') {
        // 当选择近30天时，恢复保存的原始数据
        if (this.originalSalesData.length > 0) {
          this.salesData = JSON.parse(JSON.stringify(this.originalSalesData))
        } else {
          this.generateSalesDataFor30Days()
        }
      } else {
        // 其他时间范围使用常规方法
        this.generateDataByTimeRange()
      }
      this.initSalesTrendChart()
    }
  }
}
</script>

<style scoped>
.data-overview {
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

.breadcrumb {
  margin: 16px 0 24px 0;
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
  
  .chart-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .chart-actions {
    width: 100%;
    justify-content: space-between;
  }
}

@media screen and (max-width: 768px) {
  .overview-grid {
    grid-template-columns: 1fr;
  }
  
  .chart-container {
    height: 300px;
  }
  
  .chart-grid .chart-container {
    height: 300px;
  }
  
  .chart-actions {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .time-select {
    width: 100%;
  }
  
  .detail-footer {
    justify-content: center;
  }
}
</style>
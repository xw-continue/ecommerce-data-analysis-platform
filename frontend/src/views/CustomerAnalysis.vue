<template>
  <div class="customer-analysis">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>客户分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>客户分析</el-breadcrumb-item>
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
      
      <!-- 客户概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              👥
            </div>
            <div class="overview-info">
              <div class="overview-title">总客户数</div>
              <div class="overview-value">{{ totalCustomers.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ customersChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              ⭐
            </div>
            <div class="overview-info">
              <div class="overview-title">活跃客户数</div>
              <div class="overview-value">{{ activeCustomers.toLocaleString() }}</div>
              <div class="overview-change positive">+{{ activeChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              📋
            </div>
            <div class="overview-info">
              <div class="overview-title">平均订单数</div>
              <div class="overview-value">{{ averageOrders.toFixed(2) }}</div>
              <div class="overview-change positive">+{{ avgOrdersChange.toFixed(1) }}%</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💳
            </div>
            <div class="overview-info">
              <div class="overview-title">平均客单价</div>
              <div class="overview-value">¥{{ averageOrderValue.toFixed(2) }}</div>
              <div class="overview-change positive">+{{ avgOrderValueChange.toFixed(1) }}%</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 客户增长与消费分析 -->
      <div class="chart-section">
        <div class="chart-grid">
          <div class="chart-card">
            <div class="chart-header">
              <h3>客户增长趋势</h3>
            </div>
            <div class="chart-container">
              <div id="customerGrowthChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>客户消费分布</h3>
            </div>
            <div class="chart-container">
              <div id="customerConsumptionChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 客户地域分布与年龄分析 -->
      <div class="chart-section">
        <div class="chart-grid">
          <div class="chart-card">
            <div class="chart-header">
              <h3>客户地域分布</h3>
            </div>
            <div class="chart-container">
              <div id="customerRegionChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>客户年龄分布</h3>
            </div>
            <div class="chart-container">
              <div id="customerAgeChart" style="width: 100%; height: 350px;"></div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 客户购买行为分析 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>客户购买行为分析</h3>
          </div>
          <div class="chart-container">
            <div id="customerBehaviorChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 客户数据明细 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>客户数据明细</h3>
          </div>
          <div class="detail-content">
            <el-table :data="customerData" style="width: 100%">
              <el-table-column prop="id" label="客户ID" width="100" />
              <el-table-column prop="name" label="客户名称" width="150" />
              <el-table-column prop="region" label="地区" width="120" />
              <el-table-column prop="age" label="年龄" width="80" />
              <el-table-column prop="gender" label="性别" width="80" />
              <el-table-column prop="orders" label="订单数" width="100" />
              <el-table-column prop="totalAmount" label="总消费" width="150">
                <template #default="scope">
                  ¥{{ scope.row.totalAmount.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="avgAmount" label="客单价" width="120">
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
              :total="customerData.length"
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
      totalCustomers: 123456,
      activeCustomers: 87654,
      averageOrders: 5.23,
      averageOrderValue: 899.50,
      customersChange: 8.7,
      activeChange: 12.3,
      avgOrdersChange: 1.5,
      avgOrderValueChange: 3.2,
      customerData: [
        { id: 1, name: '张三', region: '北京', age: 25, gender: '男', orders: 12, totalAmount: 12345, avgAmount: 1028.75 },
        { id: 2, name: '李四', region: '上海', age: 32, gender: '女', orders: 8, totalAmount: 9876, avgAmount: 1234.50 },
        { id: 3, name: '王五', region: '广州', age: 28, gender: '男', orders: 15, totalAmount: 15678, avgAmount: 1045.20 },
        { id: 4, name: '赵六', region: '深圳', age: 35, gender: '女', orders: 6, totalAmount: 7890, avgAmount: 1315.00 },
        { id: 5, name: '钱七', region: '成都', age: 22, gender: '男', orders: 10, totalAmount: 8990, avgAmount: 899.00 },
        { id: 6, name: '孙八', region: '杭州', age: 30, gender: '女', orders: 13, totalAmount: 11234, avgAmount: 864.15 },
        { id: 7, name: '周九', region: '武汉', age: 27, gender: '男', orders: 9, totalAmount: 8765, avgAmount: 973.89 },
        { id: 8, name: '吴十', region: '西安', age: 33, gender: '女', orders: 7, totalAmount: 6543, avgAmount: 934.71 },
        { id: 9, name: '郑十一', region: '南京', age: 29, gender: '男', orders: 11, totalAmount: 10987, avgAmount: 998.82 },
        { id: 10, name: '王十二', region: '重庆', age: 26, gender: '女', orders: 14, totalAmount: 13456, avgAmount: 961.14 }
      ],
      customerGrowthChart: null,
      customerConsumptionChart: null,
      customerRegionChart: null,
      customerAgeChart: null,
      customerBehaviorChart: null,
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
    if (this.customerGrowthChart) {
      this.customerGrowthChart.dispose()
      this.customerGrowthChart = null
    }
    if (this.customerConsumptionChart) {
      this.customerConsumptionChart.dispose()
      this.customerConsumptionChart = null
    }
    if (this.customerRegionChart) {
      this.customerRegionChart.dispose()
      this.customerRegionChart = null
    }
    if (this.customerAgeChart) {
      this.customerAgeChart.dispose()
      this.customerAgeChart = null
    }
    if (this.customerBehaviorChart) {
      this.customerBehaviorChart.dispose()
      this.customerBehaviorChart = null
    }
  },
  activated() {
    // 当组件被激活时重新初始化图表
    this.$nextTick(() => {
      // 先确保图表容器存在且图表未初始化
      if (document.getElementById('customerGrowthChart') && !this.customerGrowthChart) {
        this.fetchData()
        this.initCharts()
      }
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
    if (this.customerGrowthChart) {
      this.customerGrowthChart.dispose()
      this.customerGrowthChart = null
    }
    if (this.customerConsumptionChart) {
      this.customerConsumptionChart.dispose()
      this.customerConsumptionChart = null
    }
    if (this.customerRegionChart) {
      this.customerRegionChart.dispose()
      this.customerRegionChart = null
    }
    if (this.customerAgeChart) {
      this.customerAgeChart.dispose()
      this.customerAgeChart = null
    }
    if (this.customerBehaviorChart) {
      this.customerBehaviorChart.dispose()
      this.customerBehaviorChart = null
    }
  },
  methods: {
    initCharts() {
      this.initCustomerGrowthChart()
      this.initCustomerConsumptionChart()
      this.initCustomerRegionChart()
      this.initCustomerAgeChart()
      this.initCustomerBehaviorChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (this.customerGrowthChart) {
          this.customerGrowthChart.resize()
        }
        if (this.customerConsumptionChart) {
          this.customerConsumptionChart.resize()
        }
        if (this.customerRegionChart) {
          this.customerRegionChart.resize()
        }
        if (this.customerAgeChart) {
          this.customerAgeChart.resize()
        }
        if (this.customerBehaviorChart) {
          this.customerBehaviorChart.resize()
        }
      })
    },
    initCustomerGrowthChart() {
      const chartDom = document.getElementById('customerGrowthChart')
      this.customerGrowthChart = echarts.init(chartDom)
      
      const dates = ['2026-01', '2026-02', '2026-03', '2026-04', '2026-05', '2026-06']
      const newCustomers = [1234, 1567, 1890, 2134, 2456, 2789]
      const totalCustomers = [10000, 11567, 13457, 15591, 18047, 20836]
      
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
          data: ['新增客户', '总客户数'],
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
            name: '新增客户',
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
            data: newCustomers
          },
          {
            name: '总客户数',
            type: 'line',
            stack: 'Total',
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(56, 182, 255, 0.5)' },
                { offset: 1, color: 'rgba(56, 182, 255, 0.1)' }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            lineStyle: {
              color: '#38b6ff',
              width: 3
            },
            data: totalCustomers
          }
        ]
      }
      
      this.customerGrowthChart.setOption(option)
    },
    initCustomerConsumptionChart() {
      const chartDom = document.getElementById('customerConsumptionChart')
      this.customerConsumptionChart = echarts.init(chartDom)
      
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
            name: '消费金额',
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
              { value: 35, name: '0-500元' },
              { value: 25, name: '501-1000元' },
              { value: 20, name: '1001-2000元' },
              { value: 15, name: '2001-5000元' },
              { value: 5, name: '5000元以上' }
            ]
          }
        ]
      }
      
      this.customerConsumptionChart.setOption(option)
    },
    initCustomerRegionChart() {
      const chartDom = document.getElementById('customerRegionChart')
      this.customerRegionChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['客户数'],
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
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: ['北京', '上海', '广州', '深圳', '成都', '杭州', '武汉', '西安', '南京', '重庆']
        },
        series: [
          {
            name: '客户数',
            type: 'bar',
            data: [15678, 14567, 12345, 11234, 9876, 8765, 7654, 6543, 5432, 4321],
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.customerRegionChart.setOption(option)
    },
    initCustomerAgeChart() {
      const chartDom = document.getElementById('customerAgeChart')
      this.customerAgeChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['客户数'],
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
          data: ['18-25岁', '26-30岁', '31-35岁', '36-40岁', '41-45岁', '46-50岁', '50岁以上']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '客户数',
            type: 'bar',
            data: [25678, 34567, 28901, 18765, 12345, 7654, 5432],
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.customerAgeChart.setOption(option)
    },
    initCustomerBehaviorChart() {
      const chartDom = document.getElementById('customerBehaviorChart')
      this.customerBehaviorChart = echarts.init(chartDom)
      
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
          data: ['购买频率', '平均客单价', '复购率'],
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
          data: ['1月', '2月', '3月', '4月', '5月', '6月']
        },
        yAxis: [
          {
            type: 'value',
            name: '购买频率',
            position: 'left'
          },
          {
            type: 'value',
            name: '客单价/复购率',
            position: 'right'
          }
        ],
        series: [
          {
            name: '购买频率',
            type: 'line',
            yAxisIndex: 0,
            lineStyle: {
              color: '#6a5acd',
              width: 3
            },
            data: [4.2, 4.5, 4.8, 5.2, 5.5, 5.8]
          },
          {
            name: '平均客单价',
            type: 'line',
            yAxisIndex: 1,
            lineStyle: {
              color: '#38b6ff',
              width: 3
            },
            data: [850, 875, 900, 925, 950, 975]
          },
          {
            name: '复购率',
            type: 'line',
            yAxisIndex: 1,
            lineStyle: {
              color: '#10b981',
              width: 3
            },
            data: [0.35, 0.38, 0.42, 0.45, 0.48, 0.52]
          }
        ]
      }
      
      this.customerBehaviorChart.setOption(option)
    },
    fetchData() {
      // 从后端API获取客户分析数据
      apiClient.get('/mock-data/customer-analysis')
        .then(response => {
          const data = response.data
          
          // 更新客户概览数据
          if (data.totalCustomers) {
            this.totalCustomers = Array.isArray(data.totalCustomers) ? data.totalCustomers[0] : data.totalCustomers
          }
          if (data.activeCustomers) {
            this.activeCustomers = Array.isArray(data.activeCustomers) ? data.activeCustomers[0] : data.activeCustomers
          }
          if (data.averageOrders) {
            this.averageOrders = Array.isArray(data.averageOrders) ? data.averageOrders[0] : data.averageOrders
          }
          if (data.averageOrderValue) {
            this.averageOrderValue = Array.isArray(data.averageOrderValue) ? data.averageOrderValue[0] : data.averageOrderValue
          }
          if (data.customersChange) {
            this.customersChange = Array.isArray(data.customersChange) ? data.customersChange[0] : data.customersChange
          }
          if (data.activeChange) {
            this.activeChange = Array.isArray(data.activeChange) ? data.activeChange[0] : data.activeChange
          }
          if (data.avgOrdersChange) {
            this.avgOrdersChange = Array.isArray(data.avgOrdersChange) ? data.avgOrdersChange[0] : data.avgOrdersChange
          }
          if (data.avgOrderValueChange) {
            this.avgOrderValueChange = Array.isArray(data.avgOrderValueChange) ? data.avgOrderValueChange[0] : data.avgOrderValueChange
          }
          
          // 更新客户数据明细
          if (data.customerData) {
            this.customerData = data.customerData
          }
          
          // 重新初始化图表
          this.initCharts()
        })
        .catch(error => {
          console.error('获取客户分析数据失败:', error)
          // 使用模拟数据作为后备
          this.totalCustomers = 123456
          this.activeCustomers = 87654
          this.averageOrders = 5.23
          this.averageOrderValue = 899.50
          this.customersChange = 8.7
          this.activeChange = 12.3
          this.avgOrdersChange = 1.5
          this.avgOrderValueChange = 3.2
          // 确保图表能正常初始化
          this.initCharts()
        })
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['客户ID', '客户名称', '地区', '年龄', '性别', '订单数', '总消费', '客单价']
      const rows = this.customerData.map(item => [
        item.id,
        item.name,
        item.region,
        item.age,
        item.gender,
        item.orders,
        `¥${item.totalAmount.toLocaleString()}`,
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
        link.setAttribute('download', `客户分析数据_${new Date().toISOString().split('T')[0]}.csv`)
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
.customer-analysis {
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
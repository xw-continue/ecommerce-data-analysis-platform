<template>
  <div class="region-analysis">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>地区销售分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>地区分析</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-select v-model="regionLevel" size="small" class="region-select">
            <el-option label="全国" value="country" />
            <el-option label="省份" value="province" />
            <el-option label="城市" value="city" />
          </el-select>
        </div>
        <div class="filter-right">
          <el-button type="primary" size="small" @click="exportData" class="export-button">
            <el-icon><i-ep-download /></el-icon>
            导出数据
          </el-button>
        </div>
      </div>
      
      <!-- 地区概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              🌍
            </div>
            <div class="overview-info">
              <div class="overview-title">覆盖地区数</div>
              <div class="overview-value">{{ regionCount }}</div>
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
              <div class="overview-title">最高销售额地区</div>
              <div class="overview-value">{{ topRegion }}</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 地区销售地图 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>地区销售分布</h3>
          </div>
          <div class="chart-container">
            <div id="regionMapChart" style="width: 100%; height: 500px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 地区销售排行 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>地区销售排行</h3>
          </div>
          <div class="chart-container">
            <div id="regionRankChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 地区详情 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>地区详情</h3>
          </div>
          <div class="detail-content">
            <el-table :data="regionData" style="width: 100%">
              <el-table-column prop="region" label="地区名称" width="150" />
              <el-table-column prop="sales" label="销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
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
              :total="regionData.length"
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
      regionLevel: 'province',
      currentPage: 1,
      pageSize: 10,
      regionCount: 31,
      totalSales: 1234567,
      averageSales: 39824.74,
      topRegion: '广东省',
      regionData: [
        { region: '广东省', sales: 198765, orders: 2345, percentage: 16.1, change: 18.5 },
        { region: '浙江省', sales: 156789, orders: 1890, percentage: 12.7, change: 15.2 },
        { region: '江苏省', sales: 145678, orders: 1789, percentage: 11.8, change: 12.8 },
        { region: '上海市', sales: 134567, orders: 1678, percentage: 10.9, change: 10.5 },
        { region: '北京市', sales: 123456, orders: 1567, percentage: 9.9, change: 9.8 },
        { region: '四川省', sales: 87654, orders: 1098, percentage: 7.1, change: 8.3 },
        { region: '山东省', sales: 76543, orders: 987, percentage: 6.2, change: 7.5 },
        { region: '湖北省', sales: 65432, orders: 876, percentage: 5.3, change: 6.9 },
        { region: '河南省', sales: 54321, orders: 765, percentage: 4.4, change: 5.7 },
        { region: '福建省', sales: 43210, orders: 654, percentage: 3.5, change: 4.8 }
      ],
      mapChart: null,
      rankChart: null,
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
    if (this.mapChart) {
      this.mapChart.dispose()
      this.mapChart = null
    }
    if (this.rankChart) {
      this.rankChart.dispose()
      this.rankChart = null
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
    if (this.mapChart) {
      this.mapChart.dispose()
      this.mapChart = null
    }
    if (this.rankChart) {
      this.rankChart.dispose()
      this.rankChart = null
    }
  },
  methods: {
    initCharts() {
      this.initMapChart()
      this.initRankChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        if (this.mapChart) {
          this.mapChart.resize()
        }
        if (this.rankChart) {
          this.rankChart.resize()
        }
      })
    },
    initMapChart() {
      const chartDom = document.getElementById('regionMapChart')
      this.mapChart = echarts.init(chartDom)
      this.updateMapChart()
    },
    initRankChart() {
      const chartDom = document.getElementById('regionRankChart')
      this.rankChart = echarts.init(chartDom)
      this.updateRankChart()
    },
    updateMapChart() {
      // 模拟地图数据
      const mapData = this.regionData.map(item => ({
        name: item.region,
        value: item.sales
      }))
      
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
          bottom: '20%',
          containLabel: true
        },
        series: [
          {
            name: '销售额',
            type: 'bar',
            data: mapData.map(item => ({
              name: item.name,
              value: item.value
            })),
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ],
        xAxis: {
          type: 'category',
          data: mapData.map(item => item.name),
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        yAxis: {
          type: 'value'
        }
      }
      
      this.mapChart.setOption(option)
    },
    updateRankChart() {
      // 按销售额排序，取前5个地区
      const sortedRegionData = [...this.regionData].sort((a, b) => b.sales - a.sales).slice(0, 5)
      const regions = sortedRegionData.map(item => item.region)
      const sales = sortedRegionData.map(item => item.sales)
      
      // 计算其他地区的销售额
      const otherSales = this.regionData.slice(5).reduce((sum, item) => sum + item.sales, 0)
      regions.push('其他')
      sales.push(otherSales)
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}: ¥{c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: regions
        },
        series: [
          {
            name: '销售占比',
            type: 'pie',
            radius: '60%',
            center: ['50%', '50%'],
            data: regions.map((region, index) => ({
              name: region,
              value: sales[index]
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
      
      this.rankChart.setOption(option)
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['地区名称', '销售额', '订单数', '占比', '环比']
      const rows = this.regionData.map(item => [
        item.region,
        `¥${item.sales.toLocaleString()}`,
        item.orders,
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
        link.setAttribute('download', `地区销售数据_${new Date().toISOString().split('T')[0]}.csv`)
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
    generateDataByRegionLevel() {
      // 根据地区级别生成不同的数据
      let regions = []
      let sales = []
      
      switch (this.regionLevel) {
        case 'country':
          // 全国数据
          regions = ['华东', '华北', '华南', '西南', '西北', '东北', '华中']
          sales = [19876543, 15678901, 12345678, 8765432, 6543210, 5432109, 4321098]
          break
        case 'province':
          // 省份数据
          regions = ['广东省', '浙江省', '江苏省', '上海市', '北京市', '四川省', '山东省', '湖北省', '河南省', '福建省']
          sales = [198765, 156789, 145678, 134567, 123456, 87654, 76543, 65432, 54321, 43210]
          break
        case 'city':
          // 城市数据
          regions = ['北京', '上海', '广州', '深圳', '杭州', '成都', '武汉', '西安', '南京', '重庆', '天津', '苏州', '郑州', '长沙', '青岛']
          sales = [98765, 87654, 76543, 65432, 54321, 43210, 32109, 21098, 19876, 18765, 17654, 16543, 15432, 14321, 13210]
          break
      }
      
      // 计算总销售额
      this.totalSales = sales.reduce((sum, value) => sum + value, 0)
      
      // 计算平均销售额
      this.averageSales = this.totalSales / regions.length
      
      // 找到最高销售额地区
      const maxSalesIndex = sales.indexOf(Math.max(...sales))
      this.topRegion = regions[maxSalesIndex]
      
      // 更新地区数量
      this.regionCount = regions.length
      
      // 生成地区详情数据
      this.regionData = regions.map((region, index) => ({
        region: region,
        sales: sales[index],
        orders: Math.floor(sales[index] / 100),
        percentage: (sales[index] / this.totalSales) * 100,
        change: Math.random() * 20 - 5
      }))
    },
    async fetchData() {
      try {
        // 获取地区销售数据
        const regionResponse = await apiClient.get('/mock-data/region-sales')
        const regionData = regionResponse.data
        
        // 计算总销售额
        this.totalSales = regionData.sales.reduce((sum, value) => sum + value, 0)
        
        // 计算平均销售额
        this.averageSales = this.totalSales / regionData.regions.length
        
        // 找到最高销售额地区
        const maxSalesIndex = regionData.sales.indexOf(Math.max(...regionData.sales))
        this.topRegion = regionData.regions[maxSalesIndex]
        
        // 生成地区详情数据
        this.regionData = regionData.regions.map((region, index) => ({
          region: region,
          sales: regionData.sales[index],
          orders: Math.floor(regionData.sales[index] / 100),
          percentage: (regionData.sales[index] / this.totalSales) * 100,
          change: Math.random() * 20 - 5
        }))
        
        // 更新图表
        this.updateMapChart()
        this.updateRankChart()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    }
  },
  watch: {
    regionLevel() {
      // 根据地区级别生成不同的数据
      this.generateDataByRegionLevel()
      this.updateMapChart()
      this.updateRankChart()
    }
  }
}
</script>

<style scoped>
.region-analysis {
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

.region-select {
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

.chart-container#regionMapChart {
  height: 500px;
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
  
  .filter-right {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .chart-container {
    height: 300px;
  }
  
  .chart-container#regionMapChart {
    height: 400px;
  }
  
  .detail-footer {
    justify-content: center;
  }
}
</style>

<template>
  <div class="sales-prediction">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>销售预测分析</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>销售分析</el-breadcrumb-item>
        <el-breadcrumb-item>销售预测</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-select v-model="predictionPeriod" size="small" class="period-select">
            <el-option label="预测7天" value="7" />
            <el-option label="预测30天" value="30" />
            <el-option label="预测90天" value="90" />
          </el-select>
        </div>
        <div class="filter-right">
          <el-button type="primary" size="small" @click="runPrediction" class="predict-button">
            <el-icon><i-ep-refresh /></el-icon>
            运行预测
          </el-button>
          <el-button type="primary" size="small" @click="exportData" class="export-button">
            <el-icon><i-ep-download /></el-icon>
            导出数据
          </el-button>
        </div>
      </div>
      
      <!-- 预测概览 -->
      <div class="overview-section">
        <div class="overview-grid">
          <div class="overview-card">
            <div class="overview-icon">
              💰
            </div>
            <div class="overview-info">
              <div class="overview-title">预测总销售额</div>
              <div class="overview-value">¥{{ predictedTotalSales.toLocaleString() }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              💳
            </div>
            <div class="overview-info">
              <div class="overview-title">平均日销售额</div>
              <div class="overview-value">¥{{ averageDailySales.toFixed(2) }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              🏆
            </div>
            <div class="overview-info">
              <div class="overview-title">预计最高日销售额</div>
              <div class="overview-value">¥{{ highestDailySales.toLocaleString() }}</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="overview-icon">
              ⏰
            </div>
            <div class="overview-info">
              <div class="overview-title">预测周期</div>
              <div class="overview-value">{{ predictionPeriod }}天</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 销售预测图表 -->
      <div class="chart-section">
        <div class="chart-card">
          <div class="chart-header">
            <h3>销售预测趋势</h3>
          </div>
          <div class="chart-container">
            <div id="predictionChart" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
      
      <!-- 预测详情 -->
      <div class="detail-section">
        <div class="detail-card">
          <div class="detail-header">
            <h3>预测详情</h3>
          </div>
          <div class="detail-content">
            <el-table :data="predictionData" style="width: 100%">
              <el-table-column prop="date" label="日期" width="120" />
              <el-table-column prop="sales" label="预测销售额" width="150">
                <template #default="scope">
                  ¥{{ scope.row.sales.toLocaleString() }}
                </template>
              </el-table-column>
              <el-table-column prop="trend" label="趋势" width="100">
                <template #default="scope">
                  <span :class="[scope.row.trend >= 0 ? 'positive' : 'negative', 'percentage']">
                    {{ scope.row.trend >= 0 ? '+' : '' }}{{ scope.row.trend.toFixed(1) }}%
                  </span>
                </template>
              </el-table-column>
              <el-table-column prop="confidence" label="置信度" width="100">
                <template #default="scope">
                  <span class="percentage">{{ scope.row.confidence.toFixed(1) }}%</span>
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
              :total="predictionData.length"
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
      predictionPeriod: '30',
      currentPage: 1,
      pageSize: 10,
      predictedTotalSales: 1567890,
      averageDailySales: 52263.00,
      highestDailySales: 89765,
      predictionData: [
        { date: '2026-01-01', sales: 56789, trend: 12.5, confidence: 95.2 },
        { date: '2026-01-02', sales: 58901, trend: 13.2, confidence: 94.8 },
        { date: '2026-01-03', sales: 61234, trend: 14.1, confidence: 94.5 },
        { date: '2026-01-04', sales: 63456, trend: 14.8, confidence: 94.2 },
        { date: '2026-01-05', sales: 65678, trend: 15.5, confidence: 93.9 },
        { date: '2026-01-06', sales: 67890, trend: 16.2, confidence: 93.6 },
        { date: '2026-01-07', sales: 70123, trend: 16.9, confidence: 93.3 },
        { date: '2026-01-08', sales: 72345, trend: 17.6, confidence: 93.0 },
        { date: '2026-01-09', sales: 74567, trend: 18.3, confidence: 92.7 },
        { date: '2026-01-10', sales: 76789, trend: 19.0, confidence: 92.4 }
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
      const chartDom = document.getElementById('predictionChart')
      this.chart = echarts.init(chartDom)
      this.updateChart()
      
      // 添加窗口大小变化监听
      window.addEventListener('resize', () => {
        this.chart.resize()
      })
    },
    updateChart() {
      const dates = this.predictionData.map(item => item.date)
      const sales = this.predictionData.map(item => item.sales)
      
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['预测销售额', '实际销售额'],
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
            name: '预测销售额',
            type: 'line',
            stack: 'Total',
            data: sales,
            itemStyle: {
              color: '#6a5acd'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(106, 90, 205, 0.5)' },
                { offset: 1, color: 'rgba(106, 90, 205, 0.1)' }
              ])
            }
          },
          {
            name: '实际销售额',
            type: 'line',
            stack: 'Total',
            data: [50000, 52000, 54000, 56000, 58000, 60000, 62000, 64000, 66000, 68000],
            itemStyle: {
              color: '#10b981'
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(16, 185, 129, 0.5)' },
                { offset: 1, color: 'rgba(16, 185, 129, 0.1)' }
              ])
            }
          }
        ]
      }
      
      this.chart.setOption(option)
    },
    runPrediction() {
      this.$message.success('预测计算完成')
      // 模拟预测数据更新
      this.updateChart()
    },
    exportData() {
      // 实现真正的数据导出功能
      this.exportToCSV()
    },
    exportToCSV() {
      // 准备CSV数据
      const headers = ['日期', '预测销售额', '趋势', '置信度']
      const rows = this.predictionData.map(item => [
        item.date,
        `¥${item.sales.toLocaleString()}`,
        `${item.trend >= 0 ? '+' : ''}${item.trend.toFixed(1)}%`,
        `${item.confidence.toFixed(1)}%`
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
        link.setAttribute('download', `销售预测数据_${new Date().toISOString().split('T')[0]}.csv`)
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
        // 获取销售预测数据
        const predictionResponse = await apiClient.get('/mock-data/sales-prediction')
        const predictionData = predictionResponse.data
        
        // 计算预测总销售额
        this.predictedTotalSales = predictionData.predicted.reduce((sum, value) => sum + value, 0)
        
        // 计算平均日销售额
        this.averageDailySales = this.predictedTotalSales / predictionData.dates.length
        
        // 找到最高日销售额
        this.highestDailySales = Math.max(...predictionData.predicted)
        
        // 生成预测详情数据
        this.predictionData = predictionData.dates.map((date, index) => ({
          date: `2026-04-${String(index + 1).padStart(2, '0')}`,
          sales: predictionData.predicted[index],
          trend: Math.random() * 20 - 2,
          confidence: 90 + Math.random() * 10
        }))
        
        // 更新图表
        this.updateChart()
      } catch (error) {
        console.error('Failed to fetch data:', error)
      }
    }
  },
  watch: {
    predictionPeriod() {
      // 模拟数据更新
      this.updateChart()
    }
  }
}
</script>

<style scoped>
.sales-prediction {
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

.period-select {
  width: 120px;
}

.predict-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.predict-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
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
  color: var(--accent-color);
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

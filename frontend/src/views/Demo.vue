<template>
  <div class="demo">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>数据可视化演示</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>数据可视化</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 演示内容 -->
      <div class="content-section">
        <div class="content-card">
          <h2>销售数据可视化演示</h2>
          <p class="intro-text">
            以下是本系统的数据可视化功能演示，登录后可查看完整的数据分析功能。
          </p>
          
          <!-- 销售趋势演示 -->
          <div class="chart-section">
            <h3>销售趋势演示</h3>
            <div class="chart-card">
              <div id="salesDemoChart" style="width: 100%; height: 400px;"></div>
            </div>
          </div>
          
          <!-- 商品分类演示 -->
          <div class="chart-section">
            <h3>商品分类演示</h3>
            <div class="chart-row">
              <div class="chart-card">
                <div id="categoryDemoChart" style="width: 100%; height: 300px;"></div>
              </div>
              <div class="chart-card">
                <div id="categoryPieChart" style="width: 100%; height: 300px;"></div>
              </div>
            </div>
          </div>
          
          <!-- 登录提示 -->
          <div class="login-prompt">
            <el-card shadow="hover" class="prompt-card">
              <div class="prompt-content">
                <h3>查看完整功能</h3>
                <p>登录后可访问完整的数据分析功能，包括实时数据、多维度分析和智能预测。</p>
                <el-button type="primary" size="large" @click="goToLogin" class="login-button">
                  <el-icon><i-ep-unlock /></el-icon>
                  立即登录
                </el-button>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'Demo',
  data() {
    return {
      salesChart: null,
      categoryChart: null,
      pieChart: null
    }
  },
  mounted() {
    this.initCharts()
  },
  beforeUnmount() {
    if (this.salesChart) {
      this.salesChart.dispose()
    }
    if (this.categoryChart) {
      this.categoryChart.dispose()
    }
    if (this.pieChart) {
      this.pieChart.dispose()
    }
  },
  methods: {
    initCharts() {
      this.initSalesChart()
      this.initCategoryChart()
      this.initPieChart()
    },
    initSalesChart() {
      const chartDom = document.getElementById('salesDemoChart')
      this.salesChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['销售额', '订单数']
        },
        xAxis: {
          type: 'category',
          data: ['1月', '2月', '3月', '4月', '5月', '6月']
        },
        yAxis: [
          {
            type: 'value',
            name: '销售额',
            axisLabel: {
              formatter: '¥{value}'
            }
          },
          {
            type: 'value',
            name: '订单数'
          }
        ],
        series: [
          {
            name: '销售额',
            type: 'line',
            data: [120000, 190000, 300000, 420000, 550000, 680000],
            itemStyle: {
              color: '#6a5acd'
            }
          },
          {
            name: '订单数',
            type: 'line',
            yAxisIndex: 1,
            data: [1200, 1900, 3000, 4200, 5500, 6800],
            itemStyle: {
              color: '#10b981'
            }
          }
        ]
      }
      
      this.salesChart.setOption(option)
    },
    initCategoryChart() {
      const chartDom = document.getElementById('categoryDemoChart')
      this.categoryChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: ['电子产品', '服装', '食品', '家居用品', '化妆品']
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '¥{value}'
          }
        },
        series: [
          {
            name: '销售额',
            type: 'bar',
            data: [456789, 321456, 189765, 123456, 87654],
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#6a5acd' },
                { offset: 1, color: '#9333ea' }
              ])
            }
          }
        ]
      }
      
      this.categoryChart.setOption(option)
    },
    initPieChart() {
      const chartDom = document.getElementById('categoryPieChart')
      this.pieChart = echarts.init(chartDom)
      
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '销售额',
            type: 'pie',
            radius: '60%',
            data: [
              { value: 456789, name: '电子产品' },
              { value: 321456, name: '服装' },
              { value: 189765, name: '食品' },
              { value: 123456, name: '家居用品' },
              { value: 87654, name: '化妆品' }
            ],
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
      
      this.pieChart.setOption(option)
    },
    goToLogin() {
      // 触发App组件的登录弹窗
      this.$eventBus.emit('openLoginDialog')
    }
  }
}
</script>

<style scoped>
.demo {
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

.content-section {
  margin-top: 24px;
}

.content-card {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 32px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.content-card h2 {
  font-size: 28px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 24px;
  text-align: center;
}

.intro-text {
  font-size: 16px;
  line-height: 1.8;
  color: var(--text-secondary);
  margin-bottom: 40px;
  text-align: center;
  max-width: 800px;
  margin-left: auto;
  margin-right: auto;
}

.chart-section {
  margin-bottom: 40px;
}

.chart-section h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.chart-card {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 24px;
  border: 1px solid var(--border-light);
  box-shadow: var(--shadow-sm);
}

.chart-row {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}

.login-prompt {
  margin-top: 40px;
}

.prompt-card {
  border-radius: 8px;
  overflow: hidden;
}

.prompt-content {
  text-align: center;
  padding: 32px;
}

.prompt-content h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.prompt-content p {
  font-size: 14px;
  line-height: 1.6;
  color: var(--text-secondary);
  margin-bottom: 24px;
}

.login-button {
  border-radius: 6px;
  padding: 10px 24px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.login-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .chart-row {
    grid-template-columns: 1fr;
  }
}

@media screen and (max-width: 768px) {
  .content-card {
    padding: 24px;
  }
  
  .content-card h2 {
    font-size: 24px;
  }
  
  .intro-text {
    font-size: 14px;
  }
  
  .chart-card {
    padding: 16px;
  }
  
  .prompt-content {
    padding: 24px;
  }
}
</style>

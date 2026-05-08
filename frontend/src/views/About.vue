<template>
  <div class="about">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>项目介绍</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>项目介绍</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 项目介绍内容 -->
      <div class="content-section">
        <div class="content-card">
          <h2>电商销售数据分析平台</h2>
          <p class="intro-text">
            本平台是一个基于SpringBoot+Python技术栈开发的电商销售数据分析系统，旨在为电商企业提供全方位的销售数据分析解决方案。
            通过实时监控销售动态，智能预测市场趋势，帮助企业做出数据驱动的决策，助力业务增长。
          </p>
          
          <div class="feature-grid">
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-trend-charts /></el-icon>
              </div>
              <h3>销售趋势分析</h3>
              <p>实时监控销售数据，分析销售趋势，帮助企业了解业务发展动态。</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-grid /></el-icon>
              </div>
              <h3>商品分类分析</h3>
              <p>深入分析不同商品分类的销售情况，优化产品结构。</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-position /></el-icon>
              </div>
              <h3>地区销售分析</h3>
              <p>分析不同地区的销售分布，制定针对性的营销策略。</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-fire /></el-icon>
              </div>
              <h3>热门商品分析</h3>
              <p>识别热销商品，了解消费者偏好，优化产品推荐。</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-sunny /></el-icon>
              </div>
              <h3>销售预测</h3>
              <p>基于历史数据，智能预测未来销售趋势，提前制定策略。</p>
            </div>
            <div class="feature-card">
              <div class="feature-icon">
                <el-icon><i-ep-setting /></el-icon>
              </div>
              <h3>系统管理</h3>
              <p>管理用户权限，配置系统参数，确保系统安全稳定运行。</p>
            </div>
          </div>
          
          <div class="tech-stack">
            <h3>技术栈</h3>
            <div class="tech-list">
              <el-tag type="primary" effect="light">SpringBoot</el-tag>
              <el-tag type="success" effect="light">Python</el-tag>
              <el-tag type="info" effect="light">Vue 3</el-tag>
              <el-tag type="warning" effect="light">Element Plus</el-tag>
              <el-tag type="danger" effect="light">ECharts</el-tag>
              <el-tag type="primary" effect="light">MySQL</el-tag>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'About'
}
</script>

<style scoped>
.about {
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

.feature-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-bottom: 40px;
}

.feature-card {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 24px;
  text-align: center;
  transition: all 0.2s ease;
  border: 1px solid var(--border-light);
}

.feature-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-color);
}

.feature-icon {
  font-size: 48px;
  color: var(--primary-color);
  margin-bottom: 16px;
}

.feature-card h3 {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.feature-card p {
  font-size: 14px;
  line-height: 1.6;
  color: var(--text-secondary);
}

.tech-stack {
  margin-top: 40px;
  padding-top: 24px;
  border-top: 1px solid var(--border-light);
}

.tech-stack h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 16px;
  text-align: center;
}

.tech-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 12px;
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .feature-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media screen and (max-width: 768px) {
  .feature-grid {
    grid-template-columns: 1fr;
  }
  
  .content-card {
    padding: 24px;
  }
  
  .content-card h2 {
    font-size: 24px;
  }
  
  .intro-text {
    font-size: 14px;
  }
}
</style>

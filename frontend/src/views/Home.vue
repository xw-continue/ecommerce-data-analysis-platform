<template>
  <div class="home">
    <!-- 英雄区域 -->
    <div class="hero-section">
      <div class="container">
        <div class="hero-content">
          <div class="hero-text">
            <div class="hero-badge">
              <el-tag type="primary" effect="light">基于SpringBoot+Python技术</el-tag>
            </div>
            <h1 class="hero-title">数据驱动增长<br/><span class="highlight">智能电商分析</span></h1>
            <p class="hero-description">
              为电商企业提供全方位的销售数据分析解决方案，实时监控销售动态，智能预测市场趋势，让数据成为您的决策利器，助力业务增长。
            </p>
            <div class="hero-buttons">
              <el-button type="primary" size="large" @click="goToLogin" class="primary-button">
                <el-icon><i-ep-unlock /></el-icon>
                立即体验
              </el-button>
              <el-button size="large" @click="learnMore" class="secondary-button">
                <el-icon><i-ep-info /></el-icon>
                了解更多
              </el-button>
            </div>
          </div>
          <div class="hero-image">
            <div class="image-container">
              <img src="https://trae-api-cn.mchost.guru/api/ide/v1/text_to_image?prompt=modern%20ecommerce%20analytics%20dashboard%20with%20sales%20charts%20and%20customer%20insights%20colorful%20interface&image_size=landscape_16_9" alt="电商数据分析仪表盘" class="hero-img">
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 数据统计 -->
    <div class="stats-section">
      <div class="container">
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon><i-ep-trend-charts /></el-icon>
            </div>
            <div class="stat-value">100万+</div>
            <div class="stat-label">分析交易数据</div>
          </div>
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon><i-ep-rocket /></el-icon>
            </div>
            <div class="stat-value">30%</div>
            <div class="stat-label">平均销售增长</div>
          </div>
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon><i-ep-bell /></el-icon>
            </div>
            <div class="stat-value">24/7</div>
            <div class="stat-label">实时数据监控</div>
          </div>
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon><i-ep-target /></el-icon>
            </div>
            <div class="stat-value">95%</div>
            <div class="stat-label">预测准确率</div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 核心功能 -->
    <div class="features-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">核心功能</h2>
          <p class="section-description">全面的数据分析功能，满足电商企业的各种需求</p>
        </div>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-sales"><i-ep-data-analysis /></el-icon>
            </div>
            <h3 class="feature-title">销售趋势分析</h3>
            <p class="feature-description">通过折线图展示销售趋势，支持时间范围选择（日、周、月、年），展示销售额、销量、订单数等多个维度的趋势变化。</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-category"><i-ep-grid /></el-icon>
            </div>
            <h3 class="feature-title">商品分类分析</h3>
            <p class="feature-description">通过饼图展示商品分类占比，支持点击查看详情，展示各分类的销售额、销量、订单数等指标，支持按销售额、销量、订单数等维度排序。</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-region"><i-ep-position /></el-icon>
            </div>
            <h3 class="feature-title">地区销售分析</h3>
            <p class="feature-description">通过柱状图展示地区销售分布，支持按销售额排序，展示各地区的销售额、销量、订单数等指标，支持地区筛选和对比分析。</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-hot"><i-ep-fire /></el-icon>
            </div>
            <h3 class="feature-title">热销商品分析</h3>
            <p class="feature-description">通过柱状图展示热销商品排行，支持查看Top 10商品，展示商品的销售额、销量、订单数等指标，支持按销售额、销量、订单数等维度排序。</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-prediction"><i-ep-sunny /></el-icon>
            </div>
            <h3 class="feature-title">销售预测</h3>
            <p class="feature-description">基于历史数据，使用线性回归模型预测未来销售趋势，展示预测销售额、预测销量、置信度等指标，支持调整预测参数。</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">
              <el-icon class="icon-export"><i-ep-download /></el-icon>
            </div>
            <h3 class="feature-title">数据导出</h3>
            <p class="feature-description">支持将分析结果导出为Excel、CSV等格式，方便企业进行进一步分析和报告生成，支持自定义导出字段和时间范围。</p>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 客户案例 -->
    <div class="cases-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">客户案例</h2>
          <p class="section-description">来自各行业电商企业的成功实践</p>
        </div>
        <div class="cases-grid">
          <div class="case-card">
            <div class="case-icon">
              <el-icon><i-ep-shopping-cart /></el-icon>
            </div>
            <h3 class="case-title">时尚电商</h3>
            <p class="case-description">通过我们的数据分析平台，某时尚电商品牌实现了销售额20%的增长，精准识别热销商品和潜在客户群体。</p>
          </div>
          <div class="case-card">
            <div class="case-icon">
              <el-icon><i-ep-phone /></el-icon>
            </div>
            <h3 class="case-title">电子消费品</h3>
            <p class="case-description">某电子消费品零售商利用我们的预测分析功能，优化了库存管理，减少了30%的库存成本，提高了运营效率。</p>
          </div>
          <div class="case-card">
            <div class="case-icon">
              <el-icon><i-ep-food /></el-icon>
            </div>
            <h3 class="case-title">食品电商</h3>
            <p class="case-description">某食品电商平台通过地区销售分析，精准定位目标市场，在3个月内新开拓了5个地区市场，销售额提升25%。</p>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 技术特点 -->
    <div class="tech-section">
      <div class="container">
        <div class="section-header">
          <h2 class="section-title">技术特点</h2>
          <p class="section-description">先进的技术架构，确保系统的稳定性和可靠性</p>
        </div>
        <div class="tech-grid">
          <div class="tech-item">
            <div class="tech-icon">
              <el-icon><i-ep-link /></el-icon>
            </div>
            <h3 class="tech-title">前后端分离架构</h3>
            <p class="tech-description">采用前后端分离的架构设计，提高了系统的可维护性和可扩展性。前端和后端通过RESTful API进行通信，实现了松耦合。</p>
          </div>
          <div class="tech-item">
            <div class="tech-icon">
              <el-icon><i-ep-tools /></el-icon>
            </div>
            <h3 class="tech-title">多技术栈融合</h3>
            <p class="tech-description">结合了Spring Boot、Python、Vue 3等多种技术栈，充分发挥各技术的优势，构建了一个功能完善、性能稳定的数据分析系统。</p>
          </div>
          <div class="tech-item">
            <div class="tech-icon">
              <el-icon><i-ep-data-analysis /></el-icon>
            </div>
            <h3 class="tech-title">智能销售预测</h3>
            <p class="tech-description">使用Python的机器学习库实现销售预测，为企业决策提供数据支持，基于历史数据预测未来销售趋势。</p>
          </div>
          <div class="tech-item">
            <div class="tech-icon">
              <el-icon><i-ep-sunny /></el-icon>
            </div>
            <h3 class="tech-title">响应式设计</h3>
            <p class="tech-description">前端页面采用响应式设计，适配不同屏幕尺寸，提供良好的用户体验，使得用户可以在任何设备上使用系统。</p>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 登录提示 -->
    <div class="login-prompt">
      <div class="container">
        <div class="prompt-content">
          <h2 class="prompt-title">开始使用</h2>
          <p class="prompt-description">登录后即可访问完整的数据分析功能，开始探索您的销售数据，驱动业务增长</p>
          <el-button type="primary" @click="goToLogin" size="large" class="prompt-button">
            <el-icon><i-ep-unlock /></el-icon>
            立即登录
          </el-button>
        </div>
      </div>
    </div>
    
    <!-- 页脚 -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-logo">
            <el-icon class="logo-icon"><i-ep-data-analysis /></el-icon>
            <span class="brand-name">电商销售数据分析平台</span>
          </div>
          <div class="footer-links">
            <div class="link-group">
              <h4 class="link-title">产品</h4>
              <a href="#" class="footer-link">核心功能</a>
              <a href="#" class="footer-link">工作原理</a>
              <a href="#" class="footer-link">用户案例</a>
            </div>
            <div class="link-group">
              <h4 class="link-title">公司</h4>
              <a href="#" class="footer-link">关于我们</a>
              <a href="#" class="footer-link">联系我们</a>
              <a href="#" class="footer-link">加入我们</a>
            </div>
            <div class="link-group">
              <h4 class="link-title">支持</h4>
              <a href="#" class="footer-link">帮助中心</a>
              <a href="#" class="footer-link">API文档</a>
              <a href="#" class="footer-link">常见问题</a>
            </div>
          </div>
        </div>
        <div class="footer-bottom">
          <p class="footer-text">© 2026 电商销售数据分析平台 | 基于SpringBoot+Python开发</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  methods: {
    goToLogin() {
      // 触发登录事件，打开登录弹窗
      this.$eventBus.emit('openLoginDialog')
    },
    learnMore() {
      // 滚动到核心功能部分
      document.querySelector('.features-section').scrollIntoView({ behavior: 'smooth' })
    }
  }
}
</script>

<style scoped>
.home {
  width: 100%;
  min-height: 100vh;
  background-color: var(--bg-light);
  overflow-x: hidden;
  position: relative;
  color: var(--text-primary);
  font-family: var(--font-family);
}

/* 英雄区域 */
.hero-section {
  background-color: var(--bg-light);
  padding: 80px 0;
  color: var(--text-primary);
  position: relative;
  overflow: hidden;
}

.hero-content {
  display: flex;
  align-items: center;
  gap: 60px;
  position: relative;
  z-index: 1;
}

.hero-text {
  flex: 1;
  animation: fadeInLeft 1s ease-out;
}

.hero-badge {
  margin-bottom: 20px;
}

.hero-title {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 20px;
  line-height: 1.2;
  color: var(--text-primary);
}

.highlight {
  color: var(--primary-color);
}

.hero-description {
  font-size: 16px;
  margin-bottom: 24px;
  line-height: 1.6;
  color: var(--text-secondary);
  max-width: 500px;
}

.hero-buttons {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

.primary-button {
  border-radius: 6px;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.primary-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.secondary-button {
  border-radius: 6px;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--bg-gray);
  border: 1px solid var(--border-light);
  color: var(--text-primary);
}

.secondary-button:hover {
  background-color: var(--bg-dark);
  box-shadow: var(--shadow-sm);
}

.hero-image {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: fadeInRight 1s ease-out;
}

.image-container {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: var(--shadow-md);
  transition: transform 0.2s ease;
}

.image-container:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-lg);
}

.hero-img {
  width: 100%;
  height: auto;
  border-radius: 12px;
}

/* 数据统计 */
.stats-section {
  background-color: var(--bg-gray);
  padding: 60px 0;
  margin-top: -40px;
  position: relative;
  z-index: 2;
  border-radius: 24px 24px 0 0;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  text-align: center;
}

.stat-item {
  padding: 24px;
  background-color: var(--bg-light);
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  transition: all 0.2s ease;
  border: 1px solid var(--border-light);
  position: relative;
  overflow: hidden;
}

.stat-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 3px;
  background-color: var(--primary-color);
  transform: scaleX(0);
  transition: transform 0.2s ease;
}

.stat-item:hover::before {
  transform: scaleX(1);
}

.stat-item:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-md);
}

.stat-icon {
  font-size: 32px;
  color: var(--primary-color);
  margin-bottom: 16px;
  transition: all 0.2s ease;
}

.stat-item:hover .stat-icon {
  transform: scale(1.1);
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 8px;
  font-family: var(--font-family);
}

.stat-label {
  font-size: 14px;
  color: var(--text-secondary);
  font-weight: 500;
}

/* 核心功能 */
.features-section {
  padding: 80px 0;
  background-color: var(--bg-light);
  position: relative;
  overflow: hidden;
}

.section-header {
  text-align: center;
  margin-bottom: 60px;
  position: relative;
  z-index: 1;
}

.section-title {
  font-size: 32px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.section-description {
  font-size: 16px;
  color: var(--text-secondary);
  line-height: 1.6;
  max-width: 500px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  position: relative;
  z-index: 1;
}

.feature-card {
  background-color: var(--bg-light);
  border-radius: 12px;
  padding: 32px;
  transition: all 0.3s ease;
  box-shadow: var(--shadow-sm);
  text-align: center;
  border: 1px solid var(--border-light);
  position: relative;
  overflow: hidden;
}

.feature-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 3px;
  background-color: var(--primary-color);
  transform: scaleX(0);
  transition: transform 0.2s ease;
}

.feature-card:hover::before {
  transform: scaleX(1);
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-color);
}

.feature-icon {
  font-size: 40px;
  margin-bottom: 16px;
  transition: all 0.2s ease;
}

.feature-card:hover .feature-icon {
  transform: scale(1.1);
}

.icon-sales {
  color: var(--primary-color);
}

.icon-category {
  color: var(--secondary-color);
}

.icon-region {
  color: var(--accent-color);
}

.icon-hot {
  color: #ff6b6b;
}

.icon-prediction {
  color: #a855f7;
}

.icon-export {
  color: #3b82f6;
}

.feature-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.feature-description {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}

/* 客户案例 */
.cases-section {
  padding: 80px 0;
  background-color: var(--bg-gray);
  position: relative;
  overflow: hidden;
}

.cases-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  position: relative;
  z-index: 1;
}

.case-card {
  background-color: var(--bg-light);
  border-radius: 12px;
  padding: 32px;
  transition: all 0.3s ease;
  box-shadow: var(--shadow-sm);
  text-align: center;
  border: 1px solid var(--border-light);
  position: relative;
  overflow: hidden;
}

.case-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 3px;
  background-color: var(--primary-color);
  transform: scaleX(0);
  transition: transform 0.2s ease;
}

.case-card:hover::before {
  transform: scaleX(1);
}

.case-card:hover {
  transform: translateY(-8px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-color);
}

.case-icon {
  font-size: 36px;
  margin-bottom: 16px;
  transition: all 0.2s ease;
  color: var(--primary-color);
}

.case-card:hover .case-icon {
  transform: scale(1.1);
}

.case-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.case-description {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}

/* 技术特点 */
.tech-section {
  padding: 80px 0;
  background-color: var(--bg-light);
  position: relative;
  overflow: hidden;
}

.tech-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
  position: relative;
  z-index: 1;
}

.tech-item {
  padding: 24px;
  background-color: var(--bg-gray);
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  transition: all 0.2s ease;
  border: 1px solid var(--border-light);
  position: relative;
  overflow: hidden;
}

.tech-item::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 3px;
  background-color: var(--primary-color);
  border-radius: 3px 0 0 3px;
}

.tech-item:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-md);
  border-color: var(--primary-color);
}

.tech-icon {
  font-size: 24px;
  color: var(--primary-color);
  margin-bottom: 12px;
}

.tech-title {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.tech-description {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
}

/* 登录提示 */
.login-prompt {
  padding: 80px 0;
  background-color: var(--bg-gray);
  text-align: center;
  position: relative;
  overflow: hidden;
}

.prompt-content {
  background-color: var(--bg-light);
  padding: 48px;
  border-radius: 12px;
  box-shadow: var(--shadow-sm);
  max-width: 600px;
  margin: 0 auto;
  border: 1px solid var(--border-light);
  transition: all 0.2s ease;
  position: relative;
  z-index: 1;
}

.prompt-content:hover {
  box-shadow: var(--shadow-md);
  transform: translateY(-4px);
  border-color: var(--primary-color);
}

.prompt-title {
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.prompt-description {
  font-size: 16px;
  color: var(--text-secondary);
  margin-bottom: 24px;
  line-height: 1.6;
}

.prompt-button {
  border-radius: 6px;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.prompt-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

/* 页脚 */
.footer {
  background-color: var(--bg-light);
  color: var(--text-primary);
  padding: 60px 0 32px;
  border-top: 1px solid var(--border-light);
  position: relative;
  overflow: hidden;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  margin-bottom: 32px;
  flex-wrap: wrap;
  gap: 32px;
  position: relative;
  z-index: 1;
}

.footer-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 16px;
  flex: 1;
  min-width: 200px;
}

.logo-icon {
  font-size: 24px;
  color: var(--primary-color);
}

.footer-links {
  display: flex;
  gap: 48px;
  flex: 2;
  flex-wrap: wrap;
  position: relative;
  z-index: 1;
}

.link-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-width: 120px;
}

.link-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 12px;
  color: var(--text-primary);
}

.footer-link {
  color: var(--text-secondary);
  text-decoration: none;
  font-size: 14px;
  transition: all 0.2s ease;
}

.footer-link:hover {
  color: var(--primary-color);
  transform: translateX(4px);
}

.footer-bottom {
  border-top: 1px solid var(--border-light);
  padding-top: 24px;
  text-align: center;
  position: relative;
  z-index: 1;
}

.footer-text {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
}

/* 动画效果 */
@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInLeft {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.hero-content, .stat-item, .feature-card, .case-card, .tech-item, .prompt-content {
  animation: fadeInUp 0.6s ease-out;
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .hero-content {
    gap: 40px;
  }
  
  .hero-title {
    font-size: 32px;
  }
  
  .features-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .footer-content {
    flex-direction: column;
    gap: 32px;
  }
  
  .footer-links {
    gap: 32px;
  }
}

@media screen and (max-width: 768px) {
  .hero-content {
    flex-direction: column;
    text-align: center;
  }
  
  .hero-title {
    font-size: 28px;
  }
  
  .hero-buttons {
    justify-content: center;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
  }
  
  .cases-grid {
    grid-template-columns: 1fr;
  }
  
  .tech-grid {
    grid-template-columns: 1fr;
  }
  
  .section-title {
    font-size: 24px;
  }
  
  .prompt-content {
    padding: 32px;
  }
  
  .footer-links {
    flex-direction: column;
    gap: 16px;
  }
  
  .container {
    padding: 0 20px;
  }
  
  .hero-section,
  .features-section,
  .tech-section,
  .cases-section,
  .login-prompt {
    padding: 60px 0;
  }
  
  .stats-section {
    padding: 40px 0;
  }
}
</style>

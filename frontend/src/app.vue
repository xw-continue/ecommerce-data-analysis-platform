<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <nav class="navbar">
      <div class="container">
        <div class="navbar-brand">
          <el-icon class="logo-icon"><i-ep-data-analysis /></el-icon>
          <span class="brand-name">电商销售数据分析平台</span>
        </div>
        <div class="navbar-menu" v-if="!showSidebar">
          <router-link to="/" class="menu-item">首页</router-link>
          <router-link to="/about" class="menu-item">项目介绍</router-link>
          <router-link to="/demo" class="menu-item">数据可视化</router-link>
          <router-link to="/contact" class="menu-item">联系我们</router-link>
        </div>
        <div class="navbar-actions">
          <div class="search-box">
            <el-input placeholder="搜索" prefix-icon="i-ep-search" size="small" />
          </div>
          <div v-if="!showSidebar">
            <el-button type="primary" size="small" @click="goToLogin" class="login-button">
              登录
            </el-button>
          </div>
          <div v-else class="user-info">
            <span class="welcome-text">欢迎，{{ username }}</span>
          </div>
        </div>
      </div>
    </nav>
    
    <!-- 主内容区域 -->
    <div class="main-content">
      <!-- 侧边栏 -->
      <div class="sidebar" v-if="showSidebar">
        <div class="sidebar-header">
          <h3>功能菜单</h3>
        </div>
        <div class="sidebar-menu">
          <div class="menu-section">
            <div class="menu-section-title">数据概览</div>
            <router-link to="/data-overview" class="menu-item">
              <el-icon><i-ep-data-analysis /></el-icon>
              <span>数据概览</span>
            </router-link>
          </div>
          <div class="menu-section">
            <div class="menu-section-title">销售分析</div>
            <router-link to="/sales-analysis" class="menu-item">
              <el-icon><i-ep-line-chart /></el-icon>
              <span>销售趋势</span>
            </router-link>
            <router-link to="/product-category" class="menu-item">
              <el-icon><i-ep-grid /></el-icon>
              <span>商品分类</span>
            </router-link>
            <router-link to="/region-analysis" class="menu-item">
              <el-icon><i-ep-position /></el-icon>
              <span>地区分析</span>
            </router-link>
            <router-link to="/hot-products" class="menu-item">
              <el-icon><i-ep-fire /></el-icon>
              <span>热销商品</span>
            </router-link>
            <router-link to="/sales-prediction" class="menu-item">
            <el-icon><i-ep-top /></el-icon>
            <span>销售预测</span>
          </router-link>
          <router-link to="/payment-analysis" class="menu-item">
            <el-icon><i-ep-credit-card /></el-icon>
            <span>支付分析</span>
          </router-link>
          <router-link to="/customer-analysis" class="menu-item">
            <el-icon><i-ep-user /></el-icon>
            <span>客户分析</span>
          </router-link>
          </div>
          <div class="menu-section">
            <div class="menu-section-title">系统管理</div>
            <router-link to="/admin" class="menu-item" v-if="userRole === 'admin'">
              <el-icon><i-ep-setting /></el-icon>
              <span>系统设置</span>
            </router-link>
            <div class="menu-item" @click="logout">
              <el-icon><i-ep-switch-button /></el-icon>
              <span>退出登录</span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 内容区域 -->
      <div class="content">
        <keep-alive>
          <router-view />
        </keep-alive>
      </div>
    </div>
    
    <!-- 登录弹窗 -->
    <el-dialog
      v-model="loginDialogVisible"
      width="900px"
      class="auth-dialog"
      :show-close="false"
    >
      <div class="auth-container">
        <!-- 左侧品牌区域 -->
        <div class="auth-brand">
          <div class="brand-content">
            <div class="brand-icon">
              <el-icon class="icon-chart"><i-ep-data-analysis /></el-icon>
            </div>
            <h1 class="brand-title">电商销售数据分析平台</h1>
            <p class="brand-subtitle">专业的数据可视化分析工具，帮助您洞察业务趋势，做出明智决策</p>
            <div class="brand-features">
              <div class="feature-item">
                <el-icon><i-ep-check /></el-icon>
                <span>实时数据监控</span>
              </div>
              <div class="feature-item">
                <el-icon><i-ep-check /></el-icon>
                <span>多维度分析</span>
              </div>
              <div class="feature-item">
                <el-icon><i-ep-check /></el-icon>
                <span>可视化报表</span>
              </div>
            </div>
          </div>
          <div class="brand-decoration">
            <div class="decoration-circle circle-1"></div>
            <div class="decoration-circle circle-2"></div>
            <div class="decoration-circle circle-3"></div>
          </div>
        </div>
        
        <!-- 右侧表单区域 -->
        <div class="auth-form-wrapper">
          <button class="close-button" @click="loginDialogVisible = false">
            <el-icon><i-ep-close /></el-icon>
          </button>
          
          <div class="auth-header">
            <h2 class="auth-title">{{ authMode === 'login' ? '欢迎回来' : '创建账户' }}</h2>
            <p class="auth-subtitle">{{ authMode === 'login' ? '请登录您的账户继续使用' : '填写以下信息完成注册' }}</p>
          </div>
          
          <div v-if="authMode === 'login'" class="login-form">
            <div class="form-item">
              <label class="form-label">用户名</label>
              <el-input 
                v-model="loginForm.username" 
                placeholder="请输入用户名" 
                size="large"
                class="auth-input"
              >
                <template #prefix>
                  <el-icon><i-ep-user /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="form-item">
              <label class="form-label">密码</label>
              <el-input 
                v-model="loginForm.password" 
                type="password" 
                placeholder="请输入密码" 
                size="large"
                class="auth-input"
                @keyup.enter="login"
              >
                <template #prefix>
                  <el-icon><i-ep-lock /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="form-actions">
              <el-button type="primary" @click="login" size="large" class="auth-button">登录系统</el-button>
            </div>
            <div class="auth-toggle" @click="toggleAuthMode" style="margin-top: 16px;">
              {{ authMode === 'login' ? '还没有账户？立即注册' : '已经有账户？立即登录' }}
            </div>
          </div>
          
          <div v-else class="register-form">
            <div class="form-item">
              <label class="form-label">用户名</label>
              <el-input 
                v-model="registerForm.username" 
                placeholder="请输入用户名" 
                size="large"
                class="auth-input"
              >
                <template #prefix>
                  <el-icon><i-ep-user /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="form-item">
              <label class="form-label">密码</label>
              <el-input 
                v-model="registerForm.password" 
                type="password" 
                placeholder="请输入密码" 
                size="large"
                class="auth-input"
              >
                <template #prefix>
                  <el-icon><i-ep-lock /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="form-item">
              <label class="form-label">确认密码</label>
              <el-input 
                v-model="registerForm.confirmPassword" 
                type="password" 
                placeholder="请确认密码" 
                size="large"
                class="auth-input"
                @keyup.enter="register"
              >
                <template #prefix>
                  <el-icon><i-ep-lock /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="form-item">
              <label class="form-label">角色</label>
              <el-select v-model="registerForm.role" size="large" class="auth-select">
                <el-option label="普通用户" value="user" />
                <el-option label="管理员" value="admin" />
              </el-select>
            </div>
            <div class="form-actions">
              <el-button type="primary" @click="register" size="large" class="auth-button">注册</el-button>
            </div>
            <div class="auth-toggle" @click="toggleAuthMode" style="margin-top: 16px;">
              {{ authMode === 'login' ? '还没有账户？立即注册' : '已经有账户？立即登录' }}
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import apiClient from './utils/api'

export default {
  data() {
    return {
      showSidebar: false,
      loginDialogVisible: false,
      authMode: 'login', // 'login' 或 'register'
      username: '',
      userRole: '',
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user' // 默认角色为普通用户
      }
    }
  },
  mounted() {
    // 检查用户是否已经登录
    const user = localStorage.getItem('user')
    if (user) {
      const userObj = JSON.parse(user)
      // 如果已经登录，显示侧边栏并设置用户名和角色
      this.showSidebar = true
      this.username = userObj.username || '用户'
      this.userRole = userObj.role || 'user'
    }
    
    // 监听打开登录弹窗的事件
    this.$eventBus.on('openLoginDialog', () => {
      this.loginDialogVisible = true
    })
  },
  methods: {
    goToLogin() {
      this.loginDialogVisible = true
    },
    toggleAuthMode() {
      this.authMode = this.authMode === 'login' ? 'register' : 'login'
    },
    async login() {
      try {
        // 调用后端API进行登录
        const response = await apiClient.post('/user/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        
        if (response.data.success) {
          const user = response.data.user
          this.$message.success('登录成功')
          localStorage.setItem('user', JSON.stringify({ 
            username: user.username, 
            role: user.role 
          }))
          this.loginDialogVisible = false
          this.showSidebar = true
          this.username = user.username
          this.userRole = user.role
          // 跳转到数据概览页面
          this.$router.push('/data-overview')
        } else {
          // 显示具体的错误信息
          const errorMessage = response.data.message || '登录失败'
          if (errorMessage.includes('用户不存在')) {
            this.$message.error('用户不存在，请先注册')
          } else if (errorMessage.includes('密码错误')) {
            this.$message.error('密码错误，请检查输入')
          } else if (errorMessage.includes('用户名或密码错误')) {
            this.$message.error('用户名或密码错误，请检查输入')
          } else if (errorMessage.includes('用户类型不匹配')) {
            this.$message.error('用户类型不匹配，请确认登录类型')
          } else {
            this.$message.error(errorMessage)
          }
        }
      } catch (error) {
        console.error('登录失败:', error)
        if (error.response) {
          // 服务器返回错误
          const errorMessage = error.response.data?.message || '登录失败，请稍后重试'
          if (errorMessage.includes('用户不存在')) {
            this.$message.error('用户不存在，请先注册')
          } else if (errorMessage.includes('密码错误')) {
            this.$message.error('密码错误，请检查输入')
          } else if (errorMessage.includes('用户名或密码错误')) {
            this.$message.error('用户名或密码错误，请检查输入')
          } else if (errorMessage.includes('用户类型不匹配')) {
            this.$message.error('用户类型不匹配，请确认登录类型')
          } else {
            this.$message.error(errorMessage)
          }
        } else if (error.request) {
          // 请求已发出但没有收到响应
          this.$message.error('网络错误，无法连接到服务器，请检查网络')
        } else {
          // 请求配置出错
          this.$message.error('登录失败，请稍后重试')
        }
      }
    },
    async register() {
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.$message.error('两次输入的密码不一致')
        return
      }
      try {
        // 调用后端API进行注册
        const response = await apiClient.post('/user/register', {
          username: this.registerForm.username,
          password: this.registerForm.password,
          role: this.registerForm.role
        })
        
        if (response.data.success) {
          this.$message.success('注册成功，请登录')
          // 切换到登录模式
          this.authMode = 'login'
        } else {
          // 显示具体的错误信息
          const errorMessage = response.data.message || '注册失败'
          if (errorMessage.includes('用户名已存在')) {
            this.$message.error('用户名已存在，请选择其他用户名')
          } else {
            this.$message.error(errorMessage)
          }
        }
      } catch (error) {
        console.error('注册失败:', error)
        if (error.response) {
          // 服务器返回错误
          const errorMessage = error.response.data?.message || '注册失败，请稍后重试'
          this.$message.error(errorMessage)
        } else if (error.request) {
          // 请求已发出但没有收到响应
          this.$message.error('网络错误，无法连接到服务器，请检查网络')
        } else {
          // 请求配置出错
          this.$message.error('注册失败，请稍后重试')
        }
      }
    },
    logout() {
      localStorage.removeItem('user')
      this.showSidebar = false
      this.username = ''
      this.userRole = ''
      this.$message.success('退出登录成功')
      this.$router.push('/')
    }
  }
}
</script>

<style>
#app {
  width: 100%;
  min-height: 100vh;
  background-color: var(--bg-light);
  color: var(--text-primary);
}

/* 导航栏样式 */
.navbar {
  background-color: var(--bg-light);
  box-shadow: var(--shadow-sm);
  padding: 24px 0;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar .container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 20px;
  font-weight: 600;
  color: var(--primary-color);
  cursor: pointer;
  transition: all 0.2s ease;
}

.navbar-brand:hover {
  color: var(--primary-light);
}

.logo-icon {
  font-size: 24px;
  color: var(--primary-color);
}

.brand-name {
  color: var(--text-primary);
}

.navbar-menu {
  display: flex;
  gap: 32px;
}

.menu-item {
  text-decoration: none;
  color: var(--text-secondary);
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  padding: 6px 0;
  position: relative;
}

.menu-item:hover {
  color: var(--primary-color);
}

.menu-item:hover::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: var(--primary-color);
  border-radius: 1px;
}

.navbar-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.search-box {
  width: 200px;
}

.login-button {
  border-radius: 6px;
  padding: 6px 16px;
  font-size: 14px;
  height: 36px;
  background-color: var(--primary-color) !important;
  border: 1px solid var(--primary-color) !important;
  color: white !important;
}

.login-button:hover {
  background-color: var(--primary-light) !important;
  box-shadow: var(--shadow-md) !important;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.welcome-text {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
}

/* 主内容区域 */
.main-content {
  display: flex;
  min-height: calc(100vh - 72px);
}

/* 侧边栏 */
.sidebar {
  width: 240px;
  background-color: var(--bg-light);
  box-shadow: var(--shadow-sm);
  padding: 24px 0;
  border-right: 1px solid var(--border-light);
  position: sticky;
  top: 72px;
  height: calc(100vh - 72px);
  overflow-y: auto;
}

.sidebar-header {
  padding: 0 24px 16px;
  border-bottom: 1px solid var(--border-light);
  margin-bottom: 16px;
}

.sidebar-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.sidebar-menu {
  padding: 0 16px;
}

.menu-section {
  margin-bottom: 24px;
}

.menu-section-title {
  font-size: 12px;
  font-weight: 600;
  color: var(--text-muted);
  text-transform: uppercase;
  margin-bottom: 8px;
  padding: 0 8px;
}

.sidebar .menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 8px;
  border-radius: 6px;
  transition: all 0.2s ease;
  cursor: pointer;
  margin-bottom: 4px;
}

.sidebar .menu-item:hover {
  background-color: var(--bg-gray);
  color: var(--primary-color);
}

.sidebar .menu-item.active,
.sidebar .router-link-active,
.sidebar .router-link-exact-active {
  background-color: var(--bg-gray);
  color: var(--primary-color);
  font-weight: 600;
  border-left: 3px solid var(--primary-color);
  padding-left: 5px;
}

.sidebar .menu-item el-icon {
  font-size: 16px;
}

/* 内容区域 */
.content {
  flex: 1;
  padding: 24px;
  background-color: var(--bg-gray);
}

/* 现代登录/注册弹窗样式 */
.auth-dialog {
  border-radius: 16px !important;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25) !important;
  overflow: hidden;
  background: var(--bg-light);
  padding: 0 !important;
  border: none !important;
}

.auth-dialog.el-dialog {
  margin: 0 auto !important;
  top: 50% !important;
  left: auto !important;
  right: auto !important;
  transform: translateY(-50%) !important;
  display: flex !important;
  flex-direction: column !important;
  max-height: 90vh !important;
  width: 90% !important;
  max-width: 900px !important;
  min-width: 700px !important;
}

.auth-dialog.el-dialog .el-dialog__body {
  padding: 0 !important;
  margin: 0 !important;
  overflow: hidden !important;
  flex: 1 !important;
  display: flex !important;
  flex-direction: column !important;
}

.auth-dialog .el-dialog__header {
  display: none !important;
}

.auth-dialog .el-dialog__footer {
  display: none !important;
}

.auth-dialog .el-dialog__body {
  padding: 0 !important;
  margin: 0 !important;
  overflow: hidden;
}

.auth-container {
  display: flex;
  height: 100%;
  min-height: 580px;
  width: 100%;
  max-width: 900px;
}

/* 左侧品牌区域 */
.auth-brand {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 48px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.brand-content {
  position: relative;
  z-index: 2;
  color: white;
}

.brand-icon {
  width: 72px;
  height: 72px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 24px;
}

.brand-icon .icon-chart {
  font-size: 36px;
  color: white;
}

.brand-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 16px;
  line-height: 1.3;
}

.brand-subtitle {
  font-size: 16px;
  opacity: 0.9;
  line-height: 1.6;
  margin-bottom: 32px;
}

.brand-features {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  font-weight: 500;
}

.feature-item .el-icon {
  font-size: 18px;
}

/* 装饰圆形 */
.brand-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
}

.circle-1 {
  width: 200px;
  height: 200px;
  top: -50px;
  right: -50px;
}

.circle-2 {
  width: 150px;
  height: 150px;
  bottom: 50px;
  left: -30px;
}

.circle-3 {
  width: 100px;
  height: 100px;
  bottom: -30px;
  right: 80px;
}

/* 右侧表单区域 */
.auth-form-wrapper {
  flex: 1;
  padding: 32px 48px;
  display: flex;
  flex-direction: column;
  position: relative;
  gap: 20px;
}

.close-button {
  position: absolute;
  top: 16px;
  right: 16px;
  width: 32px;
  height: 32px;
  border: none;
  background: var(--bg-gray);
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
  color: var(--text-secondary);
}

.close-button:hover {
  background: var(--border-light);
  color: var(--text-primary);
}

.auth-header {
  margin-bottom: 32px;
  z-index: 10;
  position: relative;
}

.auth-title {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  margin-bottom: 8px;
}

.auth-subtitle {
  font-size: 14px;
  color: #6b7280;
}

/* 表单样式 */
.login-form,
.register-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
  flex: 1;
}

.form-item {
  margin-bottom: 16px;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

/* 输入框样式 */
.auth-input {
  border-radius: 8px !important;
  border: 1px solid #e5e7eb !important;
  font-size: 14px !important;
  transition: all 0.3s ease !important;
  background-color: white !important;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05) !important;
  height: 48px !important;
}

.auth-input:focus-within {
  border-color: #667eea !important;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1) !important;
  transform: translateY(-1px);
}

/* 下拉框样式 */
.auth-select {
  border-radius: 8px !important;
  border: 1px solid #e5e7eb !important;
  font-size: 14px !important;
  width: 100% !important;
  background-color: white !important;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05) !important;
  height: 48px !important;
}

.auth-select:focus-within {
  border-color: #667eea !important;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1) !important;
  transform: translateY(-1px);
}

.form-actions {
  margin-top: 24px;
  margin-bottom: 32px;
}

.auth-button {
  width: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
  border: none !important;
  border-radius: 8px !important;
  height: 48px !important;
  font-size: 16px !important;
  font-weight: 600 !important;
  transition: all 0.3s ease !important;
  color: white !important;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4) !important;
  margin-top: 8px;
}

.auth-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.5) !important;
}

.auth-button:active {
  transform: translateY(0);
}

.auth-toggle {
  text-align: center;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  font-weight: 600;
  color: #667eea;
}

.auth-toggle:hover {
  color: #764ba2;
  opacity: 0.9;
  transform: translateY(-1px);
}

/* 确保内部元素没有多余边框 */
.login-form .el-input__wrapper,
.login-form .el-select__wrapper,
.register-form .el-input__wrapper,
.register-form .el-select__wrapper {
  border: none !important;
  box-shadow: none !important;
  background-color: transparent !important;
  border-radius: 8px;
}

.login-form .el-input--large .el-input__wrapper,
.register-form .el-input--large .el-input__wrapper {
  padding: 4px 16px;
}

.login-form .el-form-item.is-error .auth-input,
.register-form .el-form-item.is-error .auth-input {
  border-color: var(--accent-color) !important;
}

.login-form .el-form-item.is-error .el-form-item__error,
.register-form .el-form-item.is-error .el-form-item__error {
  color: var(--accent-color) !important;
  font-size: 12px !important;
  line-height: 1 !important;
  padding-top: 4px !important;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .auth-container {
    flex-direction: column;
  }
  
  .auth-brand {
    padding: 32px;
  }
  
  .brand-title {
    font-size: 22px;
  }
  
  .brand-subtitle {
    font-size: 14px;
  }
  
  .auth-form-wrapper {
    padding: 32px;
  }
  
  .auth-title {
    font-size: 24px;
  }
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .sidebar {
    width: 200px;
  }
  
  .navbar-menu {
    gap: 24px;
  }
}

@media (max-width: 768px) {
  .sidebar {
    display: none;
  }
  
  .navbar-menu {
    display: none;
  }
  
  .content {
    padding: 16px;
  }
  
  .navbar-actions {
    gap: 8px;
  }
  
  .search-box {
    width: 150px;
  }
}
</style>

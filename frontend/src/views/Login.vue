<template>
  <div class="login">
    <div class="container">
      <div class="login-content">
        <div class="login-left">
          <h1 class="login-title">联盟链版权管理系统</h1>
          <p class="login-subtitle">BLOCKCHAIN COPYRIGHT MANAGEMENT SYSTEM</p>
          
          <div class="login-features">
            <div class="feature-item">
              <el-icon><i-ep-lock /></el-icon>
              <span>安全可靠</span>
              <p>基于FISCO BCOS联盟链技术</p>
            </div>
            <div class="feature-item">
              <el-icon><i-ep-shield /></el-icon>
              <span>版权保护</span>
              <p>区块确认，永久可追溯</p>
            </div>
            <div class="feature-item">
              <el-icon><i-ep-sold /></el-icon>
              <span>全程监管</span>
              <p>对授权交易进行实时状态跟踪</p>
            </div>
            <div class="feature-item">
              <el-icon><i-ep-data-analysis /></el-icon>
              <span>数据可视</span>
              <p>高效展示区块网络拓扑补</p>
            </div>
          </div>
          
          <div class="login-stats">
            <div class="stat-item">
              <div class="stat-value">1,234</div>
              <div class="stat-label">版权数</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">5,678</div>
              <div class="stat-label">授权次数</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">99.9%</div>
              <div class="stat-label">系统可用性</div>
            </div>
          </div>
        </div>
        
        <div class="login-right">
          <div class="login-form">
            <h2 class="form-title">欢迎登录</h2>
            <p class="form-subtitle">请选择角色并输入您的区块链地址</p>
            
            <el-form :model="loginForm" ref="loginForm" label-position="top">
              <el-form-item label="用户角色" required>
                <el-select v-model="loginForm.role" placeholder="请选择用户角色" class="auth-select">
                  <el-option label="内容拥有者" value="owner" />
                  <el-option label="内容使用者" value="user" />
                </el-select>
              </el-form-item>
              <el-form-item label="钱包地址" required>
                <el-input v-model="loginForm.walletAddress" placeholder="请输入钱包地址" class="auth-input" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="login" class="auth-button">登录系统</el-button>
              </el-form-item>
              <div class="form-links">
                <span>还没有账户？</span>
                <a href="#" @click.prevent="goToRegister" class="register-link">申请注册</a>
              </div>
              <div class="form-links">
                <span>已经交易？</span>
                <a href="#" class="status-link">查询状态</a>
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      loginForm: {
        role: '',
        walletAddress: ''
      }
    }
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/api/user/login', {
          role: this.loginForm.role,
          walletAddress: this.loginForm.walletAddress
        })
        if (response.data.success) {
          this.$message.success('登录成功')
          localStorage.setItem('user', JSON.stringify(response.data.user))
          // 跳转到版权管理页面
          this.$router.push('/copyright-management')
        } else {
          this.$message.error(response.data.message)
        }
      } catch (error) {
        console.error('登录失败:', error)
        this.$message.error('登录失败，请稍后重试')
      }
    },
    goToRegister() {
      this.$router.push('/register')
    }
  }
}
</script>

<style scoped>
.login {
  width: 100%;
  min-height: 100vh;
  background-color: var(--bg-light);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
}

.login-content {
  display: flex;
  gap: 60px;
  background-color: var(--bg-white);
  border-radius: 12px;
  box-shadow: var(--shadow-lg);
  padding: 48px;
  width: 100%;
  max-width: 1000px;
}

.login-left {
  flex: 1;
  padding-right: 20px;
}

.login-title {
  font-size: 32px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 8px;
}

.login-subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 40px;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.login-features {
  margin-bottom: 40px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  margin-bottom: 20px;
  padding: 16px;
  background-color: var(--bg-gray);
  border-radius: 8px;
  transition: all 0.2s ease;
}

.feature-item:hover {
  background-color: var(--bg-dark);
  transform: translateX(4px);
}

.feature-item el-icon {
  font-size: 20px;
  color: var(--primary-color);
  margin-top: 2px;
}

.feature-item span {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 4px;
  display: block;
}

.feature-item p {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
  line-height: 1.4;
}

.login-stats {
  display: flex;
  gap: 24px;
  margin-top: 40px;
}

.stat-item {
  text-align: center;
  padding: 16px;
  background-color: var(--bg-gray);
  border-radius: 8px;
  flex: 1;
  transition: all 0.2s ease;
}

.stat-item:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}

.stat-value {
  font-size: 24px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  color: var(--text-secondary);
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-form {
  width: 100%;
  max-width: 350px;
  padding: 32px;
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.form-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
  text-align: center;
}

.form-subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 24px;
  text-align: center;
}

.auth-select {
  border-radius: 4px !important;
  border: 1px solid var(--border-light) !important;
  height: 44px !important;
  font-size: 14px !important;
  width: 100% !important;
  background-color: var(--bg-white) !important;
  box-shadow: var(--shadow-sm) !important;
}

.auth-select:focus-within {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 0 2px rgba(106, 90, 205, 0.2) !important;
}

.auth-input {
  border-radius: 4px !important;
  border: 1px solid var(--border-light) !important;
  height: 44px !important;
  font-size: 14px !important;
  padding: 0 16px !important;
  transition: all 0.2s ease !important;
  background-color: var(--bg-white) !important;
  box-shadow: var(--shadow-sm) !important;
}

.auth-input:focus-within {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 0 2px rgba(106, 90, 205, 0.2) !important;
}

.auth-button {
  background-color: var(--primary-color) !important;
  border: 1px solid var(--primary-color) !important;
  border-radius: 4px !important;
  height: 44px !important;
  font-size: 14px !important;
  font-weight: 600 !important;
  transition: all 0.2s ease !important;
  margin-top: 8px !important;
  color: white !important;
  width: 100% !important;
}

.auth-button:hover {
  background-color: var(--primary-light) !important;
  box-shadow: var(--shadow-md) !important;
}

.form-links {
  margin-top: 16px;
  text-align: center;
  font-size: 14px;
  color: var(--text-secondary);
}

.register-link,
.status-link {
  color: var(--primary-color);
  text-decoration: none;
  margin-left: 4px;
  transition: all 0.2s ease;
}

.register-link:hover,
.status-link:hover {
  color: var(--primary-light);
  text-decoration: underline;
}

/* 确保内部元素没有多余边框 */
.el-input__wrapper,
.el-select__wrapper {
  border: none !important;
  box-shadow: none !important;
  background-color: transparent !important;
}

.el-form-item.is-error .auth-input,
.el-form-item.is-error .auth-select {
  border-color: var(--accent-color) !important;
  box-shadow: 0 0 0 2px rgba(255, 107, 107, 0.2) !important;
}

.el-form-item.is-error .el-form-item__error {
  color: var(--accent-color) !important;
  font-size: 12px !important;
  line-height: 1 !important;
  padding-top: 4px !important;
  margin-top: 4px !important;
}

/* 响应式设计 */
@media screen and (max-width: 1024px) {
  .login-content {
    flex-direction: column;
    gap: 40px;
    padding: 32px;
  }
  
  .login-left {
    padding-right: 0;
  }
  
  .login-features {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }
  
  .feature-item {
    margin-bottom: 0;
  }
}

@media screen and (max-width: 768px) {
  .login {
    padding: 20px;
  }
  
  .login-content {
    padding: 24px;
  }
  
  .login-title {
    font-size: 28px;
  }
  
  .login-features {
    grid-template-columns: 1fr;
  }
  
  .login-stats {
    flex-direction: column;
    gap: 16px;
  }
  
  .login-form {
    padding: 24px;
  }
}
</style>

<template>
  <div class="register">
    <div class="container">
      <div class="register-content">
        <div class="register-left">
          <h1 class="register-title">联盟链版权管理系统</h1>
          <p class="register-subtitle">BLOCKCHAIN COPYRIGHT MANAGEMENT SYSTEM</p>
          
          <div class="register-features">
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
        </div>
        
        <div class="register-right">
          <div class="register-form">
            <h2 class="form-title">申请注册</h2>
            <p class="form-subtitle">请填写您的信息以注册账户</p>
            
            <el-form :model="registerForm" ref="registerForm" label-position="top">
              <el-form-item label="用户名" required>
                <el-input v-model="registerForm.username" placeholder="请输入用户名" class="auth-input" />
              </el-form-item>
              <el-form-item label="密码" required>
                <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" class="auth-input" />
              </el-form-item>
              <el-form-item label="确认密码" required>
                <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" class="auth-input" />
              </el-form-item>
              <el-form-item label="邮箱" required>
                <el-input v-model="registerForm.email" placeholder="请输入邮箱" class="auth-input" />
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input v-model="registerForm.phone" placeholder="请输入手机号码" class="auth-input" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="register" class="auth-button">注册</el-button>
              </el-form-item>
              <div class="form-links">
                <span>已经有账户？</span>
                <a href="#" @click.prevent="goToLogin" class="login-link">立即登录</a>
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
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        phone: ''
      }
    }
  },
  methods: {
    async register() {
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.$message.error('两次输入的密码不一致')
        return
      }
      
      try {
        const response = await axios.post('/api/user/register', {
          username: this.registerForm.username,
          password: this.registerForm.password,
          email: this.registerForm.email,
          phone: this.registerForm.phone
        })
        if (response.data.success) {
          this.$message.success('注册成功，请登录')
          // 跳转到登录页面
          this.$router.push('/login')
        } else {
          this.$message.error(response.data.message)
        }
      } catch (error) {
        console.error('注册失败:', error)
        this.$message.error('注册失败，请稍后重试')
      }
    },
    goToLogin() {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.register {
  width: 100%;
  min-height: 100vh;
  background-color: var(--bg-light);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 0;
}

.register-content {
  display: flex;
  gap: 60px;
  background-color: var(--bg-white);
  border-radius: 12px;
  box-shadow: var(--shadow-lg);
  padding: 48px;
  width: 100%;
  max-width: 1000px;
}

.register-left {
  flex: 1;
  padding-right: 20px;
}

.register-title {
  font-size: 32px;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 8px;
}

.register-subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 40px;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.register-features {
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

.register-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.register-form {
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

.login-link {
  color: var(--primary-color);
  text-decoration: none;
  margin-left: 4px;
  transition: all 0.2s ease;
}

.login-link:hover {
  color: var(--primary-light);
  text-decoration: underline;
}

/* 确保内部元素没有多余边框 */
.el-input__wrapper {
  border: none !important;
  box-shadow: none !important;
  background-color: transparent !important;
}

.el-form-item.is-error .auth-input {
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
  .register-content {
    flex-direction: column;
    gap: 40px;
    padding: 32px;
  }
  
  .register-left {
    padding-right: 0;
  }
  
  .register-features {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }
  
  .feature-item {
    margin-bottom: 0;
  }
}

@media screen and (max-width: 768px) {
  .register {
    padding: 20px;
  }
  
  .register-content {
    padding: 24px;
  }
  
  .register-title {
    font-size: 28px;
  }
  
  .register-features {
    grid-template-columns: 1fr;
  }
  
  .register-form {
    padding: 24px;
  }
}
</style>

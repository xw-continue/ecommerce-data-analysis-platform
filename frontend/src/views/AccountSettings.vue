<template>
  <div class="account-settings">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>账户设置</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>账户设置</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 设置内容 -->
      <div class="settings-content">
        <div class="settings-sidebar">
          <div class="sidebar-menu">
            <div class="menu-item active">
              <el-icon><i-ep-user /></el-icon>
              <span>个人信息</span>
            </div>
            <div class="menu-item">
              <el-icon><i-ep-lock /></el-icon>
              <span>安全设置</span>
            </div>
            <div class="menu-item">
              <el-icon><i-ep-bank-card /></el-icon>
              <span>钱包管理</span>
            </div>
            <div class="menu-item">
              <el-icon><i-ep-notification /></el-icon>
              <span>通知设置</span>
            </div>
          </div>
        </div>
        
        <div class="settings-main">
          <div class="settings-card">
            <h2>个人信息</h2>
            <el-form :model="userForm" ref="userForm" label-position="top">
              <el-form-item label="用户名">
                <el-input v-model="userForm.username" class="form-input" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="userForm.email" class="form-input" />
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input v-model="userForm.phone" class="form-input" />
              </el-form-item>
              <el-form-item label="所属机构">
                <el-input v-model="userForm.organization" class="form-input" />
              </el-form-item>
              <el-form-item label="职位">
                <el-input v-model="userForm.position" class="form-input" />
              </el-form-item>
              <el-form-item label="个人简介">
                <el-input v-model="userForm.bio" type="textarea" class="form-textarea" :rows="4" />
              </el-form-item>
              <div class="form-actions">
                <el-button type="primary" @click="saveSettings" class="btn-primary">保存设置</el-button>
                <el-button @click="cancel" class="btn-secondary">取消</el-button>
              </div>
            </el-form>
          </div>
          
          <div class="settings-card">
            <h2>安全设置</h2>
            <div class="security-item">
              <div class="security-label">
                <el-icon><i-ep-lock /></el-icon>
                <span>修改密码</span>
              </div>
              <el-button type="primary" size="small" @click="changePassword">修改</el-button>
            </div>
            <div class="security-item">
              <div class="security-label">
                <el-icon><i-ep-phone /></el-icon>
                <span>手机验证</span>
              </div>
              <el-button type="primary" size="small" @click="verifyPhone">验证</el-button>
            </div>
            <div class="security-item">
              <div class="security-label">
                <el-icon><i-ep-message /></el-icon>
                <span>邮箱验证</span>
              </div>
              <el-button type="primary" size="small" @click="verifyEmail">验证</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userForm: {
        username: '张三',
        email: 'zhangsan@example.com',
        phone: '13800138000',
        organization: '某科技公司',
        position: '产品经理',
        bio: '专注于区块链技术和版权保护领域'
      }
    }
  },
  methods: {
    saveSettings() {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          this.$message.success('设置保存成功')
        } else {
          this.$message.error('请填写完整的信息')
        }
      })
    },
    cancel() {
      this.$refs.userForm.resetFields()
      this.$message.info('已取消修改')
    },
    changePassword() {
      this.$message.info('修改密码')
    },
    verifyPhone() {
      this.$message.info('验证手机')
    },
    verifyEmail() {
      this.$message.info('验证邮箱')
    }
  }
}
</script>

<style scoped>
.account-settings {
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

.settings-content {
  display: flex;
  gap: 24px;
  margin-top: 32px;
}

.settings-sidebar {
  width: 200px;
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  padding: 24px 0;
}

.sidebar-menu {
  padding: 0 16px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 8px;
  border-radius: 6px;
  transition: all 0.2s ease;
  cursor: pointer;
  margin-bottom: 4px;
}

.menu-item:hover {
  background-color: var(--bg-gray);
  color: var(--primary-color);
}

.menu-item.active {
  background-color: rgba(106, 90, 205, 0.1);
  color: var(--primary-color);
}

.menu-item el-icon {
  font-size: 16px;
}

.settings-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.settings-card {
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 32px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.settings-card h2 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 24px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-light);
}

.form-input {
  border-radius: 4px !important;
  border: 1px solid var(--border-light) !important;
  height: 44px !important;
  font-size: 14px !important;
  padding: 0 16px !important;
  transition: all 0.2s ease !important;
  background-color: var(--bg-white) !important;
  box-shadow: var(--shadow-sm) !important;
}

.form-input:focus-within {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 0 2px rgba(106, 90, 205, 0.2) !important;
}

.form-textarea {
  border-radius: 4px !important;
  border: 1px solid var(--border-light) !important;
  font-size: 14px !important;
  padding: 12px 16px !important;
  transition: all 0.2s ease !important;
  background-color: var(--bg-white) !important;
  box-shadow: var(--shadow-sm) !important;
  resize: vertical;
}

.form-textarea:focus-within {
  border-color: var(--primary-color) !important;
  box-shadow: 0 0 0 2px rgba(106, 90, 205, 0.2) !important;
}

.form-actions {
  display: flex;
  gap: 16px;
  margin-top: 32px;
  justify-content: flex-end;
}

.btn-primary {
  border-radius: 6px;
  padding: 10px 24px;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.btn-secondary {
  border-radius: 6px;
  padding: 10px 24px;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--bg-gray);
  border: 1px solid var(--border-light);
  color: var(--text-primary);
}

.btn-secondary:hover {
  background-color: var(--bg-dark);
  box-shadow: var(--shadow-sm);
}

.security-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid var(--border-light);
}

.security-item:last-child {
  border-bottom: none;
}

.security-label {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 14px;
  color: var(--text-primary);
}

.security-label el-icon {
  font-size: 16px;
  color: var(--primary-color);
}

/* 确保内部元素没有多余边框 */
.el-input__wrapper {
  border: none !important;
  box-shadow: none !important;
  background-color: transparent !important;
}

.el-form-item.is-error .form-input,
.el-form-item.is-error .form-textarea {
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
  .settings-content {
    flex-direction: column;
  }
  
  .settings-sidebar {
    width: 100%;
  }
  
  .sidebar-menu {
    display: flex;
    gap: 16px;
    overflow-x: auto;
    padding: 0 16px;
  }
  
  .menu-item {
    white-space: nowrap;
    margin-bottom: 0;
  }
}

@media screen and (max-width: 768px) {
  .settings-card {
    padding: 24px;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .btn-primary,
  .btn-secondary {
    width: 100%;
  }
}
</style>

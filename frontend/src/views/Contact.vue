<template>
  <div class="contact">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>联系我们</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>联系我们</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 联系内容 -->
      <div class="content-section">
        <div class="content-card">
          <h2>联系我们</h2>
          <p class="intro-text">
            如有任何问题或建议，欢迎通过以下方式联系我们。
          </p>
          
          <div class="contact-container">
            <!-- 联系信息 -->
            <div class="contact-info">
              <h3>联系方式</h3>
              <div class="info-item">
                <el-icon><i-ep-phone /></el-icon>
                <span>电话：123-4567-8910</span>
              </div>
              <div class="info-item">
                <el-icon><i-ep-message /></el-icon>
                <span>邮箱：contact@example.com</span>
              </div>
              <div class="info-item">
                <el-icon><i-ep-position /></el-icon>
                <span>地址：北京市海淀区中关村科技园区</span>
              </div>
              <div class="info-item">
                <el-icon><i-ep-time /></el-icon>
                <span>工作时间：周一至周五 9:00-18:00</span>
              </div>
            </div>
            
            <!-- 联系表单 -->
            <div class="contact-form">
              <h3>发送消息</h3>
              <el-form :model="form" ref="form" label-width="100px">
                <el-form-item label="姓名" required>
                  <el-input v-model="form.name" placeholder="请输入您的姓名" />
                </el-form-item>
                <el-form-item label="邮箱" required>
                  <el-input v-model="form.email" placeholder="请输入您的邮箱" />
                </el-form-item>
                <el-form-item label="电话">
                  <el-input v-model="form.phone" placeholder="请输入您的电话" />
                </el-form-item>
                <el-form-item label="主题" required>
                  <el-input v-model="form.subject" placeholder="请输入消息主题" />
                </el-form-item>
                <el-form-item label="消息" required>
                  <el-input v-model="form.message" type="textarea" :rows="4" placeholder="请输入您的消息" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm" class="submit-button">
                    <el-icon><i-ep-send /></el-icon>
                    发送消息
                  </el-button>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Contact',
  data() {
    return {
      form: {
        name: '',
        email: '',
        phone: '',
        subject: '',
        message: ''
      }
    }
  },
  methods: {
    submitForm() {
      if (!this.form.name || !this.form.email || !this.form.subject || !this.form.message) {
        this.$message.error('请填写必填字段')
        return
      }
      
      // 模拟表单提交
      setTimeout(() => {
        this.$message.success('消息发送成功，我们会尽快回复您')
        // 重置表单
        this.form = {
          name: '',
          email: '',
          phone: '',
          subject: '',
          message: ''
        }
      }, 1000)
    }
  }
}
</script>

<style scoped>
.contact {
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

.contact-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.contact-info {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 32px;
  border: 1px solid var(--border-light);
}

.contact-info h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 20px;
  font-size: 14px;
  color: var(--text-secondary);
}

.info-item el-icon {
  font-size: 20px;
  color: var(--primary-color);
}

.contact-form {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 32px;
  border: 1px solid var(--border-light);
}

.contact-form h3 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.submit-button {
  border-radius: 6px;
  padding: 10px 24px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.submit-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .contact-container {
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
  
  .contact-info,
  .contact-form {
    padding: 24px;
  }
  
  .info-item {
    font-size: 14px;
  }
}
</style>

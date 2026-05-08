<template>
  <div class="copyright-management">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>版权管理</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item><a href="#">版权管理</a></el-breadcrumb-item>
        <el-breadcrumb-item>注册版权</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 步骤导航 -->
      <div class="steps">
        <div class="step active">
          <div class="step-number">1</div>
          <div class="step-title">基本信息</div>
          <div class="step-description">填写版权的基本信息</div>
        </div>
        <div class="step">
          <div class="step-number">2</div>
          <div class="step-title">文件信息</div>
          <div class="step-description">上传相关证明材料</div>
        </div>
        <div class="step">
          <div class="step-number">3</div>
          <div class="step-title">确认提交</div>
          <div class="step-description">核对信息并提交</div>
        </div>
      </div>
      
      <!-- 表单区域 -->
      <div class="form-section">
        <div class="form-left">
          <h2>基本信息</h2>
          <p class="form-description">填写版权的基本信息，包括标题和作者</p>
          
          <el-form :model="copyrightForm" ref="copyrightForm" label-position="top">
            <el-form-item label="版权标题" required>
              <el-input v-model="copyrightForm.title" placeholder="请输入版权标题（如：歌曲名称、作品名称等）" class="form-input" />
            </el-form-item>
            
            <el-form-item label="作者/创作省" required>
              <el-input v-model="copyrightForm.author" placeholder="请输入作者姓名" class="form-input" />
            </el-form-item>
            
            <el-form-item label="作品描述" required>
              <el-input v-model="copyrightForm.description" type="textarea" placeholder="请输入作品描述，包含创作背景、使用范围等信息" class="form-textarea" :rows="4" />
            </el-form-item>
            
            <el-form-item label="版权类型">
              <el-radio-group v-model="copyrightForm.type">
                <el-radio label="音乐作品">音乐作品</el-radio>
                <el-radio label="文学作品">文学作品</el-radio>
                <el-radio label="视觉艺术">视觉艺术</el-radio>
                <el-radio label="影视作品">影视作品</el-radio>
                <el-radio label="软件作品">软件作品</el-radio>
              </el-radio-group>
            </el-form-item>
            
            <el-form-item label="创作时间">
              <el-date-picker v-model="copyrightForm.creationDate" type="date" placeholder="请选择创作时间" class="form-input" />
            </el-form-item>
            
            <el-form-item label="首次发表时间">
              <el-date-picker v-model="copyrightForm.publicationDate" type="date" placeholder="请选择首次发表时间" class="form-input" />
            </el-form-item>
            
            <div class="form-actions">
              <el-button type="primary" @click="nextStep" class="btn-primary">下一步</el-button>
              <el-button @click="cancel" class="btn-secondary">取消</el-button>
            </div>
          </el-form>
        </div>
        
        <div class="form-right">
          <!-- 注册说明 -->
          <div class="info-card">
            <h3>注册说明</h3>
            <ul>
              <li>版权信息将永久记录在区块链上，不可修改</li>
              <li>请确保填写的标题和作者信息准确无误</li>
              <li>文件将用于版权验证的完整性和唯一性</li>
              <li>注册后将自动生成唯一的版权ID</li>
            </ul>
          </div>
          
          <!-- 最近注册 -->
          <div class="info-card">
            <h3>最近注册</h3>
            <div class="recent-item">
              <div class="recent-title">版权一：百年孤独</div>
              <div class="recent-date">2026-04-01</div>
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
      copyrightForm: {
        title: '',
        author: '',
        description: '',
        type: '音乐作品',
        creationDate: '',
        publicationDate: ''
      }
    }
  },
  methods: {
    nextStep() {
      // 验证表单
      this.$refs.copyrightForm.validate((valid) => {
        if (valid) {
          // 跳转到文件信息步骤
          // 这里可以实现步骤切换逻辑
          this.$message.success('基本信息验证通过，进入下一步')
        } else {
          this.$message.error('请填写完整的基本信息')
        }
      })
    },
    cancel() {
      // 重置表单
      this.$refs.copyrightForm.resetFields()
      this.$message.info('已取消注册')
    }
  }
}
</script>

<style scoped>
.copyright-management {
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

.steps {
  display: flex;
  gap: 40px;
  margin: 32px 0;
  padding-bottom: 24px;
  border-bottom: 1px solid var(--border-light);
}

.step {
  flex: 1;
  position: relative;
  text-align: center;
}

.step::after {
  content: '';
  position: absolute;
  top: 20px;
  left: 50%;
  width: 100%;
  height: 2px;
  background-color: var(--border-light);
  z-index: 1;
}

.step:last-child::after {
  display: none;
}

.step.active {
  color: var(--primary-color);
}

.step.active::after {
  background-color: var(--primary-color);
}

.step-number {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: var(--bg-white);
  border: 2px solid var(--border-light);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 600;
  margin: 0 auto 12px;
  position: relative;
  z-index: 2;
  transition: all 0.2s ease;
}

.step.active .step-number {
  background-color: var(--primary-color);
  border-color: var(--primary-color);
  color: white;
}

.step-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
}

.step-description {
  font-size: 12px;
  color: var(--text-secondary);
}

.form-section {
  display: flex;
  gap: 32px;
  margin-top: 32px;
}

.form-left {
  flex: 1;
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 32px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.form-left h2 {
  font-size: 20px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.form-description {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 24px;
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

.form-right {
  width: 300px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.info-card {
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.info-card h3 {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 16px;
}

.info-card ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.info-card li {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 8px;
  padding-left: 20px;
  position: relative;
}

.info-card li::before {
  content: '•';
  position: absolute;
  left: 0;
  color: var(--primary-color);
  font-weight: bold;
}

.recent-item {
  padding: 16px;
  background-color: var(--bg-gray);
  border-radius: 6px;
  margin-top: 12px;
}

.recent-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 4px;
}

.recent-date {
  font-size: 12px;
  color: var(--text-secondary);
}

/* 确保内部元素没有多余边框 */
.el-input__wrapper,
.el-select__wrapper,
.el-date-picker__wrapper {
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
  .form-section {
    flex-direction: column;
  }
  
  .form-right {
    width: 100%;
    flex-direction: row;
  }
  
  .info-card {
    flex: 1;
  }
}

@media screen and (max-width: 768px) {
  .steps {
    flex-direction: column;
    gap: 24px;
  }
  
  .step::after {
    display: none;
  }
  
  .form-right {
    flex-direction: column;
  }
  
  .form-left {
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

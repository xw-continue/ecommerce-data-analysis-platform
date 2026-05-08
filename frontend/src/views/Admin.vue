<template>
  <div class="admin">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>系统管理</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 标签页 -->
      <el-tabs v-model="activeTab">
        <el-tab-pane label="用户管理" name="users">
          <!-- 用户管理内容 -->
          <div class="tab-content">
            <div class="filter-section">
              <el-input v-model="userSearch" placeholder="搜索用户" size="small" prefix-icon="i-ep-search" class="search-input" />
              <el-button type="primary" size="small" @click="addUser" class="add-button">
                <el-icon><i-ep-plus /></el-icon>
                添加用户
              </el-button>
            </div>
            
            <div class="detail-card">
              <div class="detail-content">
                <el-table :data="usersData" style="width: 100%">
                  <el-table-column prop="id" label="ID" width="80" />
                  <el-table-column prop="username" label="用户名" width="150" />
                  <el-table-column prop="role" label="角色" width="120">
                    <template #default="scope">
                      <el-tag :type="scope.row.role === 'admin' ? 'primary' : 'success'">
                        {{ scope.row.role === 'admin' ? '管理员' : '普通用户' }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" width="150">
                    <template #default="scope">
                      <el-button type="primary" size="small" @click="editUser(scope.row)" class="edit-button">
                        <el-icon><i-ep-edit /></el-icon>
                        编辑
                      </el-button>
                      <el-button type="danger" size="small" @click="deleteUser(scope.row.id)" class="delete-button">
                        <el-icon><i-ep-delete /></el-icon>
                        删除
                      </el-button>
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
                  :total="usersData.length"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                />
              </div>
            </div>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="系统设置" name="settings">
          <!-- 系统设置内容 -->
          <div class="tab-content">
            <div class="settings-grid">
              <div class="settings-card">
                <div class="settings-header">
                  <h3>基本设置</h3>
                </div>
                <div class="settings-content">
                  <el-form :model="systemSettings" label-width="120px">
                    <el-form-item label="系统名称">
                      <el-input v-model="systemSettings.systemName" size="small" />
                    </el-form-item>
                    <el-form-item label="系统版本">
                      <el-input v-model="systemSettings.version" size="small" disabled />
                    </el-form-item>
                    <el-form-item label="API 地址">
                      <el-input v-model="systemSettings.apiUrl" size="small" />
                    </el-form-item>
                    <el-form-item label="数据刷新间隔">
                      <el-input-number v-model="systemSettings.refreshInterval" :min="10" :max="3600" size="small" />
                      <span class="unit">秒</span>
                    </el-form-item>
                  </el-form>
                </div>
                <div class="settings-footer">
                  <el-button type="primary" size="small" @click="saveSettings" class="save-button">
                    <el-icon><i-ep-check /></el-icon>
                    保存设置
                  </el-button>
                </div>
              </div>
              
              <div class="settings-card">
                <div class="settings-header">
                  <h3>数据设置</h3>
                </div>
                <div class="settings-content">
                  <el-form :model="dataSettings" label-width="120px">
                    <el-form-item label="数据备份">
                      <el-button type="primary" size="small" @click="backupData" class="backup-button">
                        <el-icon><i-ep-download /></el-icon>
                        备份数据
                      </el-button>
                    </el-form-item>
                    <el-form-item label="数据清理">
                      <el-button type="danger" size="small" @click="cleanData" class="clean-button">
                        <el-icon><i-ep-delete /></el-icon>
                        清理数据
                      </el-button>
                    </el-form-item>
                    <el-form-item label="自动备份">
                      <el-switch v-model="dataSettings.autoBackup" size="small" />
                    </el-form-item>
                    <el-form-item label="备份频率">
                      <el-select v-model="dataSettings.backupFrequency" size="small">
                        <el-option label="每天" value="daily" />
                        <el-option label="每周" value="weekly" />
                        <el-option label="每月" value="monthly" />
                      </el-select>
                    </el-form-item>
                  </el-form>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="日志管理" name="logs">
          <!-- 日志管理内容 -->
          <div class="tab-content">
            <div class="filter-section">
              <el-input v-model="logSearch" placeholder="搜索日志" size="small" prefix-icon="i-ep-search" class="search-input" />
              <el-select v-model="logType" size="small" class="log-type-select">
                <el-option label="全部" value="all" />
                <el-option label="登录" value="login" />
                <el-option label="操作" value="operation" />
                <el-option label="错误" value="error" />
              </el-select>
            </div>
            
            <div class="detail-card">
              <div class="detail-content">
                <el-table :data="logsData" style="width: 100%">
                  <el-table-column prop="id" label="ID" width="80" />
                  <el-table-column prop="type" label="类型" width="100">
                    <template #default="scope">
                      <el-tag :type="{
                        'login': 'primary',
                        'operation': 'success',
                        'error': 'danger'
                      }[scope.row.type]">
                        {{ {
                          'login': '登录',
                          'operation': '操作',
                          'error': '错误'
                        }[scope.row.type] }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column prop="message" label="内容" />
                  <el-table-column prop="userId" label="用户ID" width="100" />
                  <el-table-column prop="ip" label="IP地址" width="150" />
                  <el-table-column prop="createdAt" label="时间" width="180" />
                </el-table>
              </div>
              <div class="detail-footer">
                <el-pagination
                  v-model:current-page="currentPage"
                  v-model:page-size="pageSize"
                  :page-sizes="[10, 20, 50, 100]"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="logsData.length"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                />
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    
    <!-- 用户表单对话框 -->
    <el-dialog
      v-model="userDialogVisible"
      :title="isEdit ? '编辑用户' : '添加用户'"
      width="400px"
    >
      <el-form :model="userForm" ref="userFormRef" label-width="80px">
        <el-form-item label="用户名" required>
          <el-input v-model="userForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="角色" required>
          <el-select v-model="userForm.role">
            <el-option label="普通用户" value="user" />
            <el-option label="管理员" value="admin" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="userDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveUser">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import apiClient from '../utils/api'

export default {
  data() {
    return {
      activeTab: 'users',
      userSearch: '',
      logSearch: '',
      logType: 'all',
      currentPage: 1,
      pageSize: 10,
      usersData: [],
      logsData: [
        { id: 1, type: 'login', message: '用户 admin 登录成功', userId: 1, ip: '192.168.1.1', createdAt: '2026-01-01 10:00:00' },
        { id: 2, type: 'operation', message: '用户 admin 修改了系统设置', userId: 1, ip: '192.168.1.1', createdAt: '2026-01-01 10:30:00' },
        { id: 3, type: 'error', message: '用户 user1 登录失败', userId: 2, ip: '192.168.1.2', createdAt: '2026-01-01 11:00:00' },
        { id: 4, type: 'login', message: '用户 user2 登录成功', userId: 3, ip: '192.168.1.3', createdAt: '2026-01-01 12:00:00' },
        { id: 5, type: 'operation', message: '用户 user2 导出了销售数据', userId: 3, ip: '192.168.1.3', createdAt: '2026-01-01 12:30:00' }
      ],
      systemSettings: {
        systemName: '电商销售数据分析平台',
        version: '1.0.0',
        apiUrl: 'http://localhost:8090/api',
        refreshInterval: 60
      },
      dataSettings: {
        autoBackup: true,
        backupFrequency: 'weekly'
      },
      // 用户表单相关
      userDialogVisible: false,
      isEdit: false,
      userForm: {
        id: '',
        username: '',
        password: '',
        role: 'user'
      }
    }
  },
  mounted() {
    // 获取用户列表
    this.getUserList()
  },
  methods: {
    // 获取用户列表
    async getUserList() {
      try {
        const response = await apiClient.get('/user/list')
        if (response.data.success) {
          this.usersData = response.data.data
        } else {
          this.$message.error('获取用户列表失败')
        }
      } catch (error) {
        console.error('获取用户列表失败:', error)
        this.$message.error('网络错误，无法连接到服务器')
      }
    },
    // 添加用户
    addUser() {
      this.isEdit = false
      this.userForm = {
        id: '',
        username: '',
        password: '',
        role: 'user'
      }
      this.userDialogVisible = true
    },
    // 编辑用户
    editUser(user) {
      this.isEdit = true
      this.userForm = {
        id: user.id,
        username: user.username,
        password: '', // 密码不回显
        role: user.role
      }
      this.userDialogVisible = true
    },
    // 保存用户
    async saveUser() {
      try {
        if (this.isEdit) {
          // 更新用户
          const response = await apiClient.post('/user/update', {
            id: this.userForm.id,
            username: this.userForm.username,
            password: this.userForm.password,
            role: this.userForm.role
          })
          if (response.data.success) {
            this.$message.success('更新用户成功')
            this.userDialogVisible = false
            this.getUserList() // 重新获取用户列表
          } else {
            this.$message.error(response.data.message || '更新用户失败')
          }
        } else {
          // 添加用户
          const response = await apiClient.post('/user/add', {
            username: this.userForm.username,
            password: this.userForm.password,
            role: this.userForm.role
          })
          if (response.data.success) {
            this.$message.success('添加用户成功')
            this.userDialogVisible = false
            this.getUserList() // 重新获取用户列表
          } else {
            this.$message.error(response.data.message || '添加用户失败')
          }
        }
      } catch (error) {
        console.error('保存用户失败:', error)
        this.$message.error('网络错误，无法连接到服务器')
      }
    },
    // 删除用户
    async deleteUser(id) {
      this.$confirm('确定要删除该用户吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const response = await apiClient.post('/user/delete', {
            id: id
          })
          if (response.data.success) {
            this.$message.success('用户删除成功')
            this.getUserList() // 重新获取用户列表
          } else {
            this.$message.error(response.data.message || '删除用户失败')
          }
        } catch (error) {
          console.error('删除用户失败:', error)
          this.$message.error('网络错误，无法连接到服务器')
        }
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    saveSettings() {
      this.$message.success('设置保存成功')
    },
    backupData() {
      this.$message.success('数据备份成功')
    },
    cleanData() {
      this.$confirm('确定要清理数据吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message.success('数据清理成功')
      }).catch(() => {
        this.$message.info('已取消清理')
      })
    },
    handleSizeChange(size) {
      this.pageSize = size
    },
    handleCurrentChange(current) {
      this.currentPage = current
    }
  }
}
</script>

<style scoped>
.admin {
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

.tab-content {
  margin-top: 24px;
}

.filter-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 24px 0;
  padding: 16px;
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.search-input {
  width: 300px;
}

.log-type-select {
  width: 120px;
}

.add-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.add-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.admin-card {
  background-color: var(--bg-light);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  margin-bottom: 24px;
}
.detail-content {
  margin-bottom: 20px;
}

.detail-footer {
  display: flex;
  justify-content: flex-end;
}

.edit-button {
  margin-right: 8px;
  border-radius: 6px;
  padding: 4px 8px;
  font-size: 12px;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.delete-button {
  border-radius: 6px;
  padding: 4px 8px;
  font-size: 12px;
  background-color: var(--accent-color);
  border: 1px solid var(--accent-color);
  color: white;
}

.settings-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}

.settings-card {
  background-color: var(--bg-white);
  border-radius: 8px;
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.settings-header {
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid var(--border-light);
}

.settings-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin: 0;
}

.settings-content {
  margin-bottom: 20px;
}

.settings-footer {
  display: flex;
  justify-content: flex-end;
}

.save-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.save-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.backup-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--primary-color);
  border: 1px solid var(--primary-color);
  color: white;
}

.backup-button:hover {
  background-color: var(--primary-light);
  box-shadow: var(--shadow-md);
}

.clean-button {
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  background-color: var(--accent-color);
  border: 1px solid var(--accent-color);
  color: white;
}

.clean-button:hover {
  background-color: #ef4444;
  box-shadow: var(--shadow-md);
}

.unit {
  margin-left: 8px;
  font-size: 14px;
  color: var(--text-secondary);
}

/* 响应式设计 */
@media screen and (max-width: 1200px) {
  .settings-grid {
    grid-template-columns: 1fr;
  }
  
  .filter-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .search-input {
    width: 100%;
  }
}

@media screen and (max-width: 768px) {
  .detail-footer {
    justify-content: center;
  }
  
  .settings-footer {
    justify-content: center;
  }
}
</style>

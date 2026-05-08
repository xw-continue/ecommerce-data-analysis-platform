<template>
  <div class="authorization-management">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>授权管理</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>授权管理</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-input placeholder="搜索授权记录" prefix-icon="i-ep-search" class="search-input" />
        </div>
        <div class="filter-right">
          <el-select v-model="statusFilter" placeholder="状态" class="status-select">
            <el-option label="全部" value="all" />
            <el-option label="待审核" value="pending" />
            <el-option label="已通过" value="approved" />
            <el-option label="已拒绝" value="rejected" />
          </el-select>
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" class="date-picker" />
          <el-button type="primary" class="btn-primary">
            <el-icon><i-ep-plus /></el-icon>
            新增授权
          </el-button>
        </div>
      </div>
      
      <!-- 授权列表 -->
      <div class="authorization-list">
        <el-table :data="authorizationData" style="width: 100%">
          <el-table-column prop="id" label="授权ID" width="180" />
          <el-table-column prop="copyrightTitle" label="版权标题" />
          <el-table-column prop="requester" label="申请人" width="180" />
          <el-table-column prop="purpose" label="使用目的" />
          <el-table-column prop="startDate" label="开始日期" width="180" />
          <el-table-column prop="endDate" label="结束日期" width="180" />
          <el-table-column prop="status" label="状态" width="120">
            <template #default="scope">
              <el-tag :type="getTagType(scope.row.status)">{{ scope.row.statusText }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button size="small" @click="viewDetails(scope.row)">查看</el-button>
              <el-button size="small" type="primary" @click="approveAuthorization(scope.row)" v-if="scope.row.status === 'pending'">通过</el-button>
              <el-button size="small" type="danger" @click="rejectAuthorization(scope.row)" v-if="scope.row.status === 'pending'">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      statusFilter: 'all',
      dateRange: [],
      currentPage: 1,
      pageSize: 10,
      total: 100,
      authorizationData: [
        {
          id: 'AUTH-20260401-001',
          copyrightTitle: '百年孤独',
          requester: '张三',
          purpose: '用于出版发行',
          startDate: '2026-04-01',
          endDate: '2027-04-01',
          status: 'approved',
          statusText: '已通过'
        },
        {
          id: 'AUTH-20260402-002',
          copyrightTitle: '星空',
          requester: '李四',
          purpose: '用于展览',
          startDate: '2026-04-02',
          endDate: '2026-10-02',
          status: 'pending',
          statusText: '待审核'
        },
        {
          id: 'AUTH-20260403-003',
          copyrightTitle: '命运交响曲',
          requester: '王五',
          purpose: '用于演出',
          startDate: '2026-04-03',
          endDate: '2026-04-30',
          status: 'rejected',
          statusText: '已拒绝'
        }
      ]
    }
  },
  methods: {
    getTagType(status) {
      switch (status) {
        case 'approved':
          return 'success'
        case 'pending':
          return 'warning'
        case 'rejected':
          return 'danger'
        default:
          return 'info'
      }
    },
    viewDetails(row) {
      this.$message.info(`查看授权详情：${row.id}`)
    },
    approveAuthorization(row) {
      this.$message.success(`通过授权：${row.id}`)
    },
    rejectAuthorization(row) {
      this.$message.success(`拒绝授权：${row.id}`)
    },
    handleSizeChange(size) {
      this.pageSize = size
      // 重新获取数据
    },
    handleCurrentChange(current) {
      this.currentPage = current
      // 重新获取数据
    }
  }
}
</script>

<style scoped>
.authorization-management {
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

.filter-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 24px 0;
  padding: 16px;
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.filter-left {
  flex: 1;
  max-width: 300px;
}

.search-input {
  width: 100%;
  border-radius: 4px;
  border: 1px solid var(--border-light);
  height: 40px;
}

.filter-right {
  display: flex;
  gap: 16px;
  align-items: center;
}

.status-select,
.date-picker {
  width: 180px;
}

.btn-primary {
  border-radius: 6px;
  padding: 8px 16px;
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

.authorization-list {
  background-color: var(--bg-white);
  border-radius: 8px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  overflow: hidden;
}

.pagination {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

/* 响应式设计 */
@media screen and (max-width: 1024px) {
  .filter-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .filter-left {
    max-width: 100%;
    width: 100%;
  }
  
  .filter-right {
    width: 100%;
    justify-content: space-between;
  }
  
  .status-select,
  .date-picker {
    flex: 1;
  }
}

@media screen and (max-width: 768px) {
  .filter-right {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .status-select,
  .date-picker {
    width: 100%;
  }
  
  .btn-primary {
    width: 100%;
  }
  
  .pagination {
    justify-content: center;
  }
}
</style>

<template>
  <div class="transaction-records">
    <div class="container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h1>交易记录</h1>
      </div>
      
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><a href="#">首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>交易记录</el-breadcrumb-item>
      </el-breadcrumb>
      
      <!-- 筛选区域 -->
      <div class="filter-section">
        <div class="filter-left">
          <el-input placeholder="搜索交易记录" prefix-icon="i-ep-search" class="search-input" />
        </div>
        <div class="filter-right">
          <el-select v-model="typeFilter" placeholder="交易类型" class="type-select">
            <el-option label="全部" value="all" />
            <el-option label="版权注册" value="registration" />
            <el-option label="授权交易" value="authorization" />
            <el-option label="版税支付" value="royalty" />
          </el-select>
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" class="date-picker" />
        </div>
      </div>
      
      <!-- 交易列表 -->
      <div class="transaction-list">
        <el-table :data="transactionData" style="width: 100%">
          <el-table-column prop="id" label="交易ID" width="180" />
          <el-table-column prop="type" label="交易类型" width="120">
            <template #default="scope">
              <el-tag :type="getTagType(scope.row.type)">{{ scope.row.typeText }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="relatedId" label="关联ID" width="180" />
          <el-table-column prop="amount" label="金额" width="120" />
          <el-table-column prop="transactionDate" label="交易日期" width="180" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getStatusTagType(scope.row.status)">{{ scope.row.statusText }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template #default="scope">
              <el-button size="small" @click="viewDetails(scope.row)">查看</el-button>
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
      typeFilter: 'all',
      dateRange: [],
      currentPage: 1,
      pageSize: 10,
      total: 100,
      transactionData: [
        {
          id: 'TXN-20260401-001',
          type: 'registration',
          typeText: '版权注册',
          relatedId: 'COPY-20260401-001',
          amount: '¥100.00',
          transactionDate: '2026-04-01 10:30:00',
          status: 'success',
          statusText: '成功'
        },
        {
          id: 'TXN-20260402-002',
          type: 'authorization',
          typeText: '授权交易',
          relatedId: 'AUTH-20260402-002',
          amount: '¥500.00',
          transactionDate: '2026-04-02 14:45:00',
          status: 'success',
          statusText: '成功'
        },
        {
          id: 'TXN-20260403-003',
          type: 'royalty',
          typeText: '版税支付',
          relatedId: 'AUTH-20260301-001',
          amount: '¥200.00',
          transactionDate: '2026-04-03 09:15:00',
          status: 'pending',
          statusText: '处理中'
        }
      ]
    }
  },
  methods: {
    getTagType(type) {
      switch (type) {
        case 'registration':
          return 'primary'
        case 'authorization':
          return 'success'
        case 'royalty':
          return 'warning'
        default:
          return 'info'
      }
    },
    getStatusTagType(status) {
      switch (status) {
        case 'success':
          return 'success'
        case 'pending':
          return 'warning'
        case 'failed':
          return 'danger'
        default:
          return 'info'
      }
    },
    viewDetails(row) {
      this.$message.info(`查看交易详情：${row.id}`)
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
.transaction-records {
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

.type-select,
.date-picker {
  width: 180px;
}

.transaction-list {
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
  
  .type-select,
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
  
  .type-select,
  .date-picker {
    width: 100%;
  }
  
  .pagination {
    justify-content: center;
  }
}
</style>

import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import DataOverview from '../views/DataOverview.vue'
import SalesAnalysis from '../views/SalesAnalysis.vue'
import ProductCategory from '../views/ProductCategory.vue'
import RegionAnalysis from '../views/RegionAnalysis.vue'
import HotProducts from '../views/HotProducts.vue'
import SalesPrediction from '../views/SalesPrediction.vue'
import PaymentAnalysis from '../views/PaymentAnalysis.vue'
import CustomerAnalysis from '../views/CustomerAnalysis.vue'
import Admin from '../views/Admin.vue'
import About from '../views/About.vue'
import Demo from '../views/Demo.vue'
import Contact from '../views/Contact.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/data-overview',
    name: 'DataOverview',
    component: DataOverview
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/demo',
    name: 'Demo',
    component: Demo
  },
  {
    path: '/contact',
    name: 'Contact',
    component: Contact
  },
  {
    path: '/sales-analysis',
    name: 'SalesAnalysis',
    component: SalesAnalysis
  },
  {
    path: '/product-category',
    name: 'ProductCategory',
    component: ProductCategory
  },
  {
    path: '/region-analysis',
    name: 'RegionAnalysis',
    component: RegionAnalysis
  },
  {
    path: '/hot-products',
    name: 'HotProducts',
    component: HotProducts
  },
  {
    path: '/sales-prediction',
    name: 'SalesPrediction',
    component: SalesPrediction
  },
  {
    path: '/payment-analysis',
    name: 'PaymentAnalysis',
    component: PaymentAnalysis
  },
  {
    path: '/customer-analysis',
    name: 'CustomerAnalysis',
    component: CustomerAnalysis
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 不需要登录的页面
  const publicPages = ['/', '/about', '/demo', '/contact', '/data-overview', '/sales-analysis', '/product-category', '/region-analysis', '/hot-products', '/sales-prediction', '/payment-analysis', '/customer-analysis']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')
  
  // 如果需要登录但未登录，重定向到首页
  if (authRequired && !loggedIn) {
    next('/')
  } else {
    next()
  }
})

export default router

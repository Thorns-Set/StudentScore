import { createApp } from 'vue'
import App from './App.vue'


// 引入elementplus
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'
// 引入路由
import router from './router/'
//引入jQuery
import $ from 'jquery'
//引入echarts
import * as echarts from 'echarts'

// createApp(App)
//     .use(ElementUI)
//     .use(router)
//     .mount('#app')

const app = createApp(App)

// 去掉Vue warn警告
app.config.warnHandler = () => null;
//全局挂载echarts
app.config.globalProperties.$echarts = echarts

app.use(ElementUI)
   .use(router)
   .mount("#app")

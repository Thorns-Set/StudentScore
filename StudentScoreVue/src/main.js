import { createApp } from 'vue'
import App from './App.vue'


// 引入elementplus
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'
// 引入路由
import router from './router/'

createApp(App)
    .use(ElementUI)
    .use(router)
    .mount('#app')

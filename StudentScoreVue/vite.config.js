import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src')
    }
  },
  server: {
    proxy: {
      //这里是通过请求/api 来转发到 http://127.0.0.1:88/
      "/api":{
        target:'http://127.0.0.1:8669/',
        changeOrigin:true,
        cookiePathRewrite:{
          '^/api':''
        }
      }
    }
  }
})

import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import Router from './router/index.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import request from './utils/request'
import "./assets/css/global.css"

import UndrawUi from 'undraw-ui'
import 'undraw-ui/dist/style.css'

import { ElMessage } from 'element-plus'

import Directive from './directive/index'

//挂载axios
// Vue.prototype.$http=request
// axios.defaults.baseURL="http://localhost:8080/api"
// Vue.config.productionTip = false


const app=createApp(App)
app.config.globalProperties.$http=request
app.use(Router).use(ElementPlus).use(UndrawUi).use(Directive).mount('#app')



//全局注册Element Icon
for (let iconName in ElementPlusIconsVue) {
    app.component(iconName, ElementPlusIconsVue[iconName])
}

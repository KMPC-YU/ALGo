import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store'
import './style.css'

// bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap-icons/font/bootstrap-icons.css'

createApp(App).use(router).use(store).mount('#app')

import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store'
import './style.css'
import '@fortawesome/fontawesome-free/css/all.css'
import { BootstrapIconsPlugin } from "bootstrap-icons-vue";
import VueDOMPurifyHTML from 'vue-dompurify-html'

// bootstrap
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap-icons/font/bootstrap-icons.css'

createApp(App).use(router).use(store).use(BootstrapIconsPlugin).use(VueDOMPurifyHTML).mount('#app')

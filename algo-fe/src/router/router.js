import { createWebHistory, createRouter } from "vue-router";
import Main from '@views/Main.vue'
import Login from '@views/Login.vue'
import Signup from '@views/Signup.vue'
import ErrorPage from '@views/ErrorPage.vue'

const router = createRouter({
    history : createWebHistory(),
    routes : [
        { path : "/", name : "Main", component : Main },
        { path : "/:pathMatch(.*)", name : "not-found", component : ErrorPage},
        { path : "/login", name : 'Login', component : Login},
        { path : "/signup", name : "Signup", component: Signup},
    ]
});

export default router;
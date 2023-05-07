import { createWebHistory, createRouter } from "vue-router"
import Main from "@views/main/Main.vue"
import Login from '@views/Login.vue'
import FindPassword from '@views/findPassword/index.vue'

const router = createRouter({
    history : createWebHistory(),
    routes : [
        { path : "/", name : "Main", component : Main },
        { path : "/:pathMatch(.*)", name : "not-found", component : () => import('@compo/common/ErrorPage.vue') },
        { path : "/login", name : "Login", component : Login },
        { path : "/signup", name : "SignUp", component: () => import('@views/signup/SignUp.vue') },
        { path: "/findpw", name: "FindPassword", component: FindPassword/*, beforeEnter: isLogin()*/ },
    ]
});

router.beforeEach((to, from, next) => {
    window.scrollTo(0, 0);
    next();
});

export default router;
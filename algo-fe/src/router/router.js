import { createWebHistory, createRouter } from "vue-router"
import Main from "@views/main/Main.vue"
import Login from '@views/Login.vue'
import SignUp from "@views/signup/SignUp.vue"
import ErrorPage from "@compo/common/ErrorPage.vue"
import FindPassword from '@views/findPassword/index.vue'

const router = createRouter({
    history : createWebHistory(),
    routes : [
        { path : "/", name : "Main", component : Main },
        { path : "/:pathMatch(.*)", name : "not-found", component : ErrorPage },
        { path : "/login", name : "Login", component : Login },
        { path : "/signup", name : "Signup", component: SignUp },
        { path: "/findpw", name: "FindPassword", component: FindPassword/*, beforeEnter: isLogin()*/ },
    ]
});

router.beforeEach((to, from, next) => {
    window.scrollTo(0, 0);
    next();
});

export default router;
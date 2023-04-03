import { createWebHistory, createRouter } from "vue-router";
import Main from "../components/views/main/Main.vue";
import Login from "../components/views/Login.vue";
import Signup from "../components/views/signup/SignUp.vue";
import ErrorPage from "../components/common/ErrorPage.vue";

const router = createRouter({
    history: createWebHistory(), // 웹 서버 설정
    routes: [
        { path: "/", name: "Main", component: Main },
        { path: "/:pathMatch(.*)", name: "not-found", component: ErrorPage },
        { path: "/login", name: "Login", component: Login },
        { path: "/signup", name: "Signup", component: Signup },
    ],
});

export default router;
import { createWebHistory, createRouter } from "vue-router"
import Main from "@views/main/Main.vue"
import Login from '@views/Login.vue'
import FindPassword from '@views/findPassword/index.vue'
import PostList from '@views/Post/PostList.vue'

const router = createRouter({
    history : createWebHistory(),
    routes : [
        { path : "/", name : "Main", component : Main },
        { path : "/:pathMatch(.*)", name : "not-found", component : () => import('@compo/common/ErrorPage.vue') },
        { path : "/login", name : "Login", component : Login },
        { path : "/signup", name : "SignUp", component: () => import('@views/signup/SignUp.vue') },
        { path: "/findpw", name: "FindPassword", component: FindPassword/*, beforeEnter: isLogin()*/ },
        { path : "/admin", name : "Admin", component: () => import('@views/AdminDashboard/ManageBoards.vue') },
        { path : "/boards/:board_id", name: "PostList", component: PostList },
        { path : "/boards/:board_id/posts/:post_id", name: "PostDetail", component: () => import('@views/Post/PostDetail.vue') },
        { path : "/boards/:board_id/write", name: "PostWrite", component: () => import('@views/Post/PostWrite.vue') },
        { path : "/boards/:board_id/posts/:post_id/modify", name: "PostModify", component: () => import('@views/Post/PostWrite.vue') },
        { path: '/foods', name: 'FoodList', component: () => import('@views/food/FoodList.vue') },
        { path: '/foods/:food_id', name: 'FoodDetail', component: () => import('@views/food/FoodDetail.vue') },
        { path: '/foods/write', name: 'FoodWrite', component: () => import('@views/food/FoodWrite.vue')/*, props: true*/ },
    ]
});

router.beforeEach((to, from, next) => {
    next();
});

export default router;
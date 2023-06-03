import { createWebHistory, createRouter } from 'vue-router'

const router = createRouter({
    history : createWebHistory(),
    routes : [
        { path : '/', name : 'Main', component : () => import('@views/main/Main.vue') },
        { path : '/:pathMatch(.*)', name : 'not-found', component : () => import('@compo/common/ErrorPage.vue') },
        { path : '/login', name : 'Login', component : () => import('@views/Login.vue') },
        { path : '/signup', name : 'SignUp', component: () => import('@views/signup/SignUp.vue') },
        { path : '/findpw', name: 'FindPassword', component: () => import('@views/findPassword/index.vue')/*, beforeEnter: isLogin()*/ },
        { path : '/admin', name : 'Admin', component: () => import('@views/AdminDashboard/ManageBoards.vue') },
        { path : '/boards/:board_id', name: 'PostList', component: () => import('@views/Post/PostList.vue') },
        { path : '/boards/:board_id/posts/:post_id', name: 'PostDetail', component: () => import('@views/Post/PostDetail.vue') },
        { path : '/boards/:board_id/write', name: 'PostWrite', component: () => import('@views/Post/PostWrite.vue') },
        { path : '/boards/:board_id/posts/:post_id/modify', name: 'PostModify', component: () => import('@views/Post/PostWrite.vue') },
        { path: '/foods', name: 'FoodList', component: () => import('@views/food/FoodList.vue') },
        { path: '/foods/:food_id', name: 'FoodDetail', component: () => import('@views/food/FoodDetail.vue') },
        { path: '/foods/:food_id/modify', name: 'FoodModify', component: () => import('@views/food/FoodWrite.vue') },
        { path: '/foods/write', name: 'FoodWrite', component: () => import('@views/food/FoodWrite.vue')/*, props: true*/ },
        { path: '/profiles/:username', name: 'Profile', component: () => import('@views/Profile.vue') },
        { path: '/recipes/', name: 'RecipeList', component: () => import('@views/recipe/RecipeList.vue') },
        { path: '/recipes/:recipe_id/', name: 'RecipeDetail', component: () => import('@views/recipe/RecipeDetail.vue') },
        { path: '/recipes/:recipe_id/modify', name: 'RecipeModify', component: () => import('@views/recipe/RecipeWrite.vue') },
        { path: '/recipes/write', name: 'RecipeWrite', component: () => import('@views/recipe/RecipeWrite.vue') },
        { path: '/chat', name: 'Chat', component: () => import('@views/Chat.vue') },
        { path: '/point', name: 'Point', component: () => import('@views/Point.vue') },
        { path: '/chatbot', name: 'ChatBot', component: () => import('@views/Chatbot.vue') },
    ]
});

router.beforeEach((to, from, next) => {
    next();
});

export default router;
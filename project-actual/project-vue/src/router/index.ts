import {createRouter, createWebHistory} from "vue-router";

// 1、引入组件（可选）
import HomeView from "../views/HomeView.vue";

// 定义的路由：
const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView,
    },
    {
        path: "/about",
        name: "about",
        // route level code-splitting
        // this generates a separate chunk (About.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import("../views/AboutView.vue"),
    },
    {
        path: "/demo",
        name: "demo",
        component: () => import("../views/demo/DemoView.vue"),
    },
]

// 2、创建 Router
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes,
});

// 3、暴露 Router
export default router;

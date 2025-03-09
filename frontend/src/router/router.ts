import {createRouter, createWebHistory} from "vue-router";
import MainPage from "@/pages/MainPage.vue";
import QuestionsPage from "@/pages/QuestionsPage.vue";
import LoginPage from "@/pages/LoginPage.vue";
import RegisterPage from "@/pages/RegisterPage.vue";
import CreateQuestionPage from "@/pages/CreateQuestionPage.vue";
import NotFoundPage from "@/pages/NotFoundPage.vue";
import ForbiddenPage from "@/pages/ForbiddenPage.vue";

const routes = [
    {
        path: '/',
        component: MainPage
    },
    {
        path: '/questions',
        component: QuestionsPage
    },
    {
        path: '/login',
        component: LoginPage
    },
    {
        path: '/register',
        component: RegisterPage
    },
    {
        path: '/questions/create',
        component: CreateQuestionPage
    },
    {
        path: '/forbidden',
        component: ForbiddenPage
    },
    {
        path: '/:pathMatch(.*)*',
        component: NotFoundPage
    }
];

const router = createRouter({
    routes,
    history: createWebHistory(process.env.BASE_URL),
});

export default router;

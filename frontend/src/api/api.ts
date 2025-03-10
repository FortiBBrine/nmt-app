import axios from "axios";
import {logout} from "@/api/auth/loginApi";
import store from "@/store/store";
import router from "@/router/router";

export const api = axios.create({
    baseURL: 'https://nmt-app.onrender.com/api'
});

api.interceptors.request.use((request) => {

    if (store.getters["auth/isAuth"]) {
        request.headers["Authorization"] = "Bearer " + store.state.auth.token;
    }

    return request;
});

api.interceptors.response.use(async (response) => {
    if (response.statusText === "Unauthorized") {
        logout();
    }

    return response;
}, async (error) => {
    if (axios.isAxiosError(error)) {
        if (error.response !== undefined && error.response.status === 401) {
            logout();
            await router.push("/login");
        }
        if (error.response !== undefined && error.response.status === 403) {
            await router.push("/forbidden");
        }
        // console.log(error.toJSON());
    }
});

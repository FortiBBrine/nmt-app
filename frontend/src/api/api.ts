import axios from "axios";
import {logout} from "@/api/auth/loginApi";
import store from "@/store/store";

export const api = axios.create({
    baseURL: 'http://0.0.0.0:8080/api/',
    withCredentials: true,
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
        }
        console.log(error.toJSON());
    }
})

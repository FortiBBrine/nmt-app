import axios from "axios";
import store from "@/store/store";

export const api = axios.create({
    baseURL: 'http://0.0.0.0:8080/api/',
});

api.interceptors.request.use((request) => {
    const token = (store.state as any).auth.token as string;

    if (token != null) {
        request.headers["Authorization"] = "Bearer " + token;
    }

    return request;
});

api.interceptors.response.use((response) => {
    if (response.statusText === "Unauthorized") {
        logout();
    }

    return response;
}, (error) => {
    if (axios.isAxiosError(error)) {
        if (error.response !== undefined && error.response.status === 401) {
            logout();
        }
        console.log(error.toJSON());
    }
})

export const logout = () => {
    store.commit("auth/setToken", null);
    localStorage.removeItem("token");
};

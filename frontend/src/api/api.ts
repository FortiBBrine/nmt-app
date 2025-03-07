import axios from "axios";
import {logout} from "@/api/auth/loginApi";

export const api = axios.create({
    baseURL: 'http://0.0.0.0:8080/api/',
    withCredentials: true,
});

api.interceptors.request.use((request) => {
    return request;
});

api.interceptors.response.use(async (response) => {
    if (response.statusText === "Unauthorized") {
        await logout();
    }

    return response;
}, async (error) => {
    if (axios.isAxiosError(error)) {
        if (error.response !== undefined && error.response.status === 401) {
            await logout();
        }
        console.log(error.toJSON());
    }
})

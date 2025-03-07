import {api} from "@/api/api";
import store from "@/store/store";

export type LoginResponseDto = {
    result: {
        username: string | undefined,
        password: string | undefined
    }
};

export async function login(username: string, password: string): Promise<LoginResponseDto> {
    const response = await api.post("/auth/login", {
        username: username,
        password: password
    });

    return response.data;
}

export async function isAuthenticated(): Promise<boolean> {
    const response = await api.get("/auth/status");
    console.log("isAuthenticated", response.data.auth);
    return response.data.auth;
}

export async function logout(): Promise<void> {
    const response = await api.post("/auth/logout");
    store.commit("auth/setAuth", false);
}

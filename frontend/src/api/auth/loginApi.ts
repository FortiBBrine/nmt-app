import {api} from "@/api/api";
import {useAuthStore} from "@/store/authModule.ts";

export type LoginResponseDto = {
    token: string | null
    errors: {
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

export function logout() {
    const store = useAuthStore();
    store.clearToken();
}

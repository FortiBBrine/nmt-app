import {api} from "@/api/api";

export type RegisterResponseDto = {
    token: string | null
    errors: {
        name: string | undefined,
        username: string | undefined,
        password: string | undefined,
        email: string | undefined
    }
};

export async function register(
    email: string,
    name: string,
    username: string,
    password: string,
): Promise<RegisterResponseDto> {
    const response = await api.post("/auth/register", {
        name: name,
        username: username,
        email: email,
        password: password,
    });

    return response.data;
}

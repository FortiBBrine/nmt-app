export interface AuthState {
    token: string | null;
}

export const authModule = {
    state: (): AuthState => ({
        token: localStorage.getItem("accessToken") || null,
    }),
    mutations: {
        setToken: (state: AuthState, token: string) => {
            state.token = token;
            localStorage.setItem("accessToken", token);
        },
        clearToken: (state: AuthState) => {
            state.token = null;
            localStorage.removeItem("accessToken");
        }
    },
    getters: {
        isAuth: (state: AuthState) => state.token !== null,
    },
    namespaced: true
};

interface AuthState {
    token: string | null;
}

export const authModule = {
    state: (): AuthState => ({
        token: null,
    }),
    mutations: {
        setToken(state: AuthState, token: string | null) {
            state.token = token;
        }
    },
    namespaced: true
};

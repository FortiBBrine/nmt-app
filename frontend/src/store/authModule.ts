interface AuthState {
    isAuth: boolean;
}

export const authModule = {
    state: (): AuthState => ({
        isAuth: false,
    }),
    mutations: {
        setAuth(state: AuthState, auth: boolean) {
            state.isAuth = auth;
        }
    },
    namespaced: true
};

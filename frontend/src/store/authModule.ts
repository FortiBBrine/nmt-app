import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useAuthStore = defineStore("auth", () => {
    const token = ref<string | null>(localStorage.getItem("accessToken") || null);
    const isAuth = computed(() => !!token.value);

    const setToken = (payload: string) => {
        token.value = payload;
        localStorage.setItem("accessToken", token.value);
    };

    const clearToken = () => {
        token.value = null;
        localStorage.removeItem("accessToken");
    };

    return {
        token,
        isAuth,
        setToken,
        clearToken,
    };
});

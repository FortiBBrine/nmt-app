import {createStore} from "vuex";
import {authModule, AuthState} from "@/store/authModule";

interface RootState {
    auth: AuthState
}

export default createStore<RootState>({
    modules: {
        auth: authModule
    }
})

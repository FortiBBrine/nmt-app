import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router/router";
import PrimeVue from "primevue/config";
import Lara from "@primeuix/themes/lara";
import Button from "primevue/button";
import Dialog from "primevue/dialog";
import InputText from "primevue/inputtext";
import Select from "primevue/select";
import KeyFilter from "primevue/keyfilter";
import {InputNumber, Password, RadioButton, Textarea, Toolbar} from "primevue";
import Editor from "primevue/editor";
import {createPinia} from "pinia";

const pinia = createPinia();
const app = createApp(App);

app
    .use(PrimeVue, {
        theme: {
            preset: Lara
        }
    });

app
    .directive("keyfilter", KeyFilter)
    .component("Button", Button)
    .component("Dialog", Dialog)
    .component("InputText", InputText)
    .component("Select", Select)
    .component("InputNumber", InputNumber)
    .component("Toolbar", Toolbar)
    .component("Password", Password)
    .component("RadioButton", RadioButton)
    .component("Editor", Editor)
    .component("Textarea", Textarea);

app
    .use(router)
    .use(pinia)
    .mount('#app');

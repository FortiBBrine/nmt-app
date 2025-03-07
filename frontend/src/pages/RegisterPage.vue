<template>
  <div>
    <InputText type="text" v-model="name" placeholder="Ім'я користувача" />
    <InputText type="text" v-model="username" placeholder="Нікнейм" />
    <InputText type="email" v-model="email" placeholder="Електронна скринька" />
    <InputText type="password" v-model="password" placeholder="Пароль" />
    <Button type="submit" severity="secondary" label="Зареєструватись" @click="registerButton" />
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {register} from "@/api/auth/registerApi";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {isAuthenticated} from "@/api/auth/loginApi";

const name = ref("");
const username = ref("");
const password = ref("");
const email = ref("");

const store = useStore();
const router = useRouter();

const registerButton = async () => {
  const data = await register(email.value, name.value, username.value, password.value);

  if (await isAuthenticated()) {
    store.commit("auth/setAuth", true);
    await router.push("/");
    return;
  }

};

</script>

<style scoped>

</style>
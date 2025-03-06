<template>
  <div>
    <InputText type="text" v-model="username" placeholder="Нікнейм" />
    <InputText type="password" v-model="password" placeholder="Пароль" />
    <Button type="submit" severity="secondary" label="Увійти" @click="loginButton" />
  </div>
</template>

<script setup lang="ts">

import {ref} from "vue";
import {login} from "@/api/auth/loginApi";
import {useStore} from "vuex";
import {useRouter} from "vue-router";

const username = ref("");
const password = ref("");

const store = useStore();
const router = useRouter();

const loginButton = async () => {
  const data = await login(username.value, password.value);

  if (data.token != null) {
    store.commit("auth/setToken", data.token);
    localStorage.setItem("token", data.token);
    await router.push("/");
    return;
  }

};

</script>

<style scoped>

</style>
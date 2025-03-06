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

const name = ref("");
const username = ref("");
const password = ref("");
const email = ref("");

const store = useStore();
const router = useRouter();

const registerButton = async () => {
  const data = await register(email.value, name.value, username.value, password.value);

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
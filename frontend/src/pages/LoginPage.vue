<template>
  <div class="flex items-center justify-center flex-1">
    <div class="size-min flex flex-col gap-2">
      <InputText type="text" v-keyfilter.alphanum id="username" name="username" autocomplete="username" v-model="username" placeholder="Ім'я користувача" />
      <template v-if="!!usernameErrors">
        <span>{{ usernameErrors }}</span>
      </template>
      <Password v-model="password" toggleMask placeholder="Пароль" />
      <Button type="submit" severity="secondary" label="Увійти" @click="loginButton" />
    </div>
  </div>
</template>

<script setup lang="ts">

import {ref} from "vue";
import {login} from "@/api/auth/loginApi";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/store/authModule.ts";

const username = ref("");
const password = ref("");

const usernameErrors = ref<string | undefined>(undefined);

const store = useAuthStore();
const router = useRouter();

const loginButton = async () => {
  const data = await login(username.value, password.value);

  if (data.token !== null) {
    store.setToken(data.token);
    await router.push("/");
    return;
  }

  usernameErrors.value = data.errors.username;

};

</script>

<style scoped>

</style>
<template>
  <div class="flex items-center justify-center flex-1">
    <div class="size-min flex flex-col gap-2">
      <InputText type="text" v-model="name" placeholder="Ім'я" maxlength="32" />
      <template v-if="!!nameErrors">
        <span>{{ nameErrors }}</span>
      </template>
      <InputText type="text" v-keyfilter.alphanum id="username" name="username" autocomplete="username" v-model="username" placeholder="Ім'я користувача" maxlength="20" />
      <template v-if="!!usernameErrors">
        <span>{{ usernameErrors }}</span>
      </template>
      <InputText type="email" id="email" name="email" autocomplete="email" v-model="email" placeholder="Електронна скринька" />
      <template v-if="!!emailErrors">
        <span>{{ emailErrors }}</span>
      </template>
      <Password v-model="password" toggleMask placeholder="Пароль" />
      <template v-if="!!passwordErrors">
        <span>{{ passwordErrors }}</span>
      </template>
      <Button type="submit" severity="secondary" label="Зареєструватись" @click="registerButton" />

    </div>
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

const nameErrors = ref<string | undefined>(undefined);
const usernameErrors = ref<string | undefined>(undefined);
const emailErrors = ref<string | undefined>(undefined);
const passwordErrors = ref<string | undefined>(undefined);

const store = useStore();
const router = useRouter();

const registerButton = async () => {
  const data = await register(email.value, name.value, username.value, password.value);

  if (data.token !== null) {
    store.commit("auth/setToken", data.token);
    await router.push("/");
    return;
  }

  nameErrors.value = data.errors.name;
  usernameErrors.value = data.errors.username;
  emailErrors.value = data.errors.email;
  passwordErrors.value = data.errors.password;

};

</script>

<style scoped>

</style>
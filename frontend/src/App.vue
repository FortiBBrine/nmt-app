<template>
  <div>
    <h1 class="text-4xl font-bold">Верхня частина сайту</h1>

    <router-link to="/">Головна сторінка</router-link>

    <div v-if="!isAuth">
      <router-link to="/login">Сторінка авторизації</router-link>
      <br>
      <router-link to="/register">Сторінка реєстрації</router-link>
    </div>
    <div v-else>
      <p @click="logout">Вийти з акаунту</p>
    </div>

    <router-view />
  </div>
</template>

<script setup lang="ts">

import {computed, onMounted} from "vue";
import {useStore} from "vuex";
import {isAuthenticated, logout} from "@/api/auth/loginApi";

const store = useStore();

const isAuth = computed(() => store.state.auth.isAuth);

onMounted(async () => {
  store.commit("auth/setAuth", await isAuthenticated());
});

</script>

<style scoped>

</style>
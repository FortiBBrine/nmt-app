<template>
  <div>
    <h1 class="text-4xl font-bold">Верхня частина сайту</h1>

    <router-link to="/">Головна сторінка</router-link>

    <div v-if="token == null">
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
import {logout} from "@/api/api";

const store = useStore();

const token = computed(() => store.state.auth.token);

onMounted(async () => {
  store.commit("auth/setToken", localStorage.getItem("token"));

});

</script>

<style scoped>

</style>
<template>
  <div class="h-screen flex flex-col">

    <div>
      <Toolbar>
        <template #start>
          <div class="flex items-center gap-2">
            <router-link to="/"><p class="font-bold">NmtApp</p></router-link>
          </div>
        </template>
        <template #end>
          <div class="flex items-center gap-2">
            <template v-if="!store.isAuth">
              <router-link to="/login"><Button label="Увійти" /></router-link>
              <router-link to="/register"><Button label="Зареєструватись" /></router-link>
            </template>
            <template v-else>
              <Button @click="logout" label="Вийти з акаунту" />
            </template>
          </div>
        </template>
      </Toolbar>
    </div>

    <router-view />
  </div>
</template>

<script setup lang="ts">

import {logout} from "@/api/auth/loginApi";
import {useAuthStore} from "@/store/authModule.ts";

const store = useAuthStore();
</script>

<style>
@import "tailwindcss";

</style>
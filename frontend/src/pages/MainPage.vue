<template>
  <div>
    <div class="flex flex-col items-start gap-2 m-10">
      <p>Головна сторінка</p>
      <Button label="Згенерувати" @click="generateDialogVisible = true" />
      <Button label="Створити предмет" @click="createStudyDialogVisible = true" />
      <router-link to="/questions/create"><Button label="Створити питання" /></router-link>
    </div>

    <GenerateDialog v-model="generateDialogVisible" />
    <CreateStudyDialog v-model="createStudyDialogVisible" />

  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import GenerateDialog from "@/components/GenerateDialog.vue";
import CreateStudyDialog from "@/components/CreateStudyDialog.vue";

const generateDialogVisible = ref(false);
const createStudyDialogVisible = ref(false);

</script>

<style scoped>

</style>
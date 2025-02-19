<template>
  <div>
    <p>Головна сторінка</p>
    <Button label="Згенерувати" @click="dialogVisible = true" />

    <Dialog v-model:visible="dialogVisible" modal header="Налаштуйте ваш тест" :style="{ width: '25rem' }">
      <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про тест</span>
      <div class="flex items-center gap-4 mb-4">
        <label for="study" class="font-semibold w-24">Предмет</label>
        <Select v-model="selectedSubject" :options="studies" optionLabel="name" placeholder="Оберіть предмет" class="flex-auto" />
      </div>
      <div class="flex items-center gap-4 mb-8">
        <label for="count" class="font-semibold w-24">Кількість запитань</label>
        <InputNumber v-model="count" input-id="count" :min="0" :max="32" class="flex-auto" />
      </div>
      <div class="flex justify-end gap-2">
        <Button type="button" label="Відмінити" severity="secondary" @click="dialogVisible = false"></Button>
        <Button type="button" label="Згенерувати" @click="generate"></Button>
      </div>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";
import {useRouter} from "vue-router";

const dialogVisible = ref(false);
const studies = ref([
  { name: "Історія України" },
  { name: "Математика" }
]);

const selectedSubject = ref<{ name: string }>();
const count = ref(0);

const router = useRouter();

function generate() {
  if (selectedSubject.value === undefined) return;

  dialogVisible.value = false;

  router.push({
    path: "/test",
    query: {
      subject: selectedSubject.value.name,
      count: count.value,
    }
  });
}

</script>

<style scoped>

</style>
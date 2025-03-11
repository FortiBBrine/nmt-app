<template>

  <Dialog v-model:visible="model" modal header="Налаштуйте ваш тест" :style="{ width: '25rem' }">
    <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про тест</span>
    <div class="flex items-center gap-4 mb-4">
      <label for="study" class="font-semibold w-24">Предмет</label>
      <Select v-model="subject" :options="studies" optionLabel="name" placeholder="Оберіть предмет" class="flex-auto" />
    </div>

    <div v-for="type in types" :key="type" class="flex items-center gap-4 mb-8">
      <label for="count" class="font-semibold w-24">{{ type }}</label>
      <InputNumber @update:model-value="(value) => count.set(type, value)" input-id="count" :min="0" :max="32" class="flex-auto" />
    </div>
    <div class="flex justify-end gap-2">
      <Button type="button" :disabled="somethingLoadingForDialog" label="Згенерувати" @click="generate"></Button>
    </div>
  </Dialog>
</template>

<script setup lang="ts">

import {computed, onMounted, ref, watch} from "vue";
import {allStudies, getTypes, type StudyDto} from "@/api/auth/studiesApi";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/store/authModule.ts";

const somethingLoadingForDialog = ref<boolean>(false);
const studies = ref<StudyDto[]>([]);
const subject = ref<StudyDto>();
const types = ref<string[]>([]);
const count = new Map<string, number>();

const router = useRouter();
const store = useAuthStore();

const props = defineProps<{ modelValue: boolean }>();
const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void;
}>();
const model = computed({
  get: () => props.modelValue,
  set: (value: boolean) => emit('update:modelValue', value),
});

const generate = () => {
  if (subject.value === undefined) return;

  model.value = false;

  router.push({
    path: "/questions",
    query: {
      subjectId: subject.value.id,
      subject: subject.value.name,
      count: JSON.stringify(count)
    }
  });

};

onMounted(async () => {
  if (store.isAuth) {
    somethingLoadingForDialog.value = true;
    const apiStudies = await allStudies();

    studies.value = [...apiStudies];
    somethingLoadingForDialog.value = false;
  }
});

watch(subject, async (newSubject) => {
  if (!!newSubject) {
    somethingLoadingForDialog.value = true;
    types.value = await getTypes(newSubject.id);
    count.clear();
    somethingLoadingForDialog.value = false;
  }
});

</script>

<style scoped>

</style>
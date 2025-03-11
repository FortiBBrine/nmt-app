<template>

  <Dialog v-model:visible="model" modal header="Налаштуйте ваш предмет" :style="{ width: '25rem' }">
    <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про предмет</span>
    <div class="flex items-center gap-4 mb-4">
      <label for="study" class="font-semibold w-24">Назва</label>
      <InputText v-model="subjectName" class="flex-auto" />
    </div>

    <div class="flex justify-end gap-2">
      <Button type="button" label="Створити" @click="createSubject"></Button>
    </div>
  </Dialog>
</template>

<script setup lang="ts">
import {computed, ref} from "vue";
import {createStudy} from "@/api/auth/studiesApi";

const props = defineProps<{ modelValue: boolean }>();
const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void;
}>();
const model = computed({
  get: () => props.modelValue,
  set: (value: boolean) => emit('update:modelValue', value),
});

const subjectName = ref<string>("");

const createSubject = async () => {
  model.value = false;

  if (subjectName.value !== "") {
    await createStudy({
      id: 0,
      name: subjectName.value
    })
  }
};

</script>

<style scoped>

</style>
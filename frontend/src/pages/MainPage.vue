<template>
  <div>
    <div class="flex flex-col items-start gap-2 m-10">
      <p>Головна сторінка</p>
      <Button label="Згенерувати" @click="generateDialogVisible = true" />
      <Button label="Створити предмет" @click="createStudyDialogVisible = true" />
      <Button label="Створити питання" @click="createQuestionDialogVisible = true" />
    </div>

    <Dialog v-model:visible="generateDialogVisible" modal header="Налаштуйте ваш тест" :style="{ width: '25rem' }">
      <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про тест</span>
      <div class="flex items-center gap-4 mb-4">
        <label for="study" class="font-semibold w-24">Предмет</label>
        <Select v-model="subject" :options="studies" optionLabel="name" placeholder="Оберіть предмет" class="flex-auto" />
      </div>

      <template v-for="type in types" :key="type">
        <div class="flex items-center gap-4 mb-8">
          <label for="count" class="font-semibold w-24">{{ type }}</label>
          <InputNumber v-model="count[type]" input-id="count" :min="0" :max="32" class="flex-auto" />
        </div>
      </template>
      <div class="flex justify-end gap-2">
        <Button type="button" :disabled="somethingLoadingForDialog" label="Згенерувати" @click="generate"></Button>
      </div>
    </Dialog>

    <Dialog v-model:visible="createStudyDialogVisible" modal header="Налаштуйте ваш предмет" :style="{ width: '25rem' }">
      <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про предмет</span>
      <div class="flex items-center gap-4 mb-4">
        <label for="study" class="font-semibold w-24">Назва</label>
        <InputText v-model="subjectName" class="flex-auto" />
<!--        <Select v-model="subject" :options="studies" optionLabel="name" placeholder="Оберіть предмет" class="flex-auto" />-->
      </div>

      <div class="flex justify-end gap-2">
        <Button type="button" label="Створити" @click="createSubject"></Button>
      </div>
    </Dialog>

    <Dialog v-model:visible="createQuestionDialogVisible" modal header="Налаштуйте ваше питання" :style="{ width: '25rem' }">
      <span class="text-surface-500 dark:text-surface-400 block mb-8">Вкажіть інформацію про питання</span>
      <div class="flex items-center gap-4 mb-4">
        <label for="study" class="font-semibold w-24">Опис</label>
        <InputText v-model="subjectName" class="flex-auto" />
      </div>

      <div class="flex items-center gap-4 mb-4">
        <label for="study" class="font-semibold w-24">Тип</label>
        <InputText v-model="subjectName" class="flex-auto" />
      </div>

      <div class="flex justify-end gap-2">
        <Button type="button" label="Створити" @click="createSubject"></Button>
      </div>
    </Dialog>

  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref, watch} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {allStudies, createStudy, getTypes, StudyDto} from "@/api/auth/studiesApi";

const generateDialogVisible = ref(false);
const createStudyDialogVisible = ref(false);
const createQuestionDialogVisible = ref(false);

const studies = ref<StudyDto[]>([]);
const subject = ref<StudyDto>();
const types = ref<string[]>([]);
const somethingLoadingForDialog = ref<boolean>(false);
const count = new Map<string, number>();

const subjectName = ref<string>("");

const router = useRouter();
const store = useStore();
const isAuth = computed(() => store.getters["auth/isAuth"]);

onMounted(async () => {
  if (isAuth) {
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

const generate = () => {
  if (subject.value === undefined) return;

  generateDialogVisible.value = false;

  router.push({
    path: "/questions",
    query: {
      subjectId: subject.value.id,
      subject: subject.value.name,
      count: JSON.stringify(count)
    }
  });

}

const createSubject = async () => {
  createStudyDialogVisible.value = false;

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
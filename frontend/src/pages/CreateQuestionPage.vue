<template>
  <div class="flex-1 mx-20 my-10 flex flex-col items-center">
    <div class="flex flex-col gap-2">
      <p class="text-2xl font-bold">Створіть тест</p>

      <Editor v-model="description" editorStyle="height: 320px"  />
      <Textarea v-model="description" rows="5" cols="30" />
      <Select v-model="subject" option-label="name" :options="studies" placeholder="Предмет" />

      <div class="flex flex-col gap-4">
        <div class="flex items-center gap-2 border p-4" v-for="(answer, answerIndex) in answers" :key="answer">
          <RadioButton v-model="correctAnswer" :value="'answer' + answerIndex" />
          <label :for="'answer' + answerIndex">{{ answer }}</label>
        </div>
      </div>

      <div class="flex gap-2">
        <InputText v-model="currentAnswer" class="flex-1" type="text" />
        <Button label="Додати варіант відповіді" severity="secondary" @click="answers = [...answers, currentAnswer]" />
      </div>

      <InputText placeholder="Тип запитання" type="text" v-model="type" />

      <Button class="mb-5" label="Створити" severity="secondary" @click="create" />
    </div>
  </div>
</template>

<script setup lang="ts">

import {onMounted, ref} from "vue";
import {allStudies, type StudyDto} from "@/api/auth/studiesApi";
import {createQuestion} from "@/api/auth/questionsApi";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/store/authModule.ts";

const description = ref();
const studies = ref<StudyDto[]>([]);
const subject = ref<StudyDto>();
const type = ref<string>("");

const answers = ref<string[]>([]);
const currentAnswer = ref("");
const correctAnswer = ref<number>();

const router = useRouter();
const store = useAuthStore();

onMounted(async () => {
  if (store.isAuth) {
    const apiStudies = await allStudies();

    studies.value = [...apiStudies];
  }
});

const create = async () => {

  if (!!correctAnswer.value && answers.value.length > 0 && !!type.value && !!subject.value) {

    await router.push("/");
    await createQuestion(
        subject.value.id,
        type.value,
        description.value,
        answers.value,
        [correctAnswer.value]
    );

  }
};

</script>

<style scoped>

</style>
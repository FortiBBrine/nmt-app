<template>
  <div class="mx-20 my-10 flex-1 flex flex-col">
    <p class="text-center font-bold text-3xl">{{ route.query.subject }} ({{ route.query.subjectId }})</p>
<!--    <p>Кількість питань: {{ route.query.count }}</p>-->

    <div v-for="(question, index) in questions" :key="question.id">
      <hr>
      <div class="mx-5">

        <p class="text-2xl mt-5">Завдання {{ index + 1 }}</p>
        <div v-html="question.description"></div>
        <div class="flex flex-col gap-4">
          <div class="flex items-center gap-2 border p-4" v-for="(answer, answerIndex) in question.answers" :key="answer">
            <RadioButton v-model="answers[index]" :value="'answer' + answerIndex" />
            <label :for="'answer' + answerIndex">{{ answer }}</label>
          </div>
        </div>

      </div>
    </div>

    <Button v-if="questions.length != 0" class="ml-5 my-5 self-start" label="Завершити тест" @click="check" />
    <div v-if="!!result" class="ml-5 my-5 border p-10">
      <p class="self-start">Ваш тестовий бал: <strong>{{ result }}</strong> можливих</p>
      <p>Ваш рейтинговий бал: <strong>не склав</strong> з 200 можливих</p>
    </div>

  </div>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {createTest, type QuestionDto} from "@/api/auth/questionsApi";
import {useAuthStore} from "@/store/authModule.ts";

const route = useRoute();

const store = useAuthStore();
const router = useRouter();

const questions = ref<QuestionDto[]>([]);
const answers = ref<(number | null)[]>([]);
const result = ref<string | null>(null);

onMounted(async () => {
  if (!store.isAuth) {
    await router.push("/login");
    return;
  }

  const subjectId = route.query.subjectId ? Number(route.query.subjectId) : null;
  const count = route.query.count as string;

  if (!!subjectId && !!count) {
    questions.value = await createTest(subjectId, count);
    answers.value = Array.from({ length: questions.value.length }, () => null);
  }
});

const check = async () => {
  const maxResult = questions.value.length;
  let currentResult = 0;

  for (let index = 0; index < questions.value.length; index++) {
    const question = questions.value[index];
    const answer = answers.value[index];

    if (answer === question.trueAnswers[0]) {
      currentResult++;
    }

    result.value = currentResult + "/" + maxResult;

  }
};

const globalCLick = (event: PointerEvent) => {
  if (!!result.value) {
    event.preventDefault()
  }
};

</script>

<style scoped>

</style>
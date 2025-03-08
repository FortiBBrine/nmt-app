<template>
  <div>
    <p>Інша сторінка з переданою інформацією і тестом</p>
    <p>Предмет: {{ route.query.subject }} ({{ route.query.subjectId }})</p>
    <p>Кількість питань: {{ route.query.count }}</p>

    <p>Згенерований тест:</p>
    <div v-for="question in questions" :key="question.id">
      <p><strong>Тема:</strong> {{ question.study }}</p>
      <p><strong>Опис:</strong> {{ question.description }}</p>
      <p><strong>Відповіді:</strong></p>
      <ul>
        <li v-for="answer in question.answers" :key="answer">{{ answer }}</li>
      </ul>
      <p><strong>Правильні відповіді:</strong> {{ question.trueAnswers.join(', ') }}</p>
      <p><strong>Тип:</strong> {{ question.type }}</p>
      <hr />
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
import {computed, onMounted, ref} from "vue";
import {createTest, QuestionDto} from "@/api/auth/questionsApi";

const route = useRoute();

const store = useStore();
const router = useRouter();
const isAuth = computed(() => store.getters["auth/isAuth"]);

const questions = ref<QuestionDto[]>([]);

onMounted(async () => {
  if (!isAuth.value) {
    await router.push("/login");
    return;
  }

  questions.value = await createTest(route.query.subjectId, route.query.count);
});

</script>

<style scoped>

</style>
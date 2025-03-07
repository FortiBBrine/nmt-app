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

    <div v-show="isAuth">
      <p>Всі можливі тести:</p>
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

  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {allQuestions, QuestionDto} from "@/api/auth/questionsApi";

const dialogVisible = ref(false);
const studies = ref([
  { name: "Історія України" },
  { name: "Математика" }
]);

const store = useStore();

const selectedSubject = ref<{ name: string }>();
const count = ref(0);

const isAuth = computed(() => store.state.auth.isAuth);

const router = useRouter();
const questions = ref<QuestionDto[]>([]);

onMounted(async () => {
  if (isAuth.value) {
    const someQuestions = await allQuestions();
    questions.value = [...someQuestions];
  }
});

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
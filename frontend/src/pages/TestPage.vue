<template>
  <div>
    <p>Інша сторінка з переданою інформацією і тестом</p>
    <p>Предмет: {{ route.query.subject }}</p>
    <p>Кількість питань: {{ route.query.count }}</p>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";

const route = useRoute();

</script>

<style scoped>

</style>
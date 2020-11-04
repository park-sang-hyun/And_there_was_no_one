<template>
  <div class="app">
     <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
};
</script>

<style>
/* 일단 테스트용 임시방편 */
  /* .app{
    background-color: black;
    width: 100%;
    height: 1000px;
  } */
</style>
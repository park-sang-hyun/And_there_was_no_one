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

<style lang="scss" scoped>
/* 일단 테스트용 임시방편 */
  .app{
    background-color: rgba(0, 0, 0, 0.7);
    width:100%;
  }
</style>
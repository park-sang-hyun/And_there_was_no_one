<template>
  <div>
    <h2>프로필</h2>
    <div class="wrap">
      <!-- 프로필 이미지 불러오기 -->
      <div class="box" style="background: #BDBDBD;">
        <img class="profile" src="https://pbs.twimg.com/profile_images/1184827741884973057/V0F3blnl.jpg">
      </div>
      <!-- 유저 정보 불러오기  -->
      <div class="info">
        <p>nickname: {{ nickname }}</p>
        <p>score: {{ score }}lv</p> 
      </div>
    </div>
  </div>
</template>

<script>
import http from "../../util/http-common.js";

export default {
  data: () => {
    return {
      nickname: "Hello Anonymous User",
      score: 999,
    };
  },
  created() {
    this.getProfile();
  },
  methods: {
    getProfile() {
      //django 서버에서 유저 프로필 가져오기


      // http
      // .get("accoounts/read/")
      // .then((res) => {
      //   // 데이터 받아와서 아래 내용 수정하기 
      //   this.nickname = res.data.nickname
      //   this.score = res.data.score
      // })


      console.log("Enter getProfile");
    }
  },
}
</script>

<style scoped>
.box {
    width: 250px;
    height: 250px; 
    border-radius: 70%;
    overflow: hidden;
    margin:auto;
}
.profile {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.info {
  background-color: hotpink;
  margin: 20px;
  padding: 20px;
}
.wrap {

}
</style>
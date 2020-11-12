<template>
  <div>
    
    <div class="profile-wrapper">
      프로필

      <div class="bell" >
        <button class="notification" @click="bellclick">
          <img class="img" src="../../assets/images/bell.svg">
          <span v-if="alarmCnt" class="notification--num">{{ alarmCnt }}</span>
        </button>
      </div>

      

      <!-- 프로필 이미지 불러오기 -->
      <div class="box" style="background: #BDBDBD;">
        <img class="profile" src="https://pbs.twimg.com/profile_images/1184827741884973057/V0F3blnl.jpg">
      </div>
      <!-- 유저 정보 불러오기  -->
      <div class="info">
        <p>nickname: {{ nickname }}</p>
        <p>score: {{ score }}lv</p> 
      </div>
      <button class="button" @click="logout">게임 종료</button>
    </div>
  </div>
</template>

<script>
import http from "../../util/http-common.js";
import httpLobby from "../../util/http-lobby.js";

export default {
  data: () => {
    return {
      nickname: "Hello Anonymous User",
      score: 999,
      alarmCnt: 0,
    };
  },
  created() {
    this.getProfile();
    this.getAlarm();
  },
  methods: {
    getProfile() {
      //django 서버에서 유저 프로필 가져오기


      http
      .get("accounts/read/" + sessionStorage.getItem("id")+"/")
      .then((res) => {
        this.nickname = res.data.nickname
        this.score = res.data.score
      })


      console.log("Enter getProfile");
    },

    logout() {
      this.$emit('logout', 'logout');
    },

    getAlarm() {
      // 친구 알람 개수 받아오기 
      httpLobby
      .get("alarm/count/" + sessionStorage.getItem('id') + "/" )
      .then((res) => {
        console.log(res.data)
        this.alarmCnt = res.data;
      })
    },

    bellclick() {
      this.$emit('bell', 'bell');
    },
  },
}
</script>

<style lang="scss" scoped>
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
  /* background-color: rgba(255, 255, 255, 0.6); */
  padding: 20px;
}
.profile-wrapper {
  background-color: #eceef155;
  border-radius: 20px;
  margin: 20px;
  padding-top: 20px;
  padding-left: 20px;
  padding-right: 20px;
  
}
.button {
    border: none;
    color: #FFF;
    background: #42b983;
    appearance: none;
    font: inherit;
    border-radius: .3em;
    cursor: pointer;
    padding: 5px 10px;
    margin-bottom: 10px;
  }



.img {
  width: 100%;
}

.notification {
  position: relative;
  width: 20%;
  border: none;
  border-radius: 50px;
  background: none;
}
.notification--num {
  position: absolute;
  top: 0%;
  left: 60%;
  font-size: 1rem;
  border-radius: 50%;
  width: 1.75em;
  height: 1.75em;
  background-color: #FF4C13;
  color: #FFFFFF;
  text-align: center;
  line-height: 1.7em;
  animation: notification 3.2s ease;
}
</style>
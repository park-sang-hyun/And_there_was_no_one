<template>
  <div>
    
    <div class="profile-wrapper">
      <div>
        <div class="btn_container">
          <button class="button" style="margin-left:2%;" @click="[logout(), buttonpush2()]">게임 종료</button>
          <button class="notification bell" @click="[bellclick(), buttonpush()]">
            <img class="img" src="../../assets/images/bell.svg">
            <span v-if="alarmCnt" class="notification--num">{{ alarmCnt }}</span>
          </button>
        </div>
          
      </div>

      <!-- 프로필 이미지 불러오기 -->
      <div class="box" style="background: black;">
        <img class="profile" src="../../assets/images/detective.png">
      </div>
      <!-- 유저 정보 불러오기  -->
      <div class="info">
        <h3 style="color: white;">{{ nickname }} </h3>
        <h5>{{rank}} ( score: {{ score }} )</h5> 
      </div>
    </div>
  </div>
</template>

<script>
import http from "../../util/http-common.js";
import httpLobby from "../../util/http-lobby.js";

export default {

  data: () => {
    return {
      nickname: "Hello, Anonymous User",
      score: 0,
      rank:'F',
      alarmCnt: 0,
    };
  },
  created() {
    this.getProfile();
    this.getRank();
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
      .catch((err) => {
          console.log(err);
      })
    },

    logout() {
      this.$emit('logout', 'logout');
    },

    getRank(){
      httpLobby
      .get("user/rank/" + sessionStorage.getItem("id"))
      .then((res) => {
        this.rank = res.data.object;
      })
      .catch((err) => {
          console.log(err);
      })

    },

    getAlarm() {
      // 친구 알람 개수 받아오기 
      httpLobby
      .get("alarm/count/" + sessionStorage.getItem('id') + "/" )
      .then((res) => {
        this.alarmCnt = res.data;
      })
      .catch((err) => {
          console.log(err);
      })
    },



    bellclick() {
      this.$emit('bell', 'bell');
    },

    buttonpush(){
      var bpush = new Audio('https://www.soundjay.com/misc/sounds/wind-chime-1.mp3');
      bpush.play();
    },
    buttonpush2(){
      var bpush = new Audio('https://www.soundjay.com/human/sounds/woman-scream-01.mp3');
      bpush.play();
    },
  },
}
</script>

<style lang="scss" scoped>
.box {
  padding-left: 3px;
    width: 200px;
    height: 200px; 
    border-radius: 70%;
    overflow: hidden;
    margin-left:17%;
    opacity:0.9;
}
.profile {
  margin-top: 10px;
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity:0.9;
}
.info {
  /* background-color: rgba(255, 255, 255, 0.6); */
  padding: 15px;
  color :rgba(255, 254, 254, 0.6);
  font-size:15px;
  text-align: center;
}
.profile-wrapper {
  width: 80%;
  background-color: #aeb0b32f;
  border-radius: 20px;
  margin: 15px;
  padding-top: 20px;
  padding-left: 20px;
  padding-right: 20px;
  height: 90%;
  margin-left: 10%;
}

/* 우측정렬용 컨테이너 */
.btn_container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.button {
    border: none;
    color: #FFF;
    background: rgba(255, 254, 254, 0.205);
    appearance: none;
    font: inherit;
    border-radius: .3em;
    cursor: pointer;
    padding: 5px 10px;
    margin-bottom: 10px;
    opacity:0.9;
    
    &:hover {
    background: rgba(255, 255, 255, 0.493);
  }
}
.img {
  width: 80%;
}
.notification {
  position: relative;
  width: 15%;
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
  color: #ffffff;
  text-align: center;
  line-height: 1.7em;
  animation: notification 3.2s ease;
}
.bell{
  opacity:0.75;

}
</style>
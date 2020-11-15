<template>
  <div class="lobby" >
    <div class="gerstner-2">
      <div class="comp">
        
        <Profile class="profile" @logout="logout" @bell="bell" ref="reloadBell"/>
        <!-- 소켓 props -->
        <Friends class="friends" ref="out" @reload="reload"></Friends>
      </div>
      <div class="comp">
        <Roomlist class="roomlist"/>
        <!-- 소켓 props -->
        <Chat class="chat"></Chat>
      </div>
    </div>

    <!-- <button @click="disconnect" v-if="status === 'connected'">연결끊기</button>
    <button @click="connect" v-if="status === 'disconnected'">연결</button> {{ status }} -->
    <br /><br />

  </div>
</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>

<script>
import Chat from "../../components/lobby/chat.vue";
import Friends from "../../components/lobby/friends.vue";
import Profile from "../../components/lobby/profile.vue";
import Roomlist from "../../components/lobby/roomlist.vue";

const storage = window.sessionStorage;

export default {
  components: {
    Chat,
    Friends,
    Profile,
    Roomlist,
  },

  data() {
    return {
      audioBtn: new Audio(require('../../assets/musics/back1.mp3')),
    }
  },

  created(){
    this.audioBtn.play();
    this.audioBtn.loop = true;
  },
  destroyed(){
    this.audioBtn.pause();
  },
  methods: {

      // 로그아웃 버튼 눌렀을 때
      logout(signal) {
        this.buttonpush();
        this.$refs.out.logout();
      },

      bell(signal) {
        this.buttonpush();
        this.$refs.out.showAlarm(0);
      },

      reload() {
        this.$refs.reloadBell.getAlarm();
      },
      buttonpush(){
        var bpush = new Audio('https://www.soundjay.com/misc/sounds/wind-chime-1.mp3');
        bpush.play();
      },
  }
}


</script>

<style lang="scss" scoped>
  .lobby /deep/ { 
    
    border: none;

    background: url('../../assets/images/background.jpg') no-repeat center center fixed;
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;

    min-height: 941px;
  }

  
  @-webkit-keyframes fadein { /* Safari and Chrome */
      from {
          opacity:0;
      }
      to {
          opacity:1;
      }
  }

  .gerstner-2 {
    -webkit-animation: fadein 1s; /* Safari and Chrome */
    display: grid;
    grid-template-columns: 1fr 2.5fr;
    /* grid-column-gap: 1em; */
    
    //background-color: black;
  }
  .comp {
    display: grid;
    min-width: 300px;
  }
  .profile { 
    /* background-color: #ba68c8;  */
  }
  .friends { 
    /* background-color: #245eaa;  */
  }
  .roomlist{ 
 
  }
  .chat { 
    /* background-color: #77aa24;  */
  }

</style>
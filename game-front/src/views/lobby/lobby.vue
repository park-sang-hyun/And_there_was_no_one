<template>
  <div class="lobby" >
    <h1>Lobby page</h1>

    <!-- <div v-if="status === 'connected'"> -->
      <!-- <div>
      <form @submit.prevent="webSocketSend" action="#">
        <input v-model="message"><button type="submit">메세지 전송</button>
      </form>
            <ul id="logs">
        <li v-for="(log, index) in logs" class="log" :key="index">
                {{ log.event }}: {{ log.data }}
        </li>
            </ul>
      </div> -->
    <!-- </div> -->

    <div class="gerstner-2">
      <div class="comp">
        
        <Profile class="profile" @logout="logout" @bell="bell" />
        <!-- 소켓 props -->
        <!-- 대흠님이 ref 메서드 멀로 해놨을까?그거에 맞춰서 ref 쓰기  -->
        <Friends class="friends" ref="out"></Friends>
      </div>
      <div class="comp">
        <Roomlist class="roomlist"/>
        <!-- 소켓 props -->
        <Chat  class="chat"></Chat>
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

  methods: {

      // 로그아웃 버튼 눌렀을 때
      logout(signal) {
        this.$refs.out.logout();
      },

      bell(signal) {
        this.$refs.out.showAlarm();
      }
  }
}


</script>

<style lang="scss" scoped>
  .lobby /deep/ { 
    border: none;;
  }
  .gerstner-2 {
    display: grid;
    grid-template-columns: 1fr 2.5fr;
    /* grid-column-gap: 1em; */
    background-color: black;
  }
  .comp {
    display: grid;
    grid-template-rows: .8fr .2fr;
    min-width: 300px;
  }
  .profile { 
    background-color: #ba68c8; 
  }
  .friends { 
    background-color: #245eaa; 
  }
  .roomlist{ 
    background-color: #aca002; 
  }
  .chat { 
    background-color: #77aa24; 
  }

</style>
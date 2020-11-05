<template>
  <div class="container">
    <h1>Lobby page</h1>

    <div v-if="status === 'connected'">
      <form @submit.prevent="sendMessage" action="#">
        <input v-model="message"><button type="submit">메세지 전송</button>
      </form>
			<ul id="logs">
        <li v-for="(log, index) in logs" class="log" :key="index">
				{{ log.event }}: {{ log.data }}
        </li>
			</ul>
    </div>

    <div class="gerstner-2">
      <div class="comp">
        <Profile class="profile"/>
        <Friends class="friends"/>
      </div>
      <div class="comp">
        <Roomlist class="roomlist"/>
        <Chat class="chat"/>
      </div>
    </div>

    <button @click="disconnect" v-if="status === 'connected'">연결끊기</button>
    <button @click="connect" v-if="status === 'disconnected'">연결</button> {{ status }}
    <br /><br />


  </div>

</template>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.min.js"></script>

<script>
import Chat from "../../components/lobby/chat.vue";
import Friends from "../../components/lobby/friends.vue";
import Profile from "../../components/lobby/profile.vue";
import Roomlist from "../../components/lobby/roomlist.vue";

export default {
  components: {
    Chat,
    Friends,
    Profile,
    Roomlist,
  },

  data: () =>{
        return{
          message: "",
          logs: [],
          status: "disconnected"
        }
    },

  methods: {
      connect() {
        this.socket = new WebSocket("ws://localhost:8001/chatting");
        this.socket.onopen = () => {
        this.status = "connected";
        this.logs.push({ event: "연결 완료: ", data: 'wss://echo.websocket.org'})
        

      this.socket.onmessage = ({data}) => {
        console.log(data)
        this.logs.push({ event: "메세지 수신", data });
        };
      };
    },
      disconnect() {
        this.socket.close();
        this.status = "disconnected";
        this.logs = [];
      },
      sendMessage(e) {
        console.log("메시지 전송")
        this.logs.push({ event: "메시지 전송", data: this.message });
        this.socket.send(this.message);
        this.message = "";
      }
  }
}
</script>

<style scoped>
  .gerstner-2 {
    display: grid;
    grid-template-columns: 1fr 3fr;
    /* grid-column-gap: 1em; */
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
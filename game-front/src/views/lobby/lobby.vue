<template>
  <div class="container">
    <h1>Lobby page</h1>

    <!-- <div v-if="status === 'connected'"> -->
      <div>
      <form @submit.prevent="webSocketSend" action="#">
        <input v-model="message"><button type="submit">메세지 전송</button>
      </form>
			<ul id="logs">
        <li v-for="(log, index) in logs" class="log" :key="index">
				{{ log.event }}: {{ log.data }}
        </li>
			</ul>
      </div>
    <!-- </div> -->

    <div class="gerstner-2">
      <div class="comp">
        <Profile class="profile"/>
        <!-- 소켓 props -->
        <Friends v-bind:websocket="websock" v-if="websock" class="friends"></Friends>
      </div>
      <div class="comp">
        <Roomlist class="roomlist"/>
        <!-- 소켓 props -->
        <Chat v-bind:websocket="websock" v-if="websock" class="chat"></Chat>
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

  data: () =>{
        return{
          websock: null,
          message: "",
          logs: [],
          status: "disconnected"
        }
    },

  
  mounted(){
    this.initWebScoket();
  },

  methods: {
      initWebScoket(){
        const wsuri = "ws://localhost:8001/chatting?userid=" + storage.getItem('id');
        this.websock = new WebSocket(wsuri);
        console.log("lobby websocket: "+this.websock)
        this.websock.onmessage = this.webSocketOnMessage;
        this.websock.onopen = this.webSocketOpen;
        this.websock.onerror = this.webSocketOnError;
        this.websock.onclose = this.webSocketClose;
        console.log(this.websock)
        this.webSocketOpen();
        console.log("websocksessionId: " + this.websock.sessionId)
      },

      webSocketOpen(){
        let temp_id = storage.getItem("id");
        console.log(temp_id);
        const msg = {'userId': 'eogma77'};
        this.websock.onopen = () =>{
          this.webSocketSend(JSON.stringify(msg));
        }
      },
      webSocketOnError(){
        this.initWebScoket();
      },

      webSokcetOnMessage(e){
        const redata = JSON.stringify(e.data);
        // this.arr
      },

      webSocketSend(Data){
        console.log("메시지 전송");
        this.websock.send(Data);
        console.log('1:' + Data)
        console.log('2:' + this.websock)
        console.log(this.websock.sessionId)
      },

      webSocketClose(e){
        console.log("소켓 닫기");
        this.websock.close();
      }
  }
}
</script>

<style scoped>
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
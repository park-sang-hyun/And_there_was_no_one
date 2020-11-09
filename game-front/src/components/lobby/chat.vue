<template>
  <div>
    <!-- <h5>채팅</h5> -->
    <div class="chatbox" v-if="status === 'connected'">

      <div class="scrollbar-box" id="style-1" >
        <div class="force-overflow" >
          <br/>
          <div v-for="(log, index) in logs" class="log" :key="index">
          {{ log.event }}: {{ log.data }}
          </div>
          <br/>
        </div>
      </div>
      <form @submit.prevent="sendMessage" action="#">
        <input v-model="message"><button type="submit">메시지 전송</button>
      </form>
      
    </div>
  </div>
</template>

<script>
export default {
  props: {
    websocket: Object,
  }, 

  data: () =>{
    return{
      
      message: "",
      logs: [],
      status: "connected"
    }
  },

  // created() {
  //   console.log("webSocket: "+this.websocket)
  // },

  methods: {
    connect() {
      this.socket = new WebSocket("ws://localhost:8001/chatting");
      this.socket.onopen = () => {
      this.status = "connected";
      this.logs.push({ event: "연결 완료: ", data: 'wss://echo.websocket.org'})
      

    this.socket.onmessage = ({data}) => {
      console.log(data)
      this.logs.push({ event: "메시지 수신", data });
      };
    };
  },
    disconnect() {
      this.socket.close();
      this.status = "disconnected";
      this.logs = [];
    },
    sendMessage(e) {
      const chatBox = document.querySelector(".scrollbar-box");
      

      console.log("메시지 전송")
      this.logs.push({ event: "메시지 전송", data: this.message });
      // scroll();
      chatBox.scrollTop = (chatBox.scrollHeight);
      
      this.message = "";  // 아래 소켓 연결 안돼서 지워지지 않음 그래서 여기서 임시로 지워줌 
      this.socket.send(this.message);
      this.message = "";
    },
    // 스크롤 바닥에 붙이는 코드를 sendMessage 안에 넣어뒀는데 그러면, 맨 마지막줄이 출력되지 않음 템플릿 변화된 다음에 호출해야되는데,
    // 방법을 몰라서 일단 밑에 br 넣어서 공간 두는 방식으로 일단 놔둠........
    // scroll() {
    //   const chatBox = document.querySelector(".scrollbar-box");
    //   console.log(chatBox.scrollHeight)
    //   chatBox.scrollTop = (chatBox.scrollHeight);
    // },
  }
}
</script>

<style scoped>
  .chatbox {
    border-radius: 20px;
    background-color: rgba(0, 41, 0, 0.6);
    height: 80%;
    width: 100%;
    margin-top: 15px;
    padding-top: 15px;
    margin-bottom: 15px;
    padding-bottom: 15px;
  }
  form{
    position: relative;
    /* top: 90%; */

    width: 100%;
    margin-top: 15px;
    margin-left: 20px;
  }
  input {
    width: 85%;
    margin-right: 10px;
  }

.scrollbar-box
{
	margin-left: 30px;
  
	height: 120px;
	width: 95%;
	
	overflow-y: scroll;
  position : relative; 
  bottom: 0px;
}

.force-overflow
{
  /* 스크롤바 내부의 글자가 누적되는 창 크기
  스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
	min-height: 121px;
}

/*
 *  scrollbar STYLE 1
 */

#style-1::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

#style-1::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F500;
}

#style-1::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}
</style>
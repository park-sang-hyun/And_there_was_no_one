<template>
    <div>
        <!-- <h5>채팅</h5> -->
        <div id="chatbox">

            <div class="scrollbar-box" id="style-1" >
                <div class="force-overflow" >
                    <br/>
                    <div v-for="(log, index) in logs" class="log" :key="index + 'chattingKey'">
                      {{ log.event }}: {{ log.data }}
                    </div>
                    <br/>
                </div>
            </div>
            <form>
                <input 
                    type="text" 
                    maxlength="80"
                    v-model="message">
                <button type="button" class="button" @click="sendMessage(message)">메시지 전송</button>
            </form>
          
        </div>
    </div>
</template>

<script>
import http from "../../util/http-common.js";

const storage = window.sessionStorage;

export default {

    data: () =>{
        return {
            socket: null,
            nickname: "",
            message: "",
            logs: [],
            count: 0,
            cnt: 0,
        }
    },

    created() {
        this.getProfile();
        this.connect();
    },

    destroyed() {
        
        this.socket.close();
        this.logs = [];
    },

    methods: {
        getProfile() {

            http
            .get("accounts/read/" + sessionStorage.getItem("id")+"/")
            .then((res) => {
                this.nickname = res.data.nickname;
            })
            .catch((err) => {
                console.log(err);
            })
        },

        connect() {
            this.socket = new WebSocket(`ws://localhost:8001/chatting?userid=${storage.getItem('id')}`);
            this.socket.onopen = () => {

                this.socket.onmessage = ({data}) => {
                    this.logs.push(JSON.parse(data));
                    const chatBox = document.querySelector(".scrollbar-box");
                    chatBox.scrollTop = (chatBox.scrollHeight);
                };
            };
        },
        disconnect() {
            this.socket.close();
            this.status = "disconnected";
            this.logs = [];
        },
        sendMessage(e) {
            this.socket.send(JSON.stringify({ event: this.nickname , data: e }));
            this.message = "";
        },
    }
}
</script>

<style lang="scss" scoped>
#chatbox {
    border-radius: 20px;
    background-color: #aeb0b32f;
    height: 90%;
    width: 93.5%;
    margin-left:1.9%;
    padding-top: 15px;
    margin-right: 5%;
    margin-top: 5.5%
}
form{
    position: relative;
    /* top: 90%; */
    width: 95%;
    margin-top: 10px;
    margin-left: 28px;
    display: flex;
    justify-content: space-between;
    padding-top: 7px;
}
input {
    width: 86%;
    height: 25px;
    margin-top:5px;
    margin-right: 10px;
    background-color: #e2e2e27a;
    border: none;
}

.button {
    border: none;
    color: rgba(255, 255, 255, 0.788);
    background: rgba(255, 254, 254, 0.151);
    appearance: none;
    font: inherit;
    border-radius: .3em;
    cursor: pointer;
    padding: 5px 10px;
    margin-bottom: 10px;
    margin-left:10px;
    
    &:hover {
        background: rgba(255, 255, 255, 0.493);
  }
}


.scrollbar-box
{
    margin-left: 30px;
    
    height: 110px;
    width: 95%;
    
    overflow-y: scroll;
    position : relative; 
    bottom: 0px;
    color:rgba(255, 254, 254, 0.7);
}

.force-overflow
{
    /* 스크롤바 내부의 글자가 누적되는 창 크기
    스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
    min-height: 111px;
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
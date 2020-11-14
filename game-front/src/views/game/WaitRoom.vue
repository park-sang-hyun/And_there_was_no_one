<template>
    <div id="waitRoom">
        <div v-if="!isSend">
        </div>

        <div v-else>
                <!-- 화면 상단 -->
                <div class="screen__top">
                    <!-- 방제목 -->
                    <div class="screen__top__left">
                        <div class="room__title">
                            [{{ room.id }}]번방 {{ sendTitle }} 
                        </div>
                    </div>
                    <div class="friends__invite">
                        <div class="btn btn-secondary" @click="friendsList">친구 초대</div>
                    </div>
                </div>

                <!-- 화면 왼쪽 -->
                <div class="screen__left">
                    <!-- 입장한 유저 목록 -->
                    <div class="user__part">
                        <user v-for="n in room.userList.length" 
                            :key="n-1 + componentLeaderKey" 
                            :number="n-1"
                            :existClick="isUserClick[n-1]"
                            :userData="room.userList[n-1]" 
                            :sendLeader="room.leader"
                            :youLeader="leader"
                            :window="windowScreen"
                            @clickUser="clickUser"
                            @noClickUser="noClickUser"
                            @changeLeader="changeLeader"
                            style="display: inline-block; float:left;">
                            <span style="display: none;">{{ isUserClick[n-1] }}</span>
                        </user>
                        <empty v-for="n in EmptyCount" :key="n + 'Emptykey'" :window="windowScreen" style="display: inline-block; float:left;"/>
                        <none v-for="n in NoneCount" :key="n + 'Nonekey'" :window="windowScreen" style="display: inline-block; float:left;"/>
                    </div>
                    <!-- 채팅 (상용구) -->
                    <div class="chat__part d-flex justify-content-center">
                        <div class="input-group">
                            <div class="input-group-append">
                                <input class="input__text__part" 
                                    type="text" 
                                    placeholder="채팅을 입력해주세요" 
                                    maxlength="50"
                                    v-model="chatMsg" 
                                    @keyup.enter="sendMessage(chatMsg)">
                            </div>
                            <!-- <select class="custom-select" id="inputMessageSelect" aria-label="Select Chat phrases" style="background-color: rgba(255, 255, 255, 0.3); color: white; border: none;">
                                <option selected style="color:black;">채팅 문구 선택</option>
                                <option v-for="(chat, index) in chatList" :value="index" :key="chat + 'chatkey'" style="color:black;">{{ chat }}</option>
                            </select> -->
                            <div class="input-group-append">
                                <div class="btn btn-outline-secondary" type="button" @click="sendMessage(chatMsg)">Enter</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 화면 오른쪽 -->
                <div class="screen__right">
                    <!-- 게임 설정 출력 (상중하 / 모드) 방장인 경우에 클릭 가능 -->
                    <div class="setting__part">
                            <mode :mode="room.mode" :isLeader="leader" :key="componentModeKey" @modeChange="modeChange" style="margin-bottom: 20px;"/>
                            <difficulty :difficulty="room.difficulty" :key="componentDiffKey" :isLeader="leader" @difficultyChange="difficultyChange"/>
                        <div v-if="leader" :key="componentModifyKey" class="mode__button d-flex justify-content-center" style="margin-top: 20px;">
                            <button @click="roomUpdate">게임 모드 수정</button>
                        </div>
                    </div>

                    <!-- 채팅 부분 -->
                    <div class="room__chatting d-flex justify-content-center">
                        <div v-if="chatStatus" class="chatting__area">
                            <div class="scrollbar-box" id="scrollbar__style" >
                                <div class="force-overflow" >
                                    <!-- <br/> -->
                                    <div v-for="(log, index) in chatLogs" :key="index + 'chatKey'">
                                        <strong>{{ log.event }}</strong>: <span style="color: rgb(201, 201, 201);">{{ log.data }}</span>
                                    </div>
                                    <!-- <br/> -->
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 게임 시작 버튼 -->
                    <div class="game__start d-flex justify-content-center align-items-center">
                        <button @click="GameStart" :disabled="!leader">게임 시작</button>
                        <button @click="ExitRoom">방 나가기</button>
                    </div>
                </div>


            <div v-if="isChangeLeader" class="change__part">
                <div class="modal__part">
                    <div class="modal__text">
                        {{ room.userList[changeLeaderNum].nickname }}에게 방장을 넘기겠습니까?
                    </div>
                    <div class="modal__button d-flex justify-content-center">
                        <button @click="exitLeader">닫기</button>
                        <button @click="leaderChange" class="ml-2">팀장 위임</button>
                    </div>
                </div>
            </div>
            
            <div v-if="isPopupFriend" class="change__part">
                <div class="freinds__part">
                    <div v-for="friend in myfriends" :id="friend.nickname + '-id'" :key="friend.id + 'friendKey'" class="modal__text" @click="inviteFriend">
                        {{ friend.nickname }}
                    </div>
                    <div class="modal__button">
                        <button @click="isPopupFriend = false;">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import user from '@/components/room/WaitUser.vue'
import empty from '@/components/room/EmptyUser.vue'
import none from '@/components/room/NoneUser.vue'
import mode from '@/components/room/modeSetting.vue';
import difficulty from '@/components/room/difficultySetting.vue';
import http from '@/util/http-game.js';
import httplobby from '@/util/http-lobby.js';
import 'bootstrap/dist/css/bootstrap.min.css';

const storage = window.sessionStorage;
const socketURL = 'ws://localhost:8002/chatting';
const socketRoomURL = 'ws://localhost:8002/renewing';


export default {
    name: 'WaitRoom',
    
    components: {
        user,
        empty,
        none,
        difficulty,
        mode,
    },

    props: {
        roomId: {
            type: undefined,
        }
    },

    data() {
        return {
            componentModeKey: 1000,
            componentDiffKey: 0,
            componentModifyKey: 10000,
            componentLeaderKey: 100000,
            room: {},               // room 데이터 받아서 넣기
            defaultroom: {          // 개발용 default 값
                title: '스겜',
                id: 1,
                mode: 1,
                cur_count: 1,
                max_count: 6,
                difficulty: 1,
                start: false,
                userList: [
                    {
                        nickname: '1번사람',
                        id: 1,
                    },
                    {
                        nickname: '2번사람',
                        id: 2,
                    },
                    {
                        nickname: '3번사람',
                        id: 3,
                    },
                    {
                        nickname: '4번사람',
                        id: 4,
                    },
                ],
                leader: {
                    id: 1,
                    nickname: '1번사람',
                },
            },
            user: 0,                // 현재 본인 위치
            EmptyCount: 0,          // 들어오지 않은 유저 수
            NoneCount: 0,           // 방에 설정된 유저 수가 8 이하일 때, 들어올 수 없는 칸
            isMode: [
                false, false, false
                ],                  // 현재 모드가 무엇인지 확인 (true: 자유그리기 | false: 이어그리기)
            delayMode: false,       // 로딩 화면 띄울건지 여부 (true: LoadingModeOne or Two | false: 현재 방)
            
            window: {               // 현재 창 너비
                width: 0,
                height: 0,
            },
            
            chatList: [             // 채팅에 쓸 상용구
                'Ready',
                '시작합시다',
                '잠시만요',
                'ㅇㅇ',
            ],
            // 소켓, 채팅 메시지
            chatMsg: '',
            chatLogs: [],
            chatStatus: false,
            socket: null,
            socketRoom: null,

            // 받아온 모드 값
            checked: {
                mode: 1,
                difficulty: 1,
            },
            leader: false,
            isSend: false,
            isUserClick: [
                false, false, false, false, false, false, false, false,
            ],
            isChangeLeader: false,
            changeLeaderNum: 0,
            myNickname: '',
            myfriends: [],
            isPopupFriend: false,
            ifFirst: true,
        }
    },


    created() {

        // 이후엔 요청 보내서 받아올 것
        // this.room = this.defaultroom;
        this.readRoom();

        // 로딩 화면 막아 놓기
        this.delayMode = false;

        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
    },

    computed: {
        windowScreen() {
            return this.window
        },

        userData(index) {
            return this.room.userList[index]
        },

        sendTitle() {
            return this.room.title
        },

    },

    watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
    },

    destroyed() {
        
        this.socket.close();
        this.socketRoom.close();

    },

    methods: {

        readRoom() {

            http
            .get(`game/waitroom/${this.roomId}`)
            .then((res) => {
                this.room = res.data;
                this.isSend = true;

                if (this.ifFirst) {
                    this.connect();
                    this.connectRoom();
                    this.ifFirst = false;
                }

                console.log(this.room);


                // 빈자리 출력을 위해 인원 확인
                if (this.room.cur_count < this.room.max_count) {
                    this.EmptyCount = this.room.max_count - this.room.cur_count;
                }

                // 막아둘 자리 출력을 위한 인원 확인
                if (this.room.max_count < 8) {
                    this.NoneCount = 8 - this.room.max_count;
                }
                
                // 본인이 방장인지 여부 확인
                if (this.room.leader.id == storage.getItem('id')) {
                    this.leader = true;
                } else {
                    this.leader = false;
                }

                // 본인 닉네임 찾기
                for (let k=0; k < this.room.userList.length; k++) {
                    if (this.room.userList[k].id == storage.getItem('id')) {
                        this.myNickname = this.room.userList[k].nickname;
                        break;
                    }
                }

                this.componentModeKey += 1;
                this.componentDiffKey += 1;
                this.componentModifyKey += 1;
                this.componentLeaderKey += 10000;

            })
            .catch((err) => {
                console.log(err);
            })
           
        },

        // 방 업데이트
        roomUpdate() {
            // 현재 유저가 리더인지 확인

            // 필요한 데이터 넣어서 보내기
            let formData = new FormData();
            // formData.append('roomId', this.room.id);
            formData.append('title', this.room.title);
            formData.append('mode', this.checked.mode);
            formData.append('max_count', this.room.max_count);
            formData.append('difficulty', this.checked.difficulty);

            http
            .put(`game/modify/${this.room.id}`, formData)
            .then((res) => {
                this.sendRoomMessage();
                alert(`방 정보가 수정되었습니다.`)
            })
            .catch((err) => {
                console.log(err);
            })

        },

        // 채팅 버튼
        // chatMessage() {
        //     var s = document.getElementById("inputMessageSelect");
        //     var idx = s.options[s.selectedIndex].value;
        //     if (this.chatList[idx] === undefined) {
        //         alert('메시지를 선택해주세요');
        //     } else {
        //         // 넣어주는 건 되는데 그래서 어떻게 해당 위치에서만 띄우지....
        //         console.log(this.chatList[idx]);
        //     }
        // },

        // 대기 방 소켓 연결
        connectRoom() {
            this.socketRoom = new WebSocket(`${socketRoomURL}/${this.room.id}`);
            this.socketRoom.onopen = () => {

                this.sendRoomMessage();

                this.socketRoom.onmessage = ({data}) => {
                    var sendRoomData = { start : false };
                    if (typeof data !== 'undefined') {
                        sendRoomData = JSON.parse(data);
                    }
                    
                    if (sendRoomData.start) {
                        this.$router.replace({ name: 'LoadingGame' , params: { sendGame: sendRoomData.sendGame, roomId: this.room.id }});
                        // this.delayMode = true;
                        // setTimeout(() => this.$router.replace({ name: 'PlayGame' , params: { sendGame: sendRoomData.sendGame, roomId: this.room.id }}), 7000);
                    } else {
                        this.readRoom();
                    }
                };
            };
        },
        
        //  
        sendRoomMessage() {
            this.socketRoom.send(JSON.stringify({ start: false, game: false, room_id: this.room.id })); 
        },

        // 채팅 부분
        // 소켓 연결
        connect() {
            this.chatStatus = true;
            this.socket = new WebSocket(`${socketURL}/${this.room.id}`);
            this.socket.onopen = () => {
                // this.chatLogs.push({ event: "연결 완료", data: 'wss://echo.websocket.org'})
                

                this.socket.onmessage = ({data}) => {
                    this.chatLogs.push(JSON.parse(data));
                    const chatBox = document.querySelector(".scrollbar-box");
                    chatBox.scrollTop = chatBox.scrollHeight;
                };
            };
        },

        //  채팅 보내기
        sendMessage(Data) {
            // websocketsend(Data) 와 동일
            console.log("나 채팅!!")
            if (Data != '' && this.myNickname != '') {
                this.socket.send(JSON.stringify({ event: this.myNickname, data: Data, room_id: this.room.id }));
            }
            this.chatMsg = "";
        },


        // 우측 상단 버튼

        // 우측 하단 버튼
        // 게임 시작(팀장만)
        GameStart() {

            if (this.room.cur_count < 3) {
                alert('인원 수가 3명보다 적어 게임을 시작할 수 없습니다.');
            } else {
                // 현재 게임 모드를 확인해서 어떤 로딩 화면을 띄울 건지 결정
                this.isMode = [false, false, false];        // 초기화
                this.isMode[this.room.mode - 1] = true;     // mode에 맞는 것만 true

                http
                .get(`game/ingame/${this.room.id}`)
                .then((res) => {
                    this.socketRoom.send(JSON.stringify({ start: true, game: false, room_id: this.room.id, sendGame: res.data }));
                    // // 로딩화면 띄우기
                    // this.delayMode = true;
                    // setTimeout(() => this.$router.replace({ name: 'PlayGame' , params: { sendGame: res.data, roomId: this.room.id, sendSocket: this.socket }}), 7000);
                })
                .catch((err) => {
                    console.log(err);
                })
            }
        },

        // 방 나가기
        ExitRoom() {
            http
            .delete(`game/leave/${storage.getItem('id')}`)
            .then((res) => {
                this.sendRoomMessage();
                this.$router.replace({ name: 'Lobby' })
            })
            .catch((err) => {
                console.log(err);
            })
            
        },

        // 선택한 설정값 변경 (모드 | 난이도)
        modeChange(mode) {
            this.checked.mode = Number(mode);
        },
        difficultyChange(difficulty) {
            this.checked.difficulty = Number(difficulty);
        },

        // 유저 선택
        clickUser(num) {

            this.isUserClick = [false, false, false, false, false, false, false, false,],
            this.isUserClick[num] = true;
        },

        noClickUser(num) {
            this.isUserClick[num] = false;
        },

        changeLeader(num) {
            if (this.leader) {
                this.changeLeaderNum = num;
                this.isChangeLeader = true;
            } else {
                alert('리더가 아닙니다');
            }
        },

        exitLeader() {
            this.isChangeLeader = false;
        },

        // 리더 위임
        leaderChange() {

            http
            .put(`game/mandate/${this.room.userList[this.changeLeaderNum].id}/${storage.getItem('id')}`)
            .then((res) => {
                this.isChangeLeader = false;
                this.sendRoomMessage();
            })
            .catch((err) => {
                console.log(err);
            })
        },

        // 친구 리스트 가져오기
        friendsList() {
            httplobby
            .get(`user/loginFriend/list/${storage.getItem('id')}`)
            .then((res) => {
                console.log(res.data);
                this.myfriends = res.data;
                this.isPopupFriend = true;
            })
        },

        // 친구 초대
        inviteFriend(event) {
            var ID = event.target.id.split('-');
            var formData = new FormData;
            formData.append('from_id', storage.getItem('id'));
            formData.append('to_nickname', ID[0]);
            formData.append('room_id', this.room.id);

            httplobby
            .post('alarm/inviteGame', formData)
            .then((res) => {
                console.log(res.data);
            })

        },
        

        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            this.window.height = (window.innerHeight < 724) ? 724 : window.innerHeight;
            this.layoutCal();
        },

        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            document.documentElement.style.setProperty('--widthSize', this.window.width + suffix);
            document.documentElement.style.setProperty('--heightSize', this.window.height + suffix);
            document.documentElement.style.setProperty('--leftSize', ((this.window.width * 0.6) - 20) + suffix);
            document.documentElement.style.setProperty('--rightSize', ((this.window.width * 0.4) - 20) + suffix);
            document.documentElement.style.setProperty('--mainSize', (this.window.height - 80 - 20) + suffix);
            document.documentElement.style.setProperty('--chatHeightSize', ((this.window.height * 0.25) - 20) + suffix);
        },
    },

}
</script>

<style lang="scss" scoped>
/* 가상 선택자, 우선 순위가 제일 높음 */
:root {
    --widthSize: 400px;
    --heightSize: 400px;
    --leftSize: 800px;
    --rightSize: 400px;
    --mainSize: 800px;
    --chatHeightSize: 400px;
}

#waitRoom {
    height: 100%;
    width: 100%;
    min-width: 1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    color: white;
}


/* 전체 영역 3부분으로 나눔 상단 | 왼 | 오 */
.screen__top {
    position: relative;
    width: var(--widthSize);
    height: 80px;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    line-height: 80px;
    /* background-color: beige; */
}

.screen__left {
    display: inline-block;
    width: var(--leftSize);
    height: var(--mainSize);
    margin: 10px;
    /* background-color: aquamarine; */
}

.screen__right {
    display: block;
    float: right;
    width: var(--rightSize);
    height: var(--mainSize);
    margin: 10px;
    /* background-color: skyblue; */
}

/* 레이아웃 상단 */
.screen__top__left {
    position: inline-block;
    width: var(--leftSize);
    height: 100%;
}


/* 레이아웃 왼쪽 */
.user__part {
    width: 100%;
}

.chat__part {
    width: 100%;
    height: 40px;
    margin: 10px auto;
}

/* 레이아웃 오른쪽 */
.setting__part {
    display: block;
    width: 100%;
    height: 55%;
    padding: 20px;
}

.room__chatting {
    display: block;
    width: 100%;
    height: 25%;
}

.game__start {
    display: block;
    width: 100%;
    height: 20%;
}


/* 개별 style */
/* 상단 우측 버튼 */
.friends__invite {
    position: fixed;
    right: 15px;
    top: 0px;
}

.friends__invite__button {
    padding: 0px 10px;
    background-color: green;
    border: none;
    border-radius: 20px;
    color: white;
    height: 50px !important;
}


/* 우측 상단 버튼 */
.mode__button > button {
    border: none;
    background-color: rgba(61, 61, 61, 0.5);
    outline: none;
    border-radius: 5px;
    padding: 10px;
    font-size: 1.1rem;
    text-decoration: none;
    color: white;
    /* font-weight: bold; */
}


/* 우측 하단 버튼 */
.game__start > button {
    border: none;
    background-color: #f7130b;
    box-shadow: 0px 5px 0px 0px #b12208;
    outline: none;
    border-radius: 5px;
    padding: 10px 30px;
    font-size: 1.3rem;
    text-decoration: none;
    margin: 20px;
    color: black;
    font-weight: bold;
    position: relative;
    display: inline-block;
    cursor: pointer;
}

.game__start > button:active {
    transform: translate(0px, 5px);
    -webkit-transform: translate(0px, 5px);
    box-shadow: 0px 1px 0px 0px;
}

/* 채팅 */
.input-group {
    width: 94%;
    /* background-color: rgb(37, 37, 37); */
    padding: 0px;
    border-radius: 5px;
    overflow: hidden;
}

// 채팅 인풋
.input__text__part {
    // width: var(--leftSize);
    width: calc( var(--leftSize) - 120px );
    padding-left: 10px;
    background-color: rgba(255, 255, 255, 0.3); 
    color: white; 
    border: none;
}


.chatting__area {
    width: 82%;
    background-color: rgba(255, 255, 255, 0.3);
    padding: 10px 10px;
    border-radius: 10px;
    font-size: 0.9rem;
}


.scrollbar-box
{
    width: 100%;
    height: calc(var(--chatHeightSize) - 30px);
	overflow-y: scroll;
    overflow-x: hidden;
    white-space: normal;
    position : relative; 
    bottom: 0px;
}

.force-overflow
{
  /* 스크롤바 내부의 글자가 누적되는 창 크기
  스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
	min-height: calc(var(--chatHeightSize) - 30px);
}


.change__part {
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    background-color: rgba(0, 0, 0, 0.5);
}

.modal__part {
    position: fixed;
    left: 50%;
    top: 50%;
    height: 100px;
    padding: 0px 50px;
    transform: translate(-50%, -50%);
    border-radius: 20px;
    background-color: white;
    color: black;
}

.modal__text {
    text-align: center;
    line-height: 50px;
}

.modal__button > button {
    padding: 3px;
    border-radius: 10px;
    color: white;
    background-color: rgba(28, 144, 65, 0.8);
    border: none;
}


// scrollbar__style

#scrollbar__style::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

#scrollbar__style::-webkit-scrollbar
{
	width: 5px;
	background-color: #F5F5F500;
}

#scrollbar__style::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}

</style>

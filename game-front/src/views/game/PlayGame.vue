<template>
    <div id="PlayGame">
        <!-- 제시어 확인 -->
        <div v-if="checkRoll">
            <roll :sendGame="sendGame" />
        </div> 

        <!-- 게임 화면 -->
        <div v-else>
            <!-- 자유 그리기 모드 -->
            <div v-if="isMode">
                <ModeOne :game="game" :output="output" :isMode="isMode" :isTurn="isTurn" :memCount="sendMemCount" :images="sendImage" @imgFile="sendAI" ref="modeOne" />
            </div>
                
            <!-- 이어그리기 모드 -->
            <div v-else>
                <ModeTwo :game="game" :output="output" :isMode="isMode" :isTurn="isTurn" :memCount="sendMemCount" @imgFile="sendAI" ref="modeTwo" />
            </div>


            <div v-if="timerShow" class="timer__part">
                <div class="text__part">
                    {{ showTimer }}
                </div>
            </div>

            <div v-if="beforeShow" class="before__part">
                <div class="text__before">
                    {{ before.showTimer }}
                </div>
            </div>

            <div v-if="show" class="showScreen">

            </div>

            <div v-if="end" class="showScreen">
                <EndScreen :isEnd="end" 
                    :isFinish="isFinish" 
                    :endScore="score" 
                    :game="game" 
                    :images="sendImage" 
                    :sendSentence="sendSentence"
                    @submitVoteResult="endVoteResult"
                    ref="endscreen" />
            </div>

            
            <div class="chat__part">
                <div class="chatting__area">
                    <div class="scrollbar-box" id="scrollbar__style" >
                        <div class="force-overflow" >
                            <br v-for="n in 27" :key="n + 'chatBRKey'"/>
                            <div v-for="(log, index) in chatLogs" class="log" :key="index + 'chatLogKey'">
                                <strong style="margin-left: 5px;">{{ log.event }}</strong>: <span style="color: rgb(201, 201, 201);">{{ log.data }}</span>
                            </div>
                            <br/>
                        </div>
                    </div>
                </div>
                <div class="input-group">
                    <select class="custom-select" id="inputMessageSelect" aria-label="Select Chat phrases" style="background-color: rgba(255, 255, 255, 0.3); color: white; border: none;">
                        <option selected style="color:black;">채팅 문구 선택</option>
                        <option v-for="(chat, index) in chatList" :value="index" :key="chat + 'chatkey'" style="color:black;">{{ chat }}</option>
                    </select>
                    <div class="input-group-append">
                        <div class="btn btn-outline-secondary" type="button" @click="chatMessage">Enter</div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
import roll from '@/components/game/GameRoll.vue';
import ModeOne from '@/components/game/GameModeOne.vue';
import ModeTwo from '@/components/game/GameModeTwo.vue';
import EndScreen from '@/components/game/EndScreen.vue';
import http from '@/util/http-game.js';
import aihttp from '@/util/http-ai.js';
import 'bootstrap/dist/css/bootstrap.min.css';

const storage = window.sessionStorage;
const socketURL = 'ws://localhost:8002/chatting';
const socketPlayURL = 'ws://localhost:8002/renewing';


export default {
    name: "PlayGame",

    components: {
        roll,
        ModeOne,
        ModeTwo,
        EndScreen,
    },

    props: {
        sendGame: {
            type: undefined,
        },
        sendSocket: {
            type: WebSocket,
        }
    },

    data() {
        return {
            game: { },
            turn: 0,                            // 현재 턴
            isMode: true,                       // 현재 게임 모드 확인 (true: 자유그리기 | false: 이어그리기)
            checkRoll: false,                   // 제시어 확인 페이지 여부
            timerShow: false,                   // timer 보여주기
            beforeShow: false,                  // before timer 여부
            show: false,                        // modal 등 전체 영역
            score: [],                          // 각 턴의 유저 score
            end: false,                         // 턴 종료 여부
            finish: false,                      // 게임 종료 여부
            images: [],                         // 이미지 넣기 위한 곳
            defaultGame: {
                id: 1,
                mode: 2,
                difficulty: 1,
                cur_count: 5,
                max_count: 7,
                start: true,
                topic: '과일',
                word: '사과',
                shadow: {
                    id: 1,
                    nickname: 'Shadow',
                },
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
                    {
                        nickname: '5번사람',
                        id: 5,
                    },
                ],
                
            },
            // 각 데이터 별로 맞는 설정 이름/숫자 매칭을 위한 리스트
            checkName: {
                mode: [
                '',
                '자유그리기',
                '이어그리기',
                'NO AI',
                ],
                difficulty: [
                    '',
                    '상',
                    '중',
                    '하'
                ],
                sec: [
                    0, 5, 10, 15
                ],
            },
            // 위 checkName에서 매칭된 값 넣기
            output: {
                mode: '',
                difficulty: '',
                sec: 0,
            },
            chatList: [         // 채팅에 쓸 상용구
                '???',
                '이건 아니지',
                '잘한다',
                'ㅇㅇ',
            ],
            chat: {
                logs: [],
            },
            memCount: {
                EmptyCount: 0,          // 들어오지 않은 유저 수
                NoneCount: 0,           // 방에 설정된 유저 수가 8 이하일 때, 들어올 수 없는 칸
            },
            counter: false,
            interval: '',
            showTimer: '',
            timer: 0,
            before: {
                counter: false,
                interval: '',
                showTimer: '',
                timer: 4,
            },
            
            // 소켓, 채팅 메시지
            chatMsg: '',
            chatLogs: [],
            socket: null,
            socketPlay: null,
            sendSentence: '',
            myNickname: '',
            
        }
    },

    created() {
        // 이후 넘기는 걸로 받아올 것
        // this.game = this.defaultGame;
        this.game = this.sendGame;
        if ( this.game.mode === 2 ) {
            this.isMode = false;
        } else {
            this.isMode = true;
        }

        // 빈자리 출력을 위해 인원 확인
        if (this.game.cur_count < this.game.max_count) {
            this.memCount.EmptyCount = this.game.max_count - this.game.cur_count;
        }
        // 막아둘 자리 출력을 위한 인원 확인
        if (this.game.max_count < 8) {
            this.memCount.NoneCount = 8 - this.game.max_count;
        }

        // 각 턴마다 점수를 위한 부분
        for (let j=0; j < this.game.cur_count; j++) {
            this.score.push(0);
        }
        
        // 본인 닉네임 찾기
        for (let k=0; k < this.game.userList.length; k++) {
            if (this.game.userList[k].id == storage.getItem('id')) {
                this.myNickname = this.game.userList[k].nickname;
                break;
            }
        }

        this.connect();
        this.connectPlay();

        
        // 역할 확인 부분
        
        // this.checkRoll = false;
        // this.beforeStartTimer();
        document.documentElement.style.setProperty('--indexNum', 99);
        
        this.checkRoll = true;
        setTimeout( this.goGame , 10000);


        // 설정 값 별로 매칭되는 이름/숫자 넣어주기
        this.output.mode = this.checkName.mode[this.game.mode];
        this.output.difficulty = this.checkName.difficulty[this.game.difficulty];
        this.output.sec = this.checkName.sec[this.game.difficulty];
        this.timer = this.output.sec;
    },

    computed: {
        // 현재 턴을 보내준다.
        isTurn() {
            this.yourTurn();
            return this.turn
        },

        sendMemCount() {
            return this.memCount
        },

        sendImage() {
            return this.images
        },

        isFinish() {
            return this.finish
        }

    },

    destroyed() {
        this.socket.close();
        this.socketPlay.close();
    },

    methods: {
        // 게임 시작
        goGame() {
            this.checkRoll = false;
            if (this.game.userList[0].id == storage.getItem('id')) {
                setTimeout(() =>
                    this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: false, turn: this.turn, data: '게임을 시작합니다.' }))
                    , 1000);
            }
        },

        // 게임 소켓 연결
        connectPlay() {
            this.socketPlay = new WebSocket(`${socketPlayURL}/${this.game.id}`);
            
            this.socketPlay.onopen = () => {

                this.socketPlay.onmessage = ({data}) => {
                    var PlayData = JSON.parse(data);
                    if (PlayData.finish) {
                        this.turnFinish();
                    } else {
                        if (PlayData.isturn) {
                            this.turn = PlayData.turn;
                            console.log(this.turn);
                            if (this.turn <= this.game.cur_count) {
                                setTimeout(this.beforeStartTimer, 1000);
                            } else {
                                this.end = true;
                            }
                        } else {
                            this.$refs.endscreen.resultCheck(PlayData.vote_result);
                        }
                    }
                    
                };
            };
        },


        // 채팅 부분
        // 소켓 연결
        connect() {
            this.socket = new WebSocket(`${socketURL}/${this.game.id}`);
            this.socket.onopen = () => {
                
                this.socket.onmessage = ({data}) => {
                    this.chatLogs.push(JSON.parse(data));
                    const chatBox = document.querySelector(".scrollbar-box");
                    chatBox.scrollTop = chatBox.scrollHeight;
                };
            };
        },

        // 채팅 버튼
        chatMessage() {
            var s = document.getElementById("inputMessageSelect");
            var idx = s.options[s.selectedIndex].value;
            if (this.chatList[idx] === undefined) {
                alert('메시지를 선택해주세요');
            } else {
                console.log(this.chatList[idx]);
                this.socket.send(JSON.stringify({ event: this.myNickname, data: this.chatList[idx], room_id: this.game.id }));
            }
        },

        // 본인의 턴이면 채팅창의 우선도를 뒤로, 아니면 앞으로
        yourTurn() {
            if (this.turn > 0 && this.turn < this.game.userList.length + 1) {
                if (this.game.userList[this.turn - 1].id == storage.getItem('id') ) {
                    document.documentElement.style.setProperty('--indexNum', 1);
                } else {
            
                    document.documentElement.style.setProperty('--indexNum', 99);
                }
            }
        },

        // 타이머
        startTimer() {
            this.timerShow = true;
            this.interval = setInterval(this.countDown, 1000);
        },

        countDown() {
            var n = this.timer;
            if (!this.counter) {
                this.counter = true;   
                this.showTimer = n;
            } else if (n > 0) {
                n = n - 1;
                this.showTimer = n;
                this.timer = n;
            } else {
                clearInterval(this.interval);
                this.counter = false;
                this.showTimer = '';
                this.timerShow = false;
                this.timer = this.output.sec;
                this.show = true;
                if (this.game.mode == 2) {
                    this.$refs.modeTwo.$refs.draw.handleSaveClick();
                } else {
                    this.$refs.modeOne.$refs.draw.handleSaveClick();
                }
            }
        },

        
        // before 타이머
        beforeStartTimer() {

            if (this.game.mode == 1) {
                this.$refs.modeOne.$refs.draw.resetCanvas();
            } else if (this.game.mode == 3) {
                this.$refs.modeOne.$refs.draw.resetCanvas();
            }

            this.show = false;
            this.beforeShow = true;
            this.before.interval = setInterval(this.beforeCountDown, 1000);
        },

        beforeCountDown() {
            var m = this.before.timer;
            if (!this.before.counter) {
                this.before.counter = true;
                this.before.showTimer = `${ this.game.userList[this.turn - 1].nickname } Turn`;     
            } else if (m > 1) {
                m = m - 1;
                this.before.showTimer = `${m}`;
                this.before.timer = m;
            } else {
                clearInterval(this.before.interval);
                this.before.counter = false;
                this.before.showTimer = '';
                this.beforeShow = false;
                this.before.timer = 4;
                this.startTimer();
            }
        },

        // mode 1,2 : 이미지 저장 후 ai로 보내기 / mode 3 : 이미지 저장 후 턴 넘기기
        sendAI(image) {
            this.images.push(image);
            console.log('this turn:' , this.turn);
            console.log('image:', this.images[this.turn-1]);

            if (this.game.userList[this.turn - 1].id == storage.getItem('id')) {
                
                if (this.game.mode == 3) {
                    setTimeout(() => this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: false, turn: this.turn })), 500 );

                } else {
                    let formData = new FormData;
                    formData.append('inputImage', image);
                    formData.append('turn', this.turn);
                    formData.append('roomId', this.game.id);

                    var flagAI = true;

                    // ai로 이미지보내기
                    aihttp
                    .post(`objects/image/`, formData)
                    .then((res) => {
                        console.log(res.data);
                        if (res.data.message) {
                            for (let i=0; i < res.data.result.length; i++) {
                                if (res.data.result[i] == this.game.word) {
                                    if (this.game.mode == 1) {
                                        flagAI = false;
                                        this.score[this.turn] = this.score[this.turn] - 20;
                                        this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: false, turn: this.turn }));
                                    } else if (this.game.mode == 2) {
                                        flagAI = false;
                                        this.score[this.turn] = this.score[this.turn] - 100;
                                        this.sendSentence = '누군가가 AI에게 발각되었습니다.';
                                        this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: true, turn: this.turn }));
                                        // this.turnFinish();

                                    }
                                    break;
                                }
                            }
                            if (flagAI) {
                                    this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: false, turn: this.turn }));
                            }
                        } else {
                            this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: true, finish: false, turn: this.turn }));
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    })
                }
            }

        },

        // 투표 결과 받아오기
        endVoteResult(data) {
            this.socketPlay.send(JSON.stringify({ game: true, room_id: this.game.id, isturn: false, finish: false, vote_result: data }));
        },

        // 게임 종료
        turnFinish() {
            this.end = true;
            this.finish = true;
        }

    },
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto:100,300');
@import url('https://fonts.googleapis.com/css2?family=Lato&family=Montserrat&family=Open+Sans&display=swap');

/* 가상 선택자, 우선 순위가 제일 높음 */
:root {
    --widthSize: 1000px;
    --heightSize: 800px;
    --indexNum: 99;
}


#PlayGame {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url('../../assets/images/background2.jpg') no-repeat center center fixed;
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;
    color: white;
}

.container {
    height: 100%;
    width: 100%;
    margin: 0px;
}

/* 채팅 부분 */
.chat__part {
    position: fixed;
    z-index: 2;
    bottom: 40px;
    left: 0;
    width: 400px;
    height: 600px;
}

.input-group {
    position: fixed;
    z-index: var(--indexNum);
    bottom: 0;
    left: 0;
    width: 320px;
    height: 40px;
}

.timer__part {
    position: fixed;
    left: 5%;
    top: 10%;
    // transform: translateY(-50%);
}

.text__part {
    width: 60px;
    height: 60px;
    color: rgba(255, 255, 255, 0.5);
    font-size: 2.5rem;
    font-weight: bold;
    font-family: 'Montserrat', 'Open Sans', sans-serif;
}

.before__part {
    position: fixed;
    z-index: 10;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: table;
    transition: opacity .3s ease;
}

.text__before {
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    color: green;
    font-size: 4rem;
    font-weight: bold;
    font-family: 'Montserrat', 'Open Sans', sans-serif;
}

/* 투표 등 배경 흐리기 해야할 때 */
.showScreen {
    position: fixed;
    z-index: 10;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .7);
    display: table;
    transition: opacity .3s ease;
}

// 채팅 부분

.chatting__area {
    width: 100%;
    padding: 10px 3px;
    border-radius: 10px;
    font-size: 0.9rem;
    color: white;
}


.scrollbar-box
{
    width: 100%;
    height: 580px;
	overflow-y: scroll;
    overflow-x: hidden;
    white-space: normal;
    position : relative; 
    bottom: 0px;
    direction:rtl; /* css scrollbar left */
}


.force-overflow
{
  /* 스크롤바 내부의 글자가 누적되는 창 크기
  스크롤바 height 보다 min-height가 커야 우측 스크롤바가 생김 */
	min-height: 580px;
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
	width: 3px;
	background-color: #F5F5F500;
}

#scrollbar__style::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}



</style>
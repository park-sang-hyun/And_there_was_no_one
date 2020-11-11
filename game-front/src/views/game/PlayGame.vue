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

            <div class="chat__part">
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
                <EndScreen :isEnd="end" :endScore="score" :game="game" :isFinish="isFinish" :images="sendImage"/>
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

        
        // 역할 확인 부분
        this.checkRoll = true;
        // console.log(this.sendGame);
        var roll = setTimeout( this.goGame , 10000);
        // this.checkRoll = false;

        var timeCheck = setTimeout( this.beforeStartTimer, 10000);

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

    methods: {
        // 채팅 버튼
        chatMessage() {
            var s = document.getElementById("inputMessageSelect");
            var idx = s.options[s.selectedIndex].value;
            if (this.chatList[idx] === undefined) {
                alert('메시지를 선택해주세요');
            } else {
                // 넣어주는 건 되는데 그래서 어떻게 해당 위치에서만 띄우지....
                this.chat.logs = [...[(this.game.userList[1].nickname, this.chatList[idx])], ...this.chat.logs];
                console.log(this.chat.logs);
            }
        },

        // 게임 시작
        goGame() {
            this.checkRoll = false;
        },

        // 본인의 턴이면 채팅창의 우선도를 뒤로, 아니면 앞으로
        yourTurn() {
            if (this.game.userList[this.turn].id == 4 ) {
                document.documentElement.style.setProperty('--indexNum', -1);
            } else {
        
                document.documentElement.style.setProperty('--indexNum', 99);
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
            this.show = false;
            this.beforeShow = true;
            this.before.interval = setInterval(this.beforeCountDown, 1000);
        },

        beforeCountDown() {
            var m = this.before.timer;
            if (!this.before.counter) {
                this.before.counter = true;
                this.before.showTimer = `${ this.game.userList[this.turn].nickname } Turn`;     
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

            if (this.game.mode == 3) {
                var time = setTimeout( this.turnChange, 1000 );
            } else {
                let formData = new FormData;
                formData.append('inputImage', image);
                formData.append('turn', this.turn);
                formData.append('roomId', this.game.id);

                // ai로 이미지보내기
                aihttp
                .post(`/objects/image/`, formData)
                .then((res) => {
                    if (res.data.message) {
                        for (let i=0; i < res.data.result.length; i++) {
                            if (res.data.result[i] == this.game.word) {
                                if (this.game.mode == 1) {
                                    this.score[this.turn] = this.score[this.turn] - 20;
                                } else if (this.game.mode == 2) {
                                    this.score[this.turn] = this.score[this.turn] - 100;
                                    this.turnFinish();
                                }
                            }
                        }
                    }
                    if (!this.finish) {
                        var time = setTimeout( this.turnChange, 1000 );
                    }
                })
                .catch((err) => {
                    console.log(err);
                })
            }
        },

        // 턴 넘기기
        turnChange() {
            if (this.turn == this.game.cur_count - 1) {
                this.end = true;
            } else {
                this.turn = this.turn + 1;
                this.beforeStartTimer();
            }
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
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
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
    z-index: var(--indexNum);
    bottom: 40;
    left: 0;
    width: 300px;
    height: 400px;
}

.input-group {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 300px;
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
    background-color: rgba(255, 255, 255, .2);
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
    z-index: 999;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .7);
    display: table;
    transition: opacity .3s ease;
}

</style>
<template>
    <div id="PlayGame">
        <!-- 제시어 확인 -->
        <div v-if="checkRoll">
            <roll :room="room" :game="game" />
        </div> 

        <!-- 게임 화면 -->
        <div v-else>
            <!-- 자유 그리기 모드 -->
            <div v-if="isMode">
                <ModeOne :room="room" :output="output" :isMode="isMode" />
            </div>
                
            <!-- 이어그리기 모드 -->
            <div v-else>
                <ModeTwo :room="room" :output="output" :isMode="isMode" />
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

            <div v-if="show" class="showScreen">

            </div>
        </div>

    </div>
</template>

<script>
import roll from '@/components/game/GameRoll.vue';
import ModeOne from '@/components/game/GameModeOne.vue';
import ModeTwo from '@/components/game/GameModeTwo.vue';

export default {
    name: "PlayGame",

    components: {
        roll,
        ModeOne,
        ModeTwo,
    },

    data() {
        return {
            room: {},                           // room 데이터 받아서 넣기 (이후 보고 아예 props로 받기)
            defaultRoom: {                      // 테스트용 default 값
                title: "테스트 중입니다.",
                mode: 1,
                difficulty: 1,
                id: 1,
                count: 8,
                start: false,
                members: [
                    {
                        nickname: '1번사람',
                        id: 1,
                        leader: true,
                    },
                    {
                        nickname: '2번사람',
                        id: 2,
                        leader: false,
                    },
                    {
                        nickname: '3번사람',
                        id: 3,
                        leader: false,
                    },
                    {
                        nickname: '4번사람',
                        id: 4,
                        leader: false,
                    },
                    {
                        nickname: '5번사람',
                        id: 5,
                        leader: false,
                    },
                    {
                        nickname: '6번사람',
                        id: 6,
                        leader: false,
                    },
                    {
                        nickname: '7번사람',
                        id: 7,
                        leader: false,
                    },
                    {
                        nickname: '8번사람',
                        id: 8,
                        leader: false,
                    },
                ],
            },
            // 각 데이터 별로 맞는 설정 이름/숫자 매칭을 위한 리스트
            checkName: {
                mode: [
                '',
                '자유그리기',
                '이어그리기',
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
            isMode: true,                       // 현재 게임 모드 확인 (true: 자유그리기 | false: 이어그리기)
            checkRoll: false,                   // 제시어 확인 페이지 여부
            turn: 3,
            game: { },
            defaultgame: {
                subject: '과일',
                word: '사과',
            },
            show: false,
            chatList: [         // 채팅에 쓸 상용구
                '???',
                '이건 아니지',
                '잘한다',
                'ㅇㅇ',
            ],
            chat: {
                logs: [],
            },
        }
    },

    created() {
        // 이후 넘기는 걸로 받아올 것
        this.room = this.defaultRoom;
        if ( this.room.mode === 1 ) {
            this.isMode = true;
        } else {
            this.isMode = false;
        }

        // 게임 정보 받아오기
        this.game = this.defaultgame;

        // 역할 확인 부분
        // this.checkRoll = true;
        // var timer = setTimeout( this.goGame , 10000);
        this.checkRoll = false;

        // 설정 값 별로 매칭되는 이름/숫자 넣어주기
        this.output.mode = this.checkName.mode[this.room.mode];
        this.output.difficulty = this.checkName.difficulty[this.room.difficulty];
        this.output.sec = this.checkName.sec[this.room.difficulty];

        this.yourTurn();
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
                this.chat.logs = [...[(this.room.members[1].nickname, this.chatList[idx])], ...this.chat.logs];
                console.log(this.chat.logs);
            }
        },

        yourTurn() {
            if (this.room.members[this.turn - 1].id == 1 ) {
                document.documentElement.style.setProperty('--indexNum', -1);
            } else {
        
                document.documentElement.style.setProperty('--indexNum', 99);
            }
        }


    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto:100,300');

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

/* 투표 등 배경 흐리기 해야할 때 */
.showScreen {
    position: fixed;
    z-index: 999;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255, 255, 255, .5);
    display: table;
    transition: opacity .3s ease;
}

</style>
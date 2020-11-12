<template>
    <div id="waitRoom">
        <div v-if="!isSend">
        </div>

        <div v-else>
            <div v-if="!delayMode">
                <!-- 화면 상단 -->
                <div class="screen__top">
                    <!-- 방제목 -->
                    <div class="room__title">
                        [{{ room.id }}]번방 {{ sendTitle }} 
                    </div>
                </div>

                <!-- 화면 왼쪽 -->
                <div class="screen__left">
                    <!-- 입장한 유저 목록 -->
                    <div class="user__part">
                        <user v-for="mem in room.userList" :key="mem.nickname + 'key'" :userData="mem" :window="windowScreen" style="display: inline-block; float:left;" />
                        <empty v-for="n in EmptyCount" :key="n + 'Emptykey'" :window="windowScreen" style="display: inline-block; float:left;"/>
                        <none v-for="n in NoneCount" :key="n + 'Nonekey'" :window="windowScreen" style="display: inline-block; float:left;"/>
                    </div>
                    <!-- 채팅 (상용구) -->
                    <div class="chat__part d-flex justify-content-center">
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

                <!-- 화면 오른쪽 -->
                <div class="screen__right">
                    <!-- 게임 설정 출력 (상중하 / 모드) 방장인 경우에 클릭 가능 -->
                    <div class="setting__part">
                        <mode :mode="room.mode" :isLeader="leader" @modeChange="modeChange" style="margin-bottom: 20px;"/>
                        <difficulty :difficulty="room.difficulty" :isLeader="leader" @difficultyChange="difficultyChange"/>
                        <div v-if="leader" class="mode__button d-flex justify-content-center" style="margin-top: 20px;">
                            <button @click="roomUpdate">게임 모드 수정</button>
                        </div>
                    </div>

                    <!-- 채팅 부분 -->
                    <div class="room__chatting d-flex justify-content-center">
                        <div class="chatting__area">채팅 내용이 나올 부분</div>
                    </div>

                    <!-- 게임 시작 버튼 -->
                    <div class="game__start d-flex justify-content-center align-items-center">
                        <button @click="GameStart" :disabled="!leader">게임 시작</button>
                        <button @click="ExitRoom">방 나가기</button>
                    </div>
                </div>
            </div>

            <!-- game 화면 이전에 로딩 화면 -->
            <div v-else>
                <!-- 자유그리기 모드일 때 -->
                <div v-if="isMode[0]">
                    <loadingOne/>
                </div>
                <!-- 이어그리기 모드일 때 -->
                <div v-if="isMode[1]">
                    <loadingTwo/>
                </div>
                <!-- NO AI 모드일 때 -->
                <div v-if="isMode[2]">
                    <loadingThree/>
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
import loadingOne from '@/components/room/LoadingModeOne.vue';
import loadingTwo from '@/components/room/LoadingModeTwo.vue';
import loadingThree from '@/components/room/LoadingModeThree.vue';
import http from '@/util/http-game.js';
import 'bootstrap/dist/css/bootstrap.min.css';


export default {
    name: 'WaitRoom',
    
    components: {
        user,
        empty,
        none,
        difficulty,
        mode,
        loadingOne,
        loadingTwo,
        loadingThree,
    },

    props: {
        roomId: {
            type: undefined,
        }
    },

    data() {
        return {
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

            // 받아온 모드 값
            checked: {
                mode: 1,
                difficulty: 1,
            },
            leader: false,
            isSend: false,
        }
    },


    created() {
        // 이후엔 요청 보내서 받아올 것
        // this.room = this.defaultroom;

        http
        .get(`game/waitroom/${this.roomId}`)
        .then((res) => {
            this.isSend = true;
            this.room = res.data;
                
            // 빈자리 출력을 위해 인원 확인
            if (this.room.cur_count < this.room.max_count) {
                this.EmptyCount = this.room.max_count - this.room.cur_count;
            }
            // 막아둘 자리 출력을 위한 인원 확인
            if (this.room.max_count < 8) {
                this.NoneCount = 8 - this.room.max_count;
            }
            
            if (this.room.leader.id == 1) {
                this.leader = true;
            } else {
                this.leader = false;
            }
        })
        .catch((err) => {
            console.log(err);
        })

        // 본인 위치 확인 (1 대신에 쿠키에서 본인 pk값 받아올 것)
        // for (let i=0; i < this.room.userList.length; i++) {
        //     if (this.room.userList[i].id === 1) {
        //         this.user = i;
        //     }
        // }

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

    methods: {

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
                this.room.mode = this.checked.mode;
                this.room.difficulty = this.checked.difficulty;
                alert(`방 정보가 수정되었습니다.`)
            })
            .catch((err) => {
                console.log(err);
            })

        },

        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            this.window.height = window.innerHeight;
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
        },

        // 채팅 버튼
        chatMessage() {
            var s = document.getElementById("inputMessageSelect");
            var idx = s.options[s.selectedIndex].value;
            if (this.chatList[idx] === undefined) {
                alert('메시지를 선택해주세요');
            } else {
                // 넣어주는 건 되는데 그래서 어떻게 해당 위치에서만 띄우지....
                console.log(this.chatList[idx]);
            }
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
                // 로딩화면 띄우기
                this.delayMode = true;

                http
                .get(`game/ingame/${this.room.id}`)
                .then((res) => {
                    setTimeout(() => this.$router.replace({ name: 'PlayGame' , params: { sendGame: res.data, roomId: this.room.id }}), 7000);
                })
                .catch((err) => {
                    console.log(err);
                })
            }
        },

        // 방 나가기
        ExitRoom() {
            let user_id = 4;
            http
            .delete(`game/leave/${user_id}`)
            .then((res) => {
                console.log(res.data);
                if (res.data.object != null) {
                    this.room.leader = res.data.object;
                    if (this.room.leader.id == 1) {
                        this.leader = true;
                    } else {
                        this.leader = false;
                    }
                }
            })
            .catch((err) => {
                console.log(err);
            })
        },

        // playgame으로 보내기
        goPlayGame(data) {
            this.$router.replace({ name: 'PlayGame' , params: { sendGame: data }});
        },

        // 선택한 설정값 변경 (모드 | 난이도)
        modeChange(mode) {
            this.checked.mode = Number(mode);
        },
        difficultyChange(difficulty) {
            this.checked.difficulty = Number(difficulty);
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

.chatting__area {
    width: 82%;
    background-color: rgba(255, 255, 255, 0.3);
    padding: 10px 20px;
    border-radius: 10px;
}



</style>
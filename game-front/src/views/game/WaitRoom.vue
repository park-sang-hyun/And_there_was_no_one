<template>
    <div id="waitRoom">
        <!-- 화면 상단 -->
        <div class="screen__top">
            <!-- 방제목 -->
            <div class="room__title">
                [{{ room.id }}]번방 {{ room.title }} 
            </div>
        </div>

        <!-- 화면 왼쪽 -->
        <div class="screen__left">
            <!-- 입장한 유저 목록 -->
            <div class="user__part">
                <user v-for="mem in room.members" :key="mem.nickname + 'key'" :userData="mem" :window="windowScreen" style="display: inline-block;" />
                <none v-for="n in NoneCount" :key="n + 'Nonekey'" :window="windowScreen" style="display: inline-block;"/>
            </div>
            <!-- 채팅 (상용구) -->
            <div class="chat__part d-flex justify-content-center">
                <div class="input-group">
                    <select class="custom-select" id="inputMessageSelect" aria-label="Select Chat phrases">
                        <option selected>채팅 문구 선택</option>
                        <option v-for="(chat, index) in chatList" :value="index" :key="chat + 'chatkey'">{{ chat }}</option>
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
                <mode :mode="room.mode" :isLeader="room.members[user].leader" style="margin-bottom: 20px;"/>
                <difficulty :difficulty="room.difficulty" :isLeader="room.members[user].leader"/>
                <div v-if="room.members[user].leader" class="mode__button d-flex justify-content-center" style="margin-top: 20px;">
                    <button>게임 모드 수정</button>
                </div>
            </div>

            <!-- 채팅 부분 -->
            <div class="room__chatting d-flex justify-content-center">
                <div>이 부분을 뭘로 채워야하나..</div>
            </div>

            <!-- 게임 시작 버튼 -->
            <div class="game__start d-flex justify-content-center align-items-center">
                <button @click="GameStart" :disabled="!room.members[user].leader">게임 시작</button>
                <button @click="ExitRoom">방 나가기</button>
            </div>
        </div>
    </div>
</template>

<script>
import user from '@/components/game/WaitUser.vue'
import none from '@/components/game/NoneUser.vue'
import mode from '@/components/game/modeSetting.vue';
import difficulty from '@/components/game/difficultySetting.vue';

export default {
    name: 'WaitRoom',
    
    components: {
        user,
        none,
        difficulty,
        mode,
    },

    data() {
        return {
            room: {},
            defaultroom: {
                title: '스겜',
                id: 1,
                mode: 1,
                count: 8,
                difficulty: 2,
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
                    // {
                    //     nickname: '5번사람',
                    //     id: 5,
                    //     leader: false,
                    // },
                    // {
                    //     nickname: '6번사람',
                    //     id: 6,
                    //     leader: false,
                    // },
                    // {
                    //     nickname: '7번사람',
                    //     id: 7,
                    //     leader: false,
                    // },
                    // {
                    //     nickname: '8번사람',
                    //     id: 8,
                    //     leader: false,
                    // },
                ],
            },
            user: 0,
            ondata: false,
            window: {
                width: 0,
                height: 0,
            },
            NoneCount: 0,
            chatList: [
                'Ready',
                '시작합시다',
                '잠시만요',
                'ㅇㅇ',
            ],
        }
    },


    created() {
        // 이후엔 요청 보내서 받아올 것
        this.room = this.defaultroom;

        // 빈자리 출력을 위해 인원 확인
        if (this.room.members.length < 8) {
            this.NoneCount = 8 - this.room.members.length;
        }

        // 본인 위치 확인 (1 대신에 쿠키에서 본인 pk값 받아올 것)
        for (let i=0; i < this.room.members.length; i++) {
            if (this.room.members[i].id === 2) {
                this.user = i;
            }
        }

        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
    },

    computed: {
        windowScreen() {
            return this.window
        },

        userData(index) {
            return this.room.members[index]
        },
    },

    watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
    },

    methods: {

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
            console.log('게임을 시작합니다.');
        },

        // 방 나가기
        ExitRoom() {
            console.log('방을 나갑니다.');
        },
    },

}
</script>

<style scoped>
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
    height: 60%;
    padding: 20px;
}

.room__chatting {
    display: block;
    width: 100%;
    height: 20%;
    /* background-color: rgba(255, 255, 255, 0.3); */
    padding: 20px;
    border-radius: 30px;
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
    background-color: #f7ef0b;
    box-shadow: 0px 5px 0px 0px #a0a70e;
    outline: none;
    border-radius: 5px;
    padding: 10px 30px;
    font-size: 1.5rem;
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



</style>
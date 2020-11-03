<template>
    <div id="PlayGame" class="container p-0 m-0">
        <!-- 자유 그리기 모드 -->
        <div v-if="checkMode" class="row">
            <div class="col-8 p-0">
                <!-- 화면 왼쪽 상단 -->
                <div class="row screen__left__top">

                    <div class="row">
                        <div class="room__title">{{ room.title }}</div>
                    </div>
                    <div class="row">
                        <div class="col-3 game__setting">주제 : 과일</div>
                        <div class="col-3 game__setting">난이도 : {{ output.difficulty }}</div>
                        <div class="col-3 game__setting">제시어 : OO</div>
                        <div class="col-3 game__setting">모드 : {{ output.mode }}</div>
                    </div>

                </div>

                <!-- 화면 왼쪽 하단 -->
                <div class="row screen__left__bottom d-flex justify-content-center align-items-center">
                    <!-- canvas(그림 그리기) -->
                    <draw :window="windowScreen"/>
                </div>
            </div>

            <!-- 화면 오른쪽 -->
            <div class="col-4 p-0">
                <div class="row screen__right__top d-flex justify-content-center">
                    유저 목록 등
                </div>
            </div>
        </div>
            

        <!-- 이어그리기 모드 -->
        <div v-else class="row justify-content-center">
            <div class="col-9 p-0 m-0">
                <!-- 화면 왼쪽 상단 -->
                <div class="row screen__left__top">
                    <div class="col-12">
                        <div class="row d-flex justify-content-center">
                            <div class="room__title">{{ room.title }}</div>
                        </div>
                        <div class="row">
                            <div class="col-3 game__setting">주제 : 과일</div>
                            <div class="col-3 game__setting">난이도 : {{ output.difficulty }}</div>
                            <div class="col-3 game__setting">제시어 : OO</div>
                            <div class="col-3 game__setting">모드 : {{ output.mode }}</div>
                        </div>
                    </div>

                </div>

                <!-- 화면 왼쪽 하단 -->
                <div class="row screen__left__bottom d-flex justify-content-center align-items-center">

                    <!-- canvas(그림 그리기) -->
                    <draw :window="windowScreen"/>
                </div>
            </div>

            <!-- 화면 오른쪽 -->
            <div class="col-3 p-0 m-0">
                <div class="row" style="height: 40px;">
                </div> 
                <div class="row screen__right">
                    <user v-for="mem in room.members" :key="mem.nickname + 'key'" :userData="mem" :window="windowScreen" style="display: inline-block;"/>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import draw from '@/components/game/GameDraw.vue'
import user from '@/components/game/PlayUser.vue'

export default {
    name: "PlayGame",

    components: {
        draw,
        user,
    },

    data() {
        return {
            room: {},
            window: {
                width: 0,
                height: 0,
            },
            defaultRoom: {
                title: "테스트 중입니다.",
                mode: 2,
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
            },
            output: {
                mode: '',
                difficulty: '',
            },
            checkMode: true,
        }
    },

    created() {
        // 이후 넘기는 걸로 받아올 것
        this.room = this.defaultRoom;
        if ( this.room.mode === 1 ) {
            this.checkMode = true;
        } else {
            this.checkMode = false;
        }

        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
        this.output.mode = this.checkName.mode[this.defaultRoom.mode];
        this.output.difficulty = this.checkName.difficulty[this.defaultRoom.difficulty];
    },

    watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
    },

    computed: {
        windowScreen() {
            return this.window
        }
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
            var leftSize = 100;
            var rightSize = 100;
            // 자유그리기 | 이어그리기
            if (this.checkMode) {
                leftSize = this.window.width * (8 / 12);
                rightSize = this.window.width * (4 / 12);
            } else {
                leftSize = this.window.width * (9 / 12);
                rightSize = this.window.width * (3 / 12);
            }
            console.log('왼', leftSize);
            console.log('오', rightSize);
            

            document.documentElement.style.setProperty('--leftSize', leftSize + suffix);
            document.documentElement.style.setProperty('--rightSize', rightSize + suffix);
            document.documentElement.style.setProperty('--widthtSize', (this.window.width) + suffix);
            document.documentElement.style.setProperty('--heightSize', (this.window.height) + suffix);
            document.documentElement.style.setProperty('--rightTopSize', (this.window.height - 40) + suffix);
            document.documentElement.style.setProperty('--leftTopSize', (this.window.height * 0.15) + suffix);
            document.documentElement.style.setProperty('--leftBottomSize', (this.window.height * 0.85) + suffix);
        },

    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto:100,300');

/* 가상 선택자, 우선 순위가 제일 높음 */
:root {
    --widthSize: 1000px;
    --leftWidth: 800px;
    --rightWidth: 400px;
    --heightSize: 800px;
    --leftTopSize: 400px;
    --leftBottomSize: 400px;
    --rightTopSize: 760px;
}

#PlayGame {
    height: 100%;
    width: 100%;
    min-width: 1024px;
    /* background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px); */
    color: white;
}

/* 전체 영역 3부분으로 나눔 왼상단 | 왼하단 | 오 */
.screen__left__top {
    display: block;
    width: 100%;
    /* width: var(--leftSize); */
    height: var(--leftTopSize);
    font-weight: bold;
    background-color: beige;
}

.screen__left__bottom {
    display: block;
    width: 100%;
    /* width: var(--leftSize); */
    height: var(--leftBottomSize);
    background-color: rgba(62, 62, 62, 0.5);
}

/* 이어그리기 우측 */
.screen__right {
    display: block;
    width: 100%;
    /* width: var(--rightSize); */
    height: var(--rightTopSize);
    background-color: skyblue;
}
.screen__right__top {
    display: block;
    width: 100%;
    /* width: var(--rightSize); */
    height: var(--rightTopSize);
    background-color: skyblue;
}

/* 개별 스타일 적용 */
.room__title {
    text-align: center;
    font-size: 1.5rem;
    font-weight: bold;
    padding: 10px 0px;
}

.game__setting {
    text-align: center;
    font-weight: bold;
}

/* 타이머 */

</style>
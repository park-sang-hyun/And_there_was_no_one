<template>
    <div id="PlayGame container p-0">
        <div class="row justify-content-center">
            <div class="col-8">
                <div class="row">

                    <!-- 화면 왼쪽 상단 -->
                    <div class="screen__left__top">
                        <div class="room__title">{{ defaultRoom.title }}</div>
                        <div class="row">
                            <div class="col-3 game__setting">주제 : 과일</div>
                            <div class="col-3 game__setting">난이도 : {{ output.difficulty }}</div>
                            <div class="col-3 game__setting">제시어 : OO</div>
                            <div class="col-3 game__setting">모드 : {{ output.mode }}</div>
                        </div>
                    </div>

                </div>

                <div class="row">
                    <!-- 화면 왼쪽 하단 -->
                    <div class="screen__left__bottom d-flex justify-content-center align-items-center">

                        <!-- canvas(그림 그리기) -->
                        <draw :window="windowScreen"/>
                    </div>
                </div>
            </div>

            <!-- 화면 오른쪽 -->
            <div class="col-4 p-0">
                <div class="screen__right d-flex justify-content-center">
                    유저 목록 등
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import draw from '@/components/game/GameDraw.vue'

export default {
    name: "PlayGame",

    components: {
        draw,
    },

    data() {
        return {
            window: {
                width: 0,
                height: 0,
            },
            defaultRoom: {
                title: "퇴근 후 한판 ㄱㄱ",
                mode: 1,
                difficulty: 1,
            },
            checkName: {
                mode: [
                '',
                '이어 그리기',
                '각자 그리기',
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
            }
        }
    },

    created() {
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
            this.window.width = window.innerWidth;
            this.window.height = window.innerHeight;
            this.layoutCal();
        },

        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            var leftSize = this.window.width * (8 / 12);
            var rightSize = this.window.width * (4 / 12);
            document.documentElement.style.setProperty('--leftSize', leftSize + suffix);
            document.documentElement.style.setProperty('--rightSize', rightSize + suffix);
            document.documentElement.style.setProperty('--heightSize', (this.window.height) + suffix);
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
    --leftWidth: 800px;
    --rightWidth: 400px;
    --heightSize: 800px;
    --leftTopSize: 400px;
    --leftBottomSize: 400px;
}

#PlayGame {
    height: 100%;
    width: 100%;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    color: white;
}

/* 전체 영역 3부분으로 나눔 왼상단 | 왼하단 | 오 */
.screen__left__top {
    display: block;
    width: var(--leftSize);
    height: var(--leftTopSize);
    font-weight: bold;
    /* background-color: beige; */
}

.screen__left__bottom {
    display: block;
    width: 100%;
    height: var(--leftBottomSize);
    /* background-color: rgba(62, 62, 62, 0.5); */
}

.screen__right {
    display: block;
    width: var(--rightSize);
    height: var(--heightSize);
    /* background-color: skyblue; */
}

/* 개별 스타일 적용 */
.room__title {
    text-align: center;
    font-size: 1.5rem;
    font-weight: bold;
    margin: 10px auto;
}

.game__setting {
    text-align: center;
    font-weight: bold;
}

/* 타이머 */

</style>
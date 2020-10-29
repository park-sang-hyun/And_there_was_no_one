<template>
    <div id="waitRoom">
        <!-- 화면 상단 -->
        <div class="screen__top">
            <!-- 방제목 -->
            <div class="room__title">
                [001]번방 {{ defaultroom.title }} 
            </div>
        </div>

        <!-- 화면 왼쪽 -->
        <div class="screen__left">
            <!-- 입장한 유저 목록 -->
            <div class="user__part">
                <user v-for="mem in defaultroom.members" :key="mem.nickname + 'key'" :userData="mem" :window="windowScreen" style="display: inline-block;"/>
            </div>
        </div>

        <!-- 화면 오른쪽 -->
        <div class="screen__right">
            <!-- 게임 설정 출력 (상중하 / 모드) -->
            <div class="setting__part">
                <mode/>
                <difficulty/>
            </div>

            <!-- 방장인 경우 클릭 가능 -->
            <div class="room__setting">
                방장 설정 변경 부분
            </div>

            <!-- 게임 시작 버튼 -->
            <div class="game__start d-flex justify-content-center align-items-center">
                <div class="btn btn-primary start-button">게임 시작</div>
            </div>
        </div>
    </div>
</template>

<script>
import user from '@/components/game/WaitUser.vue'
import mode from '@/components/game/modeSetting.vue';
import difficulty from '@/components/game/difficultySetting.vue';

export default {
    name: 'WaitRoom',
    
    components: {
        user,
        difficulty,
        mode,
    },

    data() {
        return {
            room: {},
            defaultroom: {
                title: '스겜',
                count: 8,
                difficulty: 1,
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
            ondata: false,
            window: {
                width: 0,
                height: 0,
            },
        }
    },


    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
    },

    computed: {
        windowScreen() {
            return this.window
        }
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
            document.documentElement.style.setProperty('--leftSize', (this.window.width * 0.6) + suffix);
            document.documentElement.style.setProperty('--rightSize', (this.window.width * 0.4) + suffix);
            document.documentElement.style.setProperty('--mainSize', (this.window.height - 60) + suffix);
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
    min-width: 1000px;
}


/* 전체 영역 3부분으로 나눔 상단 | 왼 | 오 */
.screen__top {
    width: var(--widthSize);
    height: 60px;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
    line-height: 60px;
    background-color: beige;
}

.screen__left {
    display: inline-block;
    width: var(--leftSize);
    height: var(--mainSize);
    background-color: aquamarine;
}

.screen__right {
    display: block;
    float: right;
    width: var(--rightSize);
    height: var(--mainSize);
    background-color: skyblue;
}


/* 레이아웃 왼쪽 */
.user__part {
    width: 100%;
}

/* 레이아웃 오른쪽 */
.setting__part {
    display: block;
    width: 100%;
    height: 50%;
}

.room__setting {
    display: block;
    width: 100%;
    height: 30%;
}

.game__start {
    display: block;
    width: 100%;
    height: 20%;
}


/* 개별 style */


/* 버튼 */
.start-button {
    display: block;
    width: 80%;
    height: 50%;
}

</style>
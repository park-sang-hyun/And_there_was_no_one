<template>
    <!-- 이어그리기 -->
    <div id="GameModeTwo" class="row">
        <div class="col-9 p-0 m-0">

            <!-- 화면 왼쪽 상단 -->
            <div class="row screen__left__top d-flex justify-content-center">
                <!-- 게임 주제, 제시어 -->
                <div class="game__title">
                    <span>주제: {{ game.topic }}</span>
                    <span class="ml-4">제시어: {{ showWord }}</span>
                </div>
                <!-- 게임 모드, 난이도 -->
                <div class="game__mode">
                    <span>{{ output.mode }}</span>
                    <span class="ml-2">{{ output.difficulty }}</span>
                </div>
            </div>

            <!-- 화면 왼쪽 하단 -->
            <div class="screen__left__bottom d-flex justify-content-end align-items-center">
                <div class="p-0 d-flex justify-content-end align-items-end">
                    <!-- canvas(그림 그리기) -->
                    <draw :window="windowScreen" :turnOff="!turnOff" @imgFile="imgFile" ref="draw" style="margin-right: 20%;"/>
                </div>
            </div>
            <div v-if="selectCanvas" class="screen__left__block"></div>

        </div>

        <!-- 화면 오른쪽 -->
        <div class="col-3 p-0 m-0">
            <!-- 유저 리스트 -->
            <div class="row screen__right">
                <div v-for="n in 8" :key="'user' + n + 'key'" style="display: inline-block;" class="d-flex justify-content-center">
                    <user v-if="n < game.userList.length + 1" :userData="game.userList[n-1]" :window="windowScreen" :isMode="isMode" :isTurn="isYourTurn" :memNo="n"/>
                    <empty v-else-if="n < game.userList.length + memCount.EmptyCount + 1"  :window="windowScreen" :isMode="isMode" />
                    <none v-else :window="windowScreen" :isMode="isMode" />
                </div>

                <div class="exit__button d-flex justify-content-center align-items-center">
                    <button @click="exitRoom">방 나가기</button>
                </div> 
            </div>
        </div>
    </div>
</template>

<script>
import draw from '@/components/game/GameDraw.vue';
import user from '@/components/game/PlayUser.vue';
import empty from '@/components/game/EmptyUser.vue';
import none from '@/components/game/NoneUser.vue';


const storage = window.sessionStorage;


export default {
    name: "GameModeTwo",

    props: {
        game: {
            type: Object
        },
        output: {
            type: Object
        },
        turnOff: {
            type: Boolean,
            default: true,
        },
        isMode: {
            type: Boolean,
            default: false,
        },
        isTurn: {
            type: Number,
            default: 1,
        },
        memCount: {
            type: Object
        },
    },

    components: {
        draw,
        user,
        empty,
        none,
    },

    data() {
        return {
            window: {                           // 현재 보이는 창 너비
                width: 0,
                height: 0,
            },
            showWord: '',
            selectCanvas: false,
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        for (let i=0; i < this.game.word.length; i++) {
            this.showWord = this.showWord + 'O';
        }

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
        },
        isYourTurn() {
            return this.isTurn
        },
    },

    methods: {
        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            this.window.height = (window.innerHeight < 724) ? 724 : window.innerHeight;
            this.layoutCal();
        },
       
        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            var leftSize = this.window.width * (9 / 12);
            var rightSize = this.window.width - leftSize;

            document.documentElement.style.setProperty('--leftSize', leftSize + suffix);
            document.documentElement.style.setProperty('--rightSize', rightSize + suffix);
            document.documentElement.style.setProperty('--heightSize', (this.window.height) + suffix);
            document.documentElement.style.setProperty('--rightTopSize', (this.window.height - 40) + suffix);
            document.documentElement.style.setProperty('--leftTopSize', (this.window.height * 0.1) + suffix);
            document.documentElement.style.setProperty('--leftBottomSize', (this.window.height * 0.9) + suffix);
        },
        // 본인의 턴이면 그림 그리기를 할 수 있도록, 아니면 못하도록 막기
        yourTurn() {
            if (this.isTurn != 0) {
                if (this.game.userList[this.isTurn-1].id == storage.getItem('id')) {
                    this.selectCanvas = false;
                } else {
                    this.selectCanvas = true;
                }

            }
        },

        exitRoom() {
            alert('게임 중에는 나갈 수 없습니다.');
        },

        imgFile(image) {
            this.$emit('imgFile', image);
        },
    },

}
</script>

<style lang="scss" scoped>
/* 가상 선택자, 우선 순위가 제일 높음 */
:root {
    --leftWidth: 800px;
    --rightWidth: 400px;
    --heightSize: 800px;
    --leftTopSize: 400px;
    --leftBottomSize: 400px;
    --rightTopSize: 760px;
}

/* 전체 영역 3부분으로 나눔 왼상단 | 왼하단 | 오 */
.screen__left__top {
    display: block;
    position: relative;
    width: var(--leftSize);
    height: var(--leftTopSize);
    font-weight: bold;
    margin: 0px;
    /* background-color: beige; */
}

.screen__left__bottom {
    display: block;
    position: relative;
    width: var(--leftSize);
    height: var(--leftBottomSize);
    margin: 0px;
    padding: 0px;
    /* background-color: rgba(62, 62, 62, 0.5); */
}

.screen__left__block {
    position: absolute;
    left: 0;
    bottom: 40px;
    width: var(--leftSize);
    height: calc(var(--leftBottomSize) - 40px);
    z-index: 11;
}

/* 이어그리기 우측 */
.screen__right {
    display: block;
    width: var(--rightSize);
    height: var(--rightTopSize);
    margin: 40px 0px 0px 0px;
    /* background-color: skyblue; */
}

/* 개별 스타일 적용 */
/* 왼편 상단 */
.game__title {
    text-align: center;
    font-size: 1.5rem;
    font-weight: bold;
    line-height: var(--leftTopSize);
}

.game__mode {
    position: absolute;
    left: 20px;
    top: 50%;
    transform: translate(0, -50%);
}

.game__mode > span {
    padding: 5px;
    border-radius: 10px;
    background-color: rgba(250, 181, 33, 0.789);
}

.game__subject {
    position: absolute;
    top: 0px;
    left: 20px;
}

.game__setting {
    font-weight: bold;
}

/* 나가기 버튼 */
.exit__button {
    height: 80px;
}

/* button {
    border-radius: 10px;
    padding: 10px;
    width: 80%;
    background-color: rgba(236, 172, 33, 0.857);
    color: black;
    font-size: 1.1rem;
    border: none;
} */


.exit__button > button {
    border: none;
    background-color: #f7130b;
    box-shadow: 0px 5px 0px 0px #b12208;
    outline: none;
    border-radius: 5px;
    padding: 10px;
    width: 80%;
    font-size: 1.2rem;
    text-decoration: none;
    color: rgba(0, 0, 0, 0.4);
    font-weight: bold;
    position: relative;
    display: inline-block;
    cursor: pointer;
}

.exit__button > button:active {
    transform: translate(0px, 5px);
    -webkit-transform: translate(0px, 5px);
    box-shadow: 0px 1px 0px 0px;
}
</style>
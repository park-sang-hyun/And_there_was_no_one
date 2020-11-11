<template>
    <div id="EndScreen">
        <!-- 게임이 완전히 종료된 상황 -->
        <div v-if="result">

        </div>
        <!-- 게임이 완전히 종료되지 않았을 때 -->
        <div v-else>

            <div v-if="showNumber == 0" class="main__story">
                <h1 class="zero__middle">모든 턴이 종료되었습니다.</h1>
            </div>

            <div v-if="showNumber == 1" class="main__story">
                <h1 class="zero__middle">지금부터 투표를 시작하겠습니다.</h1>
            </div>

            <div v-if="showNumber == 2" class="main__story">
                <h1 style="margin-top: 30px; margin-bottom: 30px; font-size: 2rem;">Shadow라 생각하는 사람에게 투표해주세요.</h1>
                <div class="d-flex justify-content-center" 
                    style="inline-block;"
                >
                    <div v-for="n in userList.length" :id="n-1" :key="n + 'key'" 
                        class="select__part"
                        @click="clickChange">
                        <img :src="images[n-1]" 
                            alt="image" 
                            class="image__showpart" >
                        <div class="user__part">{{ userList[n-1].nickname }}</div>
                        <div :class="{selectUser: isClick[n-1]}">
                            <span style="display:none">{{ selectNumber }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "EndScreen",

    props: {
        endScore: {
            type: Array,
        },
        userList: {
            type: Array,
        },
        isEnd: {
            type: Boolean,
        },
        isFinish: {
            type: Boolean,
        },
        images: {
            type: Array,
        },
    },

    data() {
        return {
            window: {                           // 현재 보이는 창 너비
                width: 0,
                height: 0,
            },
            result: false,
            show: [
                true, false, false, false,
            ],
            isClick: [],
            showNumber: 0,
            showTimer: '',
            selectNumber: null,
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        // 처음에 사람 수만큼 채워두기
        for (let step = 0 ; step < this.userList.length; step++) {
            this.isClick.push(false)
        }

        
        // 게임 종료 여부 확인
        if (this.isFinish) {
            this.result = true;
        } else {
            this.result = false;
        }
        //  턴 넘기기
        this.showTimer = setInterval(this.showPage, 3000);
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

        sendEnd() {
            this.endStart();
            return this.isEnd
        }
    },

    methods: {

        clickChange(event) {
            // id 값을 받아온다. 만약 자식 요소라면 부모요소로 접근해서 id값 가져오기
            var idx = event.target.id
            if (!event.target.id) {
                idx = event.target.parentElement.id
            }

            this.isClick[idx] = true;

            // id 값과 동일한 subjects의 인덱스 값이 해당 부분의 value이므로 받아서 넣어준다.
            if (idx != this.selectNumber) {
                var beforeidx = this.selectNumber;
                this.isClick[beforeidx] = false
                this.selectNumber = Number(idx)
            } else {
                this.selectNumber = Number(idx)
            }
        },

        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            this.window.height = window.innerHeight;
            let suffix = 'px';
            var num = this.userList.length % 4;
            if (num == 0) {
                num = 4;
            }
            var selectWidthSize = ((this.window.width - 40) / num);
            var selectHeightSize = ((this.window.height - 80) / 2)
            var imageSize = selectWidthSize - 20;
            if (imageSize > selectHeightSize - 20) {
                imageSize = selectHeightSize - 20;
            }
            document.documentElement.style.setProperty('--widthSize', this.window.width + suffix);
            document.documentElement.style.setProperty('--heightSize', this.window.height + suffix);
            document.documentElement.style.setProperty('--selectWidthSize', selectWidthSize + suffix);
            document.documentElement.style.setProperty('--selectHeightSize', selectHeightSize + suffix);
            document.documentElement.style.setProperty('--imageSize', imageSize + suffix)
        },

        // 페이지 넘기기
        showPage() {
            if (this.showNumber == 0) {
                this.show[this.showNumber] = true;
                this.showNumber = this.showNumber + 1;
            } else if (this.showNumber < 2) {
                this.show[this.showNumber - 1] = false;
                this.show[this.showNumber] = true;
                this.showNumber = this.showNumber + 1;
            } else {
                clearInterval(this.showTimer);
            }
        },
    }

}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&family=Oswald&display=swap');

:root {
    --widthSize: 1024px;
    --heightSize: 728px;
    --selectWidthSize: 240px;
    --selectHeightSize: 300px;
    --imageSize: 100px;
}

#EndScreen {
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
}

.main__story {
    clear: both; 
    position: relative;
    width: 100%;
    height: 100%;
    margin: auto;
    font-size:20px;
    font-family: 'Open Sans', sans-serif, 'Oswald', sans-serif;
    margin: 0;
    opacity:0;
    transform:translateY(1em);
    animation:page-in ease-out 3s;
    animation-fill-mode:forwards;
}

.zero__middle {
    line-height: var(--heightSize);
}

.screen__middle {
    padding: auto 0;
}

h1 {
    font-family: sans-serif;
    font-weight: bold;
    text-align: center;
}

.image__showpart {
    width: var(--imageSize);
    height: var(--imageSize);
    margin: 0 auto;
}

.select__part {
    clear: both;
    display: flex;
    justify-content: center;
    align-content: center;
    position: relative;
    width: var(--selectWidthSize);
    height: var(--selectHeightSize);
}
.selectUser {
    position: absolute;
    left: 50%;
    top: 0;
    transform: translateX(-50%);
    width: var(--imageSize);
    height: var(--imageSize);
    background-color: rgba(255, 0, 0, 0.5);
}

.user__part {
    position: absolute;
    top: 5%;
    left: 50%;
    transform: translateX(-50%);
    border-radius: 10px;
    padding: 5px;
    background-color: gray;
}

@keyframes page-in {
    from {
        transform:translateY(1em);
        opacity:0;
    }
    to {
        transform:translateY(0);
        opacity:1;
    }
}



</style>
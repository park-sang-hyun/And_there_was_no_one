<template>
    <!-- 역할 분담 (제시어) -->
    <div id="GameRoll" class="d-flex justify-content-center align-items-center">

        <div v-if="isStart" class="sub__part">
            이제 제시어를 확인하겠습니다
        </div>

        <div v-else>
            <!-- 카드 -->
            <section id="sectionCard">
                <!-- 빈 화면 -->
                <div class="front">
                </div>

                <!-- 주제 | 제시어 화면 -->
                <div class="back">
                    <div class="game__subject">
                        <h3>주제</h3>
                        <p>{{ sendGame.topic }}</p>
                    </div>
                    <div class="game__word">
                        <h3>제시어</h3>
                        <p v-if="isShadow">You are Shadow</p>
                        <p v-else>{{ sendGame.word }}</p>
                    </div>
                </div>
            </section>
        </div>
        
    </div>
</template>

<script>

const storage = window.sessionStorage;


export default {
    name: "GameRoll",

    props: {
        sendGame: {
            type: Object,
        }

    },

    data() {
        return {
            isStart: true,
            isShadow: false,
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();
        this.isStart = true;

        if ( this.sendGame.shadow.id == storage.getItem('id')) {
            this.isShadow = true;
        } else {
            this.isShadow = false;
        }
        
        setTimeout( this.start , 4000);
    },

    computed: {
        callTurnOn() {
            return this.turnOn
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
            var width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            var height = window.innerHeight;
            let suffix = 'px';
            document.documentElement.style.setProperty('--widthSize', width + suffix);
            document.documentElement.style.setProperty('--heightSize', height + suffix);
        },

        start() {
            this.isStart = false;
        }

    },
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Open+Sans&family=Oswald&display=swap');

:root {
    --widthSize: 400px;
    --heightSize: 400px;
}

#GameRoll {
    font-family: 'Open Sans', sans-serif, 'Oswald', sans-serif;
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    overflow: inherit; 
}

// start 부분
.sub__part {
    position: sticky;
    color: rgb(165, 165, 165);
    font-size: 3rem;
    font-weight: bold;
    transform:translateY(1em);
    // animation stuff
    animation:page-in ease-out 3s;
    animation-fill-mode:forwards;
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



// card
section {
    position: relative;
    width: 325px;
    height: 420px; 
    margin: 5% auto;
    transform-style: preserve-3d;
    transition: transform 0.5s ease-in-out;
    // 카드 돌리기 위한 부분 (로테이션, 반복횟수)
    -webkit-animation: rotating 0.5s linear;
    animation-iteration-count: 3;
}

@-webkit-keyframes rotating {
    from{
        -webkit-transform: rotateY(0deg);
    }
    to{
        -webkit-transform: rotateY(360deg);
    }
}

section > div {
    width: 100%;
    height: 100%;
    text-align: center;
    position: absolute;
    top: 0;
    left: 0;
    border-radius: 4%;
    box-shadow: 0px 0px 20px rgba(0, 0, 0, .5);
    background-color: #252c3e;
    color: white;
    line-height: 1.4;
}

// 빈화면..
.front {
    z-index: 2;
    backface-visibility: hidden;
    transform: rotateY(180deg);
    text-align: center; 
    line-height: 420px;
}

// 주제, 제시어 부분
.back {
    position: relative;
    z-index: 3;
    overflow: hidden;
    backface-visibility: hidden;
}

.game__subject {
    position: absolute;
    top: 0px;
    left: 50%;
    transform: translateX(-50%);
    font-weight: bold;
}

.game__word {
    position: absolute;
    top: 210px;
    left: 50%;
    transform: translateX(-50%);
    font-weight: bold;
}

.back > div > h3 {
    margin: 30px 0px;
}

.back > div > p {
    font-size: 3rem;
    font-family: 'Cute Font', cursive, 'Gaegu', cursive;
}



</style>
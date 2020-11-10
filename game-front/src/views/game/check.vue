<template>
    <!-- 역할 분담 (제시어) -->
    <div id="GameRoll" class="d-flex justify-content-center align-items-center">

        <!-- 카드 -->
        <section id="sectionCard">
            <!-- 빈 화면 -->
            <div class="front">
            </div>

            <!-- 주제 | 제시어 화면 -->
            <div class="back">
                <div class="game__subject">
                    <h3>주제</h3>
                    <p>{{ game.subject }}</p>
                </div>
                <div class="game__word">
                    <h3>제시어</h3>
                    <p>{{ game.word }}</p>
                </div>
            </div>
        </section>
        
    </div>
</template>

<script>
export default {
    name: "GameRoll",

    data() {
        return {
            game: { },
            defaultgame: {
                subject: '과일',
                word: '사과',
            }
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        this.game = this.defaultgame;
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

    },
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Cute+Font&family=Gaegu&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Poor+Story&family=Stylish&display=swap');

:root {
    --widthSize: 400px;
    --heightSize: 400px;
}

#GameRoll {
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    min-width:1024px;
    background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    overflow: inherit; 
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
    font-family: 'Nanum Gothic', 'Poor Story', cursive, 'Stylish', sans-serif;
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
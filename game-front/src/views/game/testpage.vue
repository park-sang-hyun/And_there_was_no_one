<template>
    <div id="testpage">
        <!-- 중앙 정렬위한 빈 공간 -->
        <div id="floater"></div>
        <div class="main__story">
            <h1 class="end__h1"><span :class="{ h1Win: didYouWin, h1Lose: !didYouWin}">{{ gameSentence }}</span></h1>
            <p class="end__p">{{ firstSentence }}</p>
            <p class="end__p">{{ secondSentence }}</p>
            <!-- 점수 (공통) / 정중앙에 출력 -->
            <div class="score js-score">
                {{ isScore }}
            </div>
        </div>

    </div>
</template>

<script>
export default {
    name: "testpage",

    data() {
        
        return {
            game: {
                score: 100,
            },
            window: {                           // 현재 보이는 창 너비
                width: 0,
                height: 0,
            },
            myScore: -40,
            gameSentence: 'You Win!',
            firstSentence: '탐정들이 Shadow를 검거했습니다.',
            secondSentence: '당신은 탐정에게 투표했습니다.',
            total: 0,
            score: 100,
            scoreTimer: null,
            didYouWin: true,
        }

    },
    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        setTimeout(this.changeScore, 1000);
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

        isScore() {
            return this.score;
        }
    },

    methods: {
        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = (window.innerWidth < 1024) ? 1024 : window.innerWidth;
            this.window.height = (window.innerHeight < 724) ? 724 : window.innerHeight;
            let suffix = 'px';

            document.documentElement.style.setProperty('--widthSize', this.window.width + suffix);
            document.documentElement.style.setProperty('--heightSize', this.window.height + suffix);
        },
                
        changeScore() {
            this.score = this.game.score;
            this.total = this.score + this.myScore;
        
            // 점수 올라가기
            if (this.myScore > 0) {
                this.scoreTimer = setInterval(() => {
                        this.score++;
                        if (this.score == this.total) {
                            clearInterval(this.scoreTimer);
                            document.querySelector('.js-score').classList.add("animated", "bounceIn");
                        }
                    }, 10); 
            } else {
                this.scoreTimer = setInterval(() => {
                    this.score--;
                    if (this.score == this.total) {
                        clearInterval(this.scoreTimer);
                        document.querySelector('.js-score').classList.add("animated", "bounceIn");
                    }
                }, 10); 
            }
        }
    }
}
</script>

<style lang="scss" scoped>

:root {
    --widthSize: 1024px;
    --heightSize: 728px;
}


#testpage {
    position: fixed;
    left: 0;
    top: 0;
    width: var(--widthSize);
    height: var(--heightSize);
    // background: repeating-linear-gradient(-45deg, rgb(33, 33, 33), rgb(33, 33, 33) 1px, rgb(10, 10, 10) 0, rgb(10, 10, 10) 10px);
    background: black;
    overflow: inherit; 
}

// 중앙 정렬 위한 floater
#floater {
    float: left; 
    height: 45%; 
    margin-bottom: -120px;
}

.main__story {
    clear: both; 
    position: sticky;
    margin: auto;
    color: white;
    font-size:20px;
    line-height:1.6em;
    font-family: 'Open Sans', sans-serif, 'Oswald', sans-serif;
    // font-family:sans-serif;
    margin: 0;
    // attributes to accompany the animation
    opacity:0;
    position:relative;
    transform:translateY(1em);
    // animation stuff
    animation:page-in ease-out 3s;
    animation-fill-mode:forwards;
    text-align: center;
}

.end__h1 {
    font-family: sans-serif;
    font-weight: bold;
}

.h1Win {
    color: rgb(54, 181, 12);
}

.h1Lose {
    color: rgb(255, 0, 0);
}

.end__p {
    margin: 20px 0px;
    color: white;
    font-size: 1.2rem;
}

.bold__part {
    // font-family: 'Averia Gruesa Libre', cursive, 'Averia Sans Libre', 'Open Sans', sans-serif;
    color: rgb(54, 181, 12);
    font-weight: bold;
}

.score {
    margin-top: 60px;
    font-size: 3rem;
    font-weight: bold;
    color: white;
}

.js-score {
    color: white;
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
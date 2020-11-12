<template>
    <div id="EndScreen">
        <!-- 게임이 완전히 종료된 상황 -->
        <div v-if="result" class="main__story">
            <!-- shadow인 경우 -->
            <div v-if="youShadow">
                <h1>당신이 이번 판의 Shadow 였습니다.</h1>
                <div v-if="loseShadow">
                    <h1>You Lose..</h1>
                </div>
                <div v-else>
                    <h1>You Win!</h1>
                </div>
            </div>

            <!-- 일반 유저인 경우 -->
            <div v-else  class="main__story">
                <div v-if="loseShadow">
                    <h1>You Win!</h1>
                </div>
                <div v-else>
                    <h1>You Lose..</h1>
                </div>

            </div>
        </div>

        <!-- 게임이 완전히 종료되지 않았을 때 -->
        <div v-else>

            <div v-if="showNumber == 0" class="main__story">
                <h1 class="zero__middle">모든 턴이 종료되었습니다.</h1>
            </div>

            <div v-if="showNumber == 1" class="main__story">
                <h1 class="zero__middle">지금부터 투표를 시작하겠습니다.</h1>
            </div>

            <!-- 투표 부분 -->
            <div v-if="showNumber == 2" class="main__story">
                <h1 style="margin-top: 30px; margin-bottom: 30px; font-size: 2rem;">{{ sentence }}</h1>
                <div class="d-flex justify-content-center" 
                    style="inline-block;">
                    <div v-for="n in game.userList.length" :id="n-1" :key="n + 'key'" 
                        class="select__part"
                        @click="clickChange">
                        <img :src="images[n-1]" 
                            alt="image" 
                            class="image__showpart" >
                        <div class="user__part">{{ game.userList[n-1].nickname }}</div>
                        <div :class="{selectUser: isClick[n-1]}">
                            <span style="display:none">{{ selectNumber }}</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 투표 로딩 -->
            <div v-if="showNumber == 3" class="main__story">
                <h1 class="zero__middle">투표 결과를 받아오고 있습니다.</h1>
            </div>

            <!-- 투표 결과 -->
            <div v-if="showNumber == 4" class="main__story">
                <h1 style="margin-top: 30px; margin-bottom: 30px; font-size: 2rem;">가장 많은 표를 받은 Shadow 후보입니다.</h1>
                <div class="d-flex justify-content-center" 
                    style="inline-block;">
                    <div v-for="m in voteUser" :key="m + 'votekey'" 
                        class="select__part">
                        <img :src="images[m]" 
                            alt="image" 
                            class="image__showpart" >
                        <div class="user__part">{{ game.userList[m].nickname }}</div>
                    </div>
                </div>
                <div v-if="showVoteResult" class="last__text">
                    {{ finishSentence }}
                </div>
            </div>

        </div>
        
        <div style="display: none;">{{ isGameFinish }}</div>
    </div>
</template>

<script>
import http from '@/util/http-game.js';

const storage = window.sessionStorage;


export default {
    name: "EndScreen",

    props: {
        endScore: {
            type: Array,
        },
        game: {
            type: Object,
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
                true, false, false, false, false, false,
            ],
            isClick: [],
            showNumber: 0,
            showTimer: '',
            selectNumber: null,
            sentence: 'Shadow라 생각하는 사람에게 투표해주세요.',
            counter: false,
            interval: '',
            timer: 8,
            voteUser: [],
            loseShadow: false,
            youShadow: false,
            showVoteResult: false,
            finishSentence: '',
        }
    },

    created() {
        // 보이는 화면 크기 확인
        window.addEventListener('resize', this.screenResize);
        this.screenResize();

        // 처음에 사람 수만큼 채워두기
        for (let step = 0 ; step < this.game.userList.length; step++) {
            this.isClick.push(false)
        }

        // shadow 여부 확인
        if (this.game.shadow.id == storage.getItem('id')) {
            this.youShadow = true;
        }

        
        // 게임 종료 여부 확인
        if (this.isFinish) {
            this.result = true;
            this.loseShadow = false;
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
        },

        isGameFinish() {
            this.goWaitRoom();
            return this.result
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
            var num = this.game.userList.length % 4;
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
            } else if (this.showNumber == 1) {
                this.show[this.showNumber - 1] = false;
                this.show[this.showNumber] = true;
                this.showNumber = this.showNumber + 1;
            } else if (this.showNumber == 2) {
                clearInterval(this.showTimer);
                this.startTimer();
            }
        },

        // 타이머
        startTimer() {
            this.interval = setInterval(this.countDown, 1000);
        },

        countDown() {
            var n = this.timer;
            if (!this.counter) {
                this.counter = true;   
            } else if (n > 1) {
                n = n - 1;
                this.timer = n;
                if (n < 4) {
                    this.sentence = `투표 종료 ${n}초 전입니다.`;
                }
            } else {
                clearInterval(this.interval);
                this.counter = false;
                this.timer = 8;
                this.sentence = '투표가 종료되었습니다.';

                this.finishVote();
            }
        },

        // 투표한 값 백으로 보내기
        finishVote() {
            // showNumber == 3
            this.showNumber = this.showNumber + 1;
            this.show[this.showNumber - 1] = false;
            this.show[this.showNumber] = true;

            var formData = new FormData;

            if (this.selectNumber != null) {
                // 특정 유저 투표(nickname) 보냄
                formData.append('who', this.game.userList[this.selectNumber].nickname);

            } else {
                // 기권
                formData.append('who', null);

            }


            http
            .post(`vote/vote/${this.game.id}`, formData)
            .then((res) => {
                setTimeout(this.voteResult, 4000);
            
            })            
            .catch((err) => {
                console.log(err);
            })
            // setTimeout(this.nextVote, 4000);

        },

        // 투표 결과 받아오기
        voteResult() {

            http
            .get(`vote/voteResult/${this.game.id}`)
            .then((res) => {
                for (let userNum=0; userNum < res.data.length; userNum++ ) {
                    for (let checkNum=0; checkNum < this.game.userList.length; checkNum++ ) {
                        console.log(res.data[userNum].who);
                        console.log(this.game.userList[checkNum].nickname);
                        if (res.data[userNum].who == this.game.userList[checkNum].nickname) {
                            this.voteUser.push(checkNum);
                        }
                    }
                }
                this.nextVote();
            })
            .catch((err) => {
                console.log(err);
            })

        },

        // 연결 후에는 finishVote에서 res.data 받는 부분으로 바꿀 것
        nextVote() {
            // showNumber == 4
            // this.voteUser.push(this.selectNumber);
            this.showNumber = this.showNumber + 1;
            this.show[this.showNumber - 1] = false;
            this.show[this.showNumber] = true;
            
            setTimeout(this.resultCheck, 4000);
        },

        // 맞는지 확인
        resultCheck() {
            this.loseShadow = false;

            if (this.voteUser.length == 1) {
                if (this.game.shadow.nickname == this.game.userList[this.voteUser[0]].nickname) {
                    this.loseShadow = true;
                    this.finishSentence = 'Shadow를 찾았습니다';
                } else {
                    this.finishSentence = 'Shadow를 찾지 못했습니다.';
                }
            } else {
                this.finishSentence = 'Shadow가 존재하지 않습니다.';
                for (let k; k < this.voteUser.length; k++) {
                    if (this.game.shadow.nickname == this.game.userList[this.voteUser[k]].nickname) {
                        this.finishSentence = 'Shadow가 존재하지만 검거하진 못했습니다.';
                    }
                }
            }

            this.showVoteResult = true;

            setTimeout(this.finishGame, 4000);
        },

        finishGame() {
            this.result = true; 
        },

        goWaitRoom() {
            if (this.result == true) {
                setTimeout(() => this.$router.replace({ name: 'WaitRoom', params: { roomId: this.game.id }}), 7000);
            }
        }

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

.last__text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    padding: 20px;
    font-size: 2rem;
    background-color: red;
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
<template>
    <!-- 대기 방 User -->
    <div id="WaitUser container">

        <div class="user__part row" @click="clickUser">
            <div style="display: none;">{{ nowWindow }}</div>
            <div class="user__left col-5 d-flex justify-content-center align-items-center p-0">
                <!-- user 왼편 래더 or 프로필? -->
                <div class="user__image">
                    <img v-if="isLeader" src="@/assets/images/detective.png" alt="user image">
                    <img v-else src="@/assets/images/detective.png" alt="user image">
                </div>
            </div>
            <div class="user__right col-7">
                <div class="row mt-4">{{ userData.nickname }}</div>
            </div>
            <div v-if="isClick" class="click__part">
                <div class="mt-4 count__part">랭크 : {{ userData.rank}}({{ userData.score}})</div>
                <div class="count__part">{{ userData.playcount}}게임({{ userData.wincount}}승|{{ losecount }}패)</div>
                <div v-if="youLeader">
                    <button v-if="!isYou" @click="changeLeader" class="leader__part">팀장 위임</button>
                </div>
            </div>
            <div style="display: none">{{ isUserClick }}</div>
            <div v-if="isLeader">
                <div class="leader__user"></div>
                <div class="leader__badge">방장</div>
            </div>
        </div>
    </div>
</template>

<script>
const storage = window.sessionStorage;


export default {
    name: "WaitUser",
    
    props: {
        userData: {
            type: Object
        },
        window: {
            type: Object
        },
        number: {
            type: Number
        },
        existClick: {
            type: Boolean,
            defualt: false,
        },
        sendLeader: {
            type: Object,
        },
        youLeader: {
            type: Boolean,
        },
    },

    data() {
        return {
            width: 0,
            height: 0,
            isClick: false,
            losecount: 0,
            isYou: false,
            isLeader: false,
        }
    },

    created() {
        this.layoutCal();
        if (this.userData.id === this.sendLeader.id) {
            this.isLeader = true;
        } else {
            this.isLeader = false;
        }

        // 본인 확인
        if (this.userData.id == storage.getItem('id')) {
            this.isYou = true;
        }

        this.losecount = Number(this.userData.playcount) - Number(this.userData.wincount);
    },

    computed: {
        // 보이는 화면 크기 변화 감지
        nowWindow() {
            this.layoutCal();
            return this.window
        },

        isUserClick() {
            this.clickChange();
            return this.existClick
        },
    },

    methods: {
        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            var userWidth = (this.window.width * 0.6 * 0.5) - 50;
            var userHeight = ((this.window.height - 80 - 40) * 0.25) - 30;
            document.documentElement.style.setProperty('--userWidth', userWidth + suffix);
            document.documentElement.style.setProperty('--userHeight', userHeight + suffix);
            document.documentElement.style.setProperty('--userLeft', (userWidth * (5/12)) + suffix);
            document.documentElement.style.setProperty('--userRight', ((userWidth * (7/12)) - 10) + suffix);
            document.documentElement.style.setProperty('--userImage', ((userWidth * 0.3) - 10) + suffix);

        },

        clickUser() {
            this.isClick = !this.isClick;

            if (this.isClick) {
                this.$emit('clickUser', this.number);
            } else {
                this.$emit('noClickUser', this.number);
            }
        },
        
        clickChange() {
            this.isClick = this.existClick;
        },


        changeLeader() {
            this.$emit('changeLeader', this.number);
        },

    }
}
</script>

<style lang="scss" scoped>
:root {
    --userWidth: 300px;
    --userHeight: 200px;
    --userLeft: 100px;
    --userRigth: 200px;
    --userImage: 50px;
}

#WaitUser {
}

.user__part {
    position: relative;
    width: var(--userWidth);
    height: var(--userHeight);
    /* background-color: rgba(0, 0, 0, 0.3); */
    margin: 10px 20px;
    border-radius: 30px;
    border: 2px solid white;
}

.user__left {
    width: var(--userLeft);
    height: var(--userHeight);
    text-align: center;
}

.user__right {
    width: var(--userRight);
    height: var(--userHeight);
}

.user__image {
    width: var(--userImage);
    max-width: 95px;
    height: var(--userImage);
    max-height: 95px;
    border-radius: 50%;
    background-color: black;
}

.user__image > img {
    width: 100%;
    height: 100%;
}

.click__part {
    position: absolute;
    right: 10%;
    top: 0;
    height: 100%;
    width: 80%;
    background-color: rgba(255, 255, 255, 0.5);
}

.count__part {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: rgba(255, 255, 0, 0.8);
}

.leader__part {
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 101%;
    background-color: black;
    color: white;
    border: none;
}

.leader__user {
    position: absolute;
    left: 50%;
    top: 50%;
    width: var(--userWidth);
    height: var(--userHeight);
    border-radius: 30px;
    border: 4px solid rgba(255, 255, 0, 0.8);;
    transform: translate(-50%, -50%);
}

.leader__badge {
    position: absolute;
    right: 5%;
    top: 5%;
    padding: 5px;
    color: white;
    border-radius: 10px;
    border: none;
    background-color: rgba(255, 255, 0, 0.8);
}


</style>
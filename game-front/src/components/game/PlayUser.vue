<template>
    <!-- 게임 실행 화면 User -->
    <div id="PlayUser" class="container p-0">
        <div class="user__part row d-flex-justify-content-center align-items-center">
            <div style="display: none;">{{ nowWindow }}</div>
            <div class="user__left col-4 d-flex justify-content-center align-items-center p-0">
                <!-- user 래더 or 프로필 -->
                <div class="user__image"></div>
            </div>
            <div class="col-8 p-0">
                <div class="user__right d-flex align-content-center align-items-center p-0">
                    <div>{{ userData.nickname }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PlayUser",
    
    props: {
        userData: {
            type: Object
        },
        window: {
            type: Object
        },
        isMode: {
            type: Boolean
        },
    },

    data() {
        return {
            width: 0,
            height: 0,
        }
    },

    created() {
        this.layoutCal();
    },

    computed: {
        // 보이는 화면 크기 변화 감지
        nowWindow() {
            this.layoutCal();
            return this.window
        },
    },

    methods: {
        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            var userWidth = 0;
            if (this.isMode) {
                userWidth = ((this.window.width * (4/12)) - 40) / 2;
            } else {
                userWidth = (this.window.width * (3/12)) - 40;
            }
            var userHeight = ((this.window.height - 40 - 80) * 0.125) - 20;
            document.documentElement.style.setProperty('--userWidth', userWidth + suffix);
            document.documentElement.style.setProperty('--userHeight', userHeight + suffix);
            document.documentElement.style.setProperty('--userLeft', (userWidth * (4/12)) + suffix);
            document.documentElement.style.setProperty('--userRight', ((userWidth * (8/12))) + suffix);
            document.documentElement.style.setProperty('--userImage', ((userWidth * 0.21) - 10) + suffix);

        },

    }
}
</script>

<style scoped>
:root {
    --userWidth: 300px;
    --userHeight: 100px;
    --userLeft: 100px;
    --userRigth: 200px;
    --userImage: 50px;
}

#PlayUser {
    position: relative;
}

.user__part {
    width: var(--userWidth);
    height: var(--userHeight);
    /* background-color: rgba(0, 0, 0, 0.3); */
    margin: 10px auto;
    border-radius: 30px;
    border: 2px solid white;
}

.user__left {
    width: var(--userLeft);
    height: var(--userHeight);
}

.user__right {
    width: var(--userRight);
    height: var(--userHeight);
}

.user__image {
    width: var(--userImage);
    max-width: 50px;
    height: var(--userImage);
    max-height: 50px;
    border-radius: 50%;
    background-color: black;
}


</style>
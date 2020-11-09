<template>
    <!-- 게임 실행 화면 User -->
    <div id="EmptyUser" class="container p-0">
        <div class="user__part row d-flex justify-content-center align-items-center">
            <div style="display: none;">{{ nowWindow }}</div>
            <div class="text__part">
                유저가 들어오지 않았습니다
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "EmptyUser",
    
    props: {
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
            var userHeight = 0;
            if (this.isMode) {
                userWidth = (this.window.width * (4/12) * 0.5 );
                userHeight = ((this.window.height - 40 - 100) * 0.5 * 0.25) - 20;
            } else {
                userWidth = (this.window.width * (3/12)) - 40;
                userHeight = ((this.window.height - 40 - 80) * 0.125) - 20;
            }
            document.documentElement.style.setProperty('--userWidth', userWidth + suffix);
            document.documentElement.style.setProperty('--userHeight', userHeight + suffix);

        },
    }
}
</script>

<style scoped>
:root {
    --userWidth: 300px;
    --userHeight: 100px;
}

#EmptyUser {
    position: relative;
}

.user__part {
    width: var(--userWidth);
    height: var(--userHeight);
    /* background-color: rgba(0, 0, 0, 0.3); */
    margin: 10px auto;
    border-radius: 30px;
    background-color: rgba(66, 66, 66, 0.5);
    color: rgba(255, 255, 255, 0.2);
    font-size: 0.8rem;
}

.text_part {
    width: 100%;
    margin: 0 auto;
}


</style>
<template>
    <div id="PlayGame">
        <!-- 화면 왼쪽 상단 -->
        <div class="screen__left__top">
            주제 등등
        </div>

        <!-- 화면 오른쪽 -->
        <div class="screen__right">
            유저 목록 등
        </div>

        <!-- 화면 왼쪽 하단 -->
        <div class="screen__left__bottom">
            그리기 영역
        </div>

    </div>
</template>

<script>
export default {
    name: "PlayGame",

    components: {

    },

    data() {
        return {
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

    watch: {
        // 보이는 화면 크기 변화 감지
        window() {
            window.removeEventListener('resize', this.screenResize);
        },
    },

    methods: {
        // 현재 보이는 화면 크기 계산
        screenResize() {
            this.window.width = window.innerWidth;
            this.window.height = window.innerHeight;
            this.layoutCal();
        },

        // 넓이 계산해서 style 안에 계산해서 넣어주기
        layoutCal() {
            let suffix = 'px';
            var leftSize = this.window.width * 0.7;
            var rightSize = this.window.width * 0.3;
            document.documentElement.style.setProperty('--leftSize', leftSize + suffix);
            document.documentElement.style.setProperty('--rightSize', rightSize + suffix);
            document.documentElement.style.setProperty('--heightSize', (this.window.height) + suffix);
            document.documentElement.style.setProperty('--leftTopSize', (this.window.height * 0.3) + suffix);
            document.documentElement.style.setProperty('--leftBottomSize', (this.window.height * 0.7) + suffix);
        },

    }

}
</script>

<style scoped>
/* 가상 선택자, 우선 순위가 제일 높음 */
:root {
    --leftWidth: 800px;
    --rightWidth: 400px;
    --heightSize: 800px;
    --leftTopSize: 400px;
    --leftBottomSize: 400px;
}

/* 전체 영역 3부분으로 나눔 왼상단 | 왼하단 | 오 */
.screen__left__top {
    display: inline-block;
    width: var(--leftSize);
    min-width: 490px;
    height: var(--leftTopSize);
    font-weight: bold;
    background-color: beige;
}

.screen__left__bottom {
    display: inline-block;
    width: var(--leftSize);
    min-width: 490px;
    height: var(--leftBottomSize);
    background-color: aquamarine;
}

.screen__right {
    float: right;
    width: var(--rightSize);
    min-width: 210px;
    height: var(--heightSize);
    background-color: skyblue;
}

</style>
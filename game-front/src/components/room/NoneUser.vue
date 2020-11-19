<template>
    <!-- 대기 방 막아놓은 칸 -->
    <div id="NoneUser">
        <div class="user__part d-flex justify-content-center align-items-center">
            <div style="display: none;">{{ nowWindow }}</div>
        </div>
    </div>
</template>

<script>
export default {
    name: "NoneUser",

    props: {
        window: {
            type: Object
        },
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
            var userWidth = (this.window.width * 0.6 * 0.5) - 50;
            var userHeight = ((this.window.height - 80 - 40) * 0.25) - 30;
            document.documentElement.style.setProperty('--userWidth', userWidth + suffix);
            document.documentElement.style.setProperty('--userHeight', userHeight + suffix);
            document.documentElement.style.setProperty('--userLeft', (userWidth * (5/12)) + suffix);
            document.documentElement.style.setProperty('--userRight', ((userWidth * (7/12)) - 10) + suffix);
            document.documentElement.style.setProperty('--userImage', ((userWidth * 0.3) - 10) + suffix);

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

.user__part {
    width: var(--userWidth);
    height: var(--userHeight);
    /* background-color: rgba(0, 0, 0, 0.3); */
    margin: 10px 20px;
    border-radius: 30px;
    background: linear-gradient(to top right, rgba(66, 66, 66, 0.5) calc(50% - 1px), rgba(255, 255, 255, 0.5), rgba(66, 66, 66, 0.5) calc(50% + 1px) );
    font-size: 0.8rem;
    overflow: hidden;
}



</style>
<template>
    <div id="WaitUser">
        <div class="user__part">
            <div style="display: none;">{{ nowWindow }}</div>
            {{ userData.nickname }}
        </div>
    </div>
</template>

<script>
export default {
    name: "WaitUser",
    
    props: {
        userData: {
            type: Object
        },
        window: {
            type: Object
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
            console.log('계산합니다');
            let suffix = 'px';
            document.documentElement.style.setProperty('--userWidth', (this.window.width * 0.6 * 0.5) + suffix);
            document.documentElement.style.setProperty('--userHeight', ((this.window.height - 60) * 0.25) + suffix);
        },

    }
}
</script>

<style scoped>
:root {
    --userWidth: 300px;
    --userHeight: 200px;
}

.user__part {
    width: var(--userWidth);
    height: var(--userHeight);
}
</style>
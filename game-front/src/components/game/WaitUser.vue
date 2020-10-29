<template>
    <div id="WaitUser">
        <div class="user__part container">
            <div style="display: none;">{{ nowWindow }}</div>
            <div class="row">
                <div class="col-3">
                    래더
                </div>
                <div class="col-9">
                    {{ userData.nickname }}
                </div>
            </div>
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
            let suffix = 'px';
            document.documentElement.style.setProperty('--userWidth', ((this.window.width * 0.6 * 0.5) - 20) + suffix);
            document.documentElement.style.setProperty('--userHeight', (((this.window.height - 60) * 0.25) - 20) + suffix);
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
    background-color: rgba(0, 0, 0, 0.3);
    margin: 10px 10px 10px 10px;
    border-radius: 20px;
}
</style>
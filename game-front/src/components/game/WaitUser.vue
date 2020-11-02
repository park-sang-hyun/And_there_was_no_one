<template>
    <div id="WaitUser container">
        <div class="user__part row">
            <div style="display: none;">{{ nowWindow }}</div>
            <div class="user__left col-5 d-flex justify-content-center align-items-center p-0">
                <div class="user__image"></div>
            </div>
            <div class="user__right col-7">
                <div class="row mt-4">{{ userData.nickname }}</div>
            </div>
            <div class="chat__message d-flex justify-content-center">Ready</div>
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
        if (this.userData.id === 1) {
            console.log(this.userData.id);
            document.documentElement.style.setProperty('--myColor', 'yellow');
        } else {
            document.documentElement.style.setProperty('--myColor', 'white');
        }
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

<style scoped>
:root {
    --userWidth: 300px;
    --userHeight: 200px;
    --userLeft: 100px;
    --userRigth: 200px;
    --userImage: 50px;
    --myColor: white;
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

.chat__message {
    position: absolute;
    color: black;
    left: var(--userLeft);
    top: 60%;
    min-width: 60px;
    transform: translate(-50%);
	background-color: var(--myColor);
	padding: 5px;
	font-size: 0.9rem;
	border-radius: 1rem;
    box-shadow:	0 0.125rem 0.5rem rgba(0, 0, 0, .3), 0 0.0625rem 0.125rem rgba(0, 0, 0, .2);
}

.chat__message::before {
	content: '';
	position: absolute;
	width: 0;
	height: 0;
	bottom: 100%;
	left: 1em;
	border: .75rem solid transparent;
	border-top: none;
	border-bottom-color: var(--myColor);
}


</style>
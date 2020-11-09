<template>
    <!-- 게임 세팅 모드 -->
    <div id="modeSetting">
        <div class="mode__header">게임 모드</div>
        <div class="mode_radio">

            <input :disabled="!isLeader" type="radio" class="mode_option" id="first_mode" name="mode_option" value="1" v-model="modeSelect">
            <input :disabled="!isLeader" type="radio" checked class="mode_option" id="second_mode" name="mode_option" value="2" v-model="modeSelect">
            <input :disabled="!isLeader" type="radio" class="mode_option" id="third_mode" name="mode_option" value="3" v-model="modeSelect">

            <label for="first_mode">
                <span class="description">새 보드에</span>
                <p class="day">자유그리기</p>
            </label>

            <label for="second_mode">
                <span class="description">이전 그림에</span>
                <p class="day">이어그리기</p>
            </label>
            
            <label for="third_mode">
                <span class="description">자유그리기+</span>
                <p class="day">No AI</p>
            </label>
            
            <div class="mode_option_slider">
            </div>

        </div>
        <div style="display: none">{{ callSelect }}</div> 
    </div>
</template>

<script>
export default {
    name: "modeSetting",

    props: {
        mode: {
            type: Number,
        },
        isLeader: {
            type: Boolean,
        },
    },

    data() {
        return {
            modeSelect: 1,
        }
    },

    created() {
        this.modeSelect = this.mode;
    },

    computed: {
        callSelect() {
            this.sendMode();
            return this.modeSelect
        }

    },

    methods: {
        sendMode() {
            this.$emit('modeChange', this.modeSelect);
        }

    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url(https://fonts.googleapis.com/css?family=Francois+One);
@import url(https://fonts.googleapis.com/css?family=PT+Sans);

#modeSetting {
    /* font-family: 'Nanum Gothic', sans-serif; */
    font-family: 'Audiowide';
}

.mode__header {
    font-size: 1.5rem;
    font-weight: bold;
    color: white;
    text-align: center;
}

.mode_radio{
    position: relative;
    background: rgba(255,255,255,.2);
    margin: 4px auto;
    overflow: hidden;
    padding: 0 !important;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 5px;
    position: relative;
    height: 70px;
    width: 378px;
}
.mode_radio > * {
    float: left;
}
.mode_radio input[type=radio]{
    display: none;
}
.mode_radio label{
    font: 90%/1.618;
    color: white;
    z-index: 0;
    display: block;
    width: 120px;
    height: 70px;
    margin: 3px 3px;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 50px;
    cursor: pointer;
    z-index: 1;
    text-align: center;
}

.mode_option_slider{
    width: 126px;
    height: 73px;
    position: absolute;
    top: 0px;
    -webkit-border-radius: 1px;
    -moz-border-radius: 1px;
    border-radius: 1px;
    -webkit-transition: all .4s ease;
    -moz-transition: all .4s ease;
    -o-transition: all .4s ease;
    -ms-transition: all .4s ease;
    transition: all .4s ease;
}

#first_mode:checked ~ .mode_option_slider{
    background: rgba(255,255,255,.15);
    left: 0px;
}
#second_mode:checked ~ .mode_option_slider{
    background: rgba(255,255,255,.15);
    left: 126px;
}
#third_mode:checked ~ .mode_option_slider{
    background: rgba(255,255,255,.15);
    left: 252px;
}

span.description{
    top:0px;
    font-size: 0.8em;
}

p.day {
    line-height: 40px;
    font-size: 1.2em;
}

</style>
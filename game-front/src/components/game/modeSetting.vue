<template>
    <div id="modeSetting">

        <div class="mode__header">게임 모드</div>
        <div class="mode_radio">

            <input type="radio" class="mode_option" id="first_mode" name="mode_option" value="1" v-model="modeSelect">
            <input type="radio" class="mode_option" id="second_mode" name="mode_option" value="2" v-model="modeSelect">

            <label for="first_mode">
                <span class="description">새 보드에</span>
                <p class="day">자유그리기</p>
            </label>

            <label for="second_mode">
                <span class="description">이전 그림에</span>
                <p class="day">이어그리기</p>
            </label>
            
            <div class="mode_option_slider">
            </div>

        </div>
    </div>
</template>

<script>
export default {
    name: "modeSetting",

    props: {
        mode: {
          object: Number,
        }
    },

    data() {
        return {
            modeSelect: 1,
        }
    },

    created() {
        this.modeSelect = this.mode;
    },

    methods: {

    },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

#modeSetting {
    font-family: 'Nanum Gothic', sans-serif;
}

.mode__header {
    font-size: 1.5rem;
    font-weight: bold;
    color: black;
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
    width: 312px;
}
.mode_radio > * {
    float: left;
}
.mode_radio input[type=radio]{
    display: none;
}
.mode_radio label{
    font: 90%/1.618;
    color: rgba(0,0,0,.9);
    z-index: 0;
    display: block;
    width: 150px;
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
    width: 152px;
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
    background: rgba(255,255,255,.3);
    left: 0px;
}
#second_mode:checked ~ .mode_option_slider{
    background: rgba(255,255,255,.3);
    left: 159px;
}

span.description{
    top:0px;
    font-size: 0.8em;
}

p.day {
    line-height: 40px;
    font-size: 1.5em;
}

</style>
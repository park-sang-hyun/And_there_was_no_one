<template>
    <!-- 게임 세팅 난이도 -->
    <div id="difficultySetting">
        <div class="radio__header">난이도</div>

        <div class="toggle_radio">

            <input :disabled="!isLeader" type="radio" class="toggle_option" id="first_toggle" name="toggle_option" value="1" v-model="difficultySelect">
            <input :disabled="!isLeader" type="radio" checked class="toggle_option" id="second_toggle" name="toggle_option" value="2" v-model="difficultySelect">
            <input :disabled="!isLeader" type="radio" class="toggle_option" id="third_toggle" name="toggle_option" value="3" v-model="difficultySelect">

            <label for="first_toggle">
                <span class="description">시간 5초</span>
                <p class="day">상</p>
            </label>

            <label for="second_toggle">
                <span class="description">시간 10초</span>
                <p class="day">중</p>
            </label>

            <label for="third_toggle">
                <span class="description">시간 15초</span>
                <p class="day">하</p>
            </label>
            
            <div class="toggle_option_slider">
            </div>

        </div>
        <div style="display: none">{{ callSelect }}</div> 
    </div>
</template>

<script>
export default {
    name: "difficultySetting",

    props: {
        difficulty: {
            type: Number,
            default: 2,
        },
        isLeader: {
            type: Boolean,
        },
    },

    data() {
        return {
            difficultySelect: 2,
        }
    },

    created() {
        this.difficultySelect = this.difficulty;
    },
    
    computed: {
        callSelect() {
            this.sendDifficulty();
            return this.difficultySelect
        }

    },

    methods: {
        sendDifficulty() {
            this.$emit('difficultyChange', this.difficultySelect);
        }

    },
}
</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
@import url(https://fonts.googleapis.com/css?family=Francois+One);
@import url(https://fonts.googleapis.com/css?family=PT+Sans);

#difficultySetting {
    /* font-family: 'Nanum Gothic', sans-serif; */
    font-family: 'Audiowide';
}

.radio__header {
    font-size: 1.5rem;
    font-weight: bold;
    color: white;
    text-align: center;
}

.toggle_radio{
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
    width: 318px;
}
.toggle_radio > * {
    float: left;
}
.toggle_radio input[type=radio]{
    display: none;
  /*   position: fixed; */
}
.toggle_radio label{
    font: 90%/1.618;
    color: white;
    z-index: 0;
    display: block;
    width: 100px;
    height: 70px;
    margin: 3px 3px;
    -webkit-border-radius: 50px;
    -moz-border-radius: 50px;
    border-radius: 50px;
    cursor: pointer;
    z-index: 1;
  /*   line-height:100px; */
    /*background: rgba(0,0,0,.1);*/
    text-align: center;
    /*margin: 0 2px;*/
    /*background: blue;*/ /*make it blue*/
}
.toggle_option_slider{
    /*display: none;*/
    /*background: red;*/
    width: 100px;
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

#first_toggle:checked ~ .toggle_option_slider{
    background: rgba(255,255,255,.15);
    left: 0px;
}
#second_toggle:checked ~ .toggle_option_slider{
    background: rgba(255,255,255,.15);
    left: 109px;
}
#third_toggle:checked ~ .toggle_option_slider{
    background: rgba(255,255,255,.15);
    left: 218px;
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
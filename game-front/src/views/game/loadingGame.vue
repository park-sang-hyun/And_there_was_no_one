<template>
    <div id="loadingGame">
        <!-- 자유그리기 모드일 때 -->
        <div v-if="sendGame.mode == 1">
            <loadingOne/>
        </div>
        <!-- 이어그리기 모드일 때 -->
        <div v-else-if="sendGame.mode == 2">
            <loadingTwo/>
        </div>
        <!-- NO AI 모드일 때 -->
        <div v-else>
            <loadingThree/>
        </div>
    </div>
</template>

<script>
import loadingOne from '@/components/room/LoadingModeOne.vue';
import loadingTwo from '@/components/room/LoadingModeTwo.vue';
import loadingThree from '@/components/room/LoadingModeThree.vue';


export default {
    name: "loadingGame",

    components: {
        loadingOne,
        loadingTwo,
        loadingThree,
    },

    props: {
        sendGame: {
            type: Object,
        },
        roomId: {
            type: Number,
        }
    },

    created() {

        setTimeout(() => this.$router.replace({ name: 'PlayGame' , params: { sendGame: this.sendGame, roomId: this.roomId }}), 7000);

    }

}
</script>

<style>

</style>
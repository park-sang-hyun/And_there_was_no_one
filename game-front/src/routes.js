import Login from './views/user/Login.vue'
import GameMain from './views/game/GameMain.vue'
import WaitRoom from './views/game/WaitRoom.vue'
import PlayGame from './views/game/PlayGame.vue'

export default [
    // User
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    // GameMain
    {
        path : '/GameMain',
        name : 'GameMain',
        component : GameMain
    },
    // WaitRoom
    {
        path: '/room',
        // path: '/room/:roomId',
        name: 'WaitRoom',
        component: WaitRoom
    },
    // PlayGame
    {
        path: '/playing',
        // path: '/playing/:roomId',
        name: 'PlayGame',
        component: PlayGame
    },
]
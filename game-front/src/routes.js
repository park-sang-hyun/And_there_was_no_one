import Login from './views/user/Login.vue'
import GameMain from './views/game/GameMain.vue'

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
]
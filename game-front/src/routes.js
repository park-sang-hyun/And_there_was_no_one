import Login from './views/user/Login.vue'
import Signup from './views/user/Signup.vue'
import GameMain from './views/game/GameMain.vue'

export default [
    // Auth 
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    // Auth 
    {
        path : '/signup/',
        name : 'Signup',
        component : Signup
    },
    // GameMain
    {
        path : '/GameMain',
        name : 'GameMain',
        component : GameMain
    },
]
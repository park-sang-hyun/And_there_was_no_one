import Login from './views/user/Login.vue'
import Signup from './views/user/Signup.vue'
import GameMain from './views/game/GameMain.vue'
import Lobby from './views/lobby/lobby.vue'
import Test from './views/user/tmp.vue'

export default [
    // test 
    {
        path : '/test',
        name : 'Test',
        component : Test
    },
    // Auth 
    {
        path : '/',
        name : 'Login',
        component : Login
    },
    // Auth 
    {
        path : '/signup',
        name : 'Signup',
        component : Signup
    },
    // GameMain
    {
        path : '/GameMain',
        name : 'GameMain',
        component : GameMain
    },
    // Lobby
    {
        path : '/lobby',
        name : 'Lobby',
        component : Lobby
    },
]
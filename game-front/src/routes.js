import Login from './views/user/Login.vue'
import Signup from './views/user/Signup.vue'
import WaitRoom from './views/game/WaitRoom.vue'
import PlayGame from './views/game/PlayGame.vue'
import Lobby from './views/lobby/lobby.vue'
import LoadingOne from './views/game/LoadingOne.vue'
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
    // Lobby
    {
        path : '/lobby',
        name : 'Lobby',
        component : Lobby
    },
    // LoadingOne
    {
        path: '/loading',
        name: 'LoadingOne',
        component: LoadingOne
    },
]
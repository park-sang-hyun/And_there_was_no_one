import Login from './views/user/Login.vue'
import Signup from './views/user/Signup.vue'
import WaitRoom from './views/game/WaitRoom.vue'
import PlayGame from './views/game/PlayGame.vue'
import Lobby from './views/lobby/lobby.vue'
import Test from './views/user/tmp.vue'
import forbidden from './views/forbidden/forbidden404.vue'


// 로그인이 필요한 경우엔 requireAuth 를 사용한다. beforeEnter: requireAuth()
const requireAuth = () => (to, from, next) => {
    let id = window.sessionStorage.getItem('id');
    let token = window.sessionStorage.getItem('token');

    if(id && token) {
        return next();
    } else {
        window.sessionStorage.clear();
        return next('/');
    }
};

// 이미 로그인을 한 경우에는 getAuth 를 사용한다. beforeEnter: getAuth()
const getAuth = () => (to, from, next) => {
    let id = window.sessionStorage.getItem('id');
    let token = window.sessionStorage.getItem('token');

    if(id && token) {
        return next('/lobby');
    } else {
        window.sessionStorage.clear();
        return next();
    }
};


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
        component : Login,
        beforeEnter: getAuth()
    },
    // Auth 
    {
        path : '/signup',
        name : 'Signup',
        component : Signup,
        beforeEnter: getAuth()
    },
    // WaitRoom
    {
        path: '/room/:roomId',
        name: 'WaitRoom',
        component: WaitRoom,
        props: true,
        beforeEnter: requireAuth()
    },
    // PlayGame
    {
        path: '/playing/:roomId',
        name: 'PlayGame',
        component: PlayGame,
        props: true,
        beforeEnter: requireAuth()
    },
    // Lobby
    {
        path : '/lobby',
        name : 'Lobby',
        component : Lobby,
        beforeEnter: requireAuth()
    },

    //  없는 경우에는 404 페이지로 연결하자
    {
        path: '*',
        redirect: '/forbidden',
    },
    //  404 페이지
    {
        path: '/forbidden',
        name: 'forbidden',
        component: forbidden,
        beforeEnter: requireAuth()
    },
]
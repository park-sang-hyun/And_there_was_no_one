import axios from 'axios';

// axios 객체 생성
export default axios.create({
    baseURL:"http://localhost:8002/",
    //baseURL:"http://k3d105.p.ssafy.io:8002", 
    headers: {
      'Content-type': 'application/json',
    },
  });

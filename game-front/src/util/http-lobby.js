import axios from 'axios';

// axios 객체 생성
export default axios.create({
    // baseURL:"http://localhost:8001/",
    baseURL:"http://k3d105.p.ssafy.io:8001/", 
   headers: {
      'Content-type': 'application/json',
    },
  });

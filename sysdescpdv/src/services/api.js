import axios from 'axios';


const api = axios.create({
    baseURL: 'http://localhost:8090/',
    //  baseURL: 'https://mobwash.herokuapp.com/',
    auth: {
        username: 'admin',
        password: '123456'
    },
    headers: { 'Content-Type': 'application/json' }
});



export default api;
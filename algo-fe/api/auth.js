import useAxios from '@/modules/axios.js'

const { axiosPost } = useAxios()

function registerUser(userData) {
    return axiosPost('/signup')
}

// 로그인 API
function loginUser(userData) {
    return instance.post('login', userData);
}

export { registerUser, loginUser };
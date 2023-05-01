import { createStore } from 'vuex';
import useAxios from '@/modules/axios.js'
import createPersistedState from "vuex-persistedstate";
import router from "@/router/router.js";

const { axiosPost } = useAxios();

export default createStore({
    state: {
        isLoggedIn: false,
    },
    getters: {
        isLoggedIn: (state) => state.isLoggedIn
    },
    mutations: {
        setLoginStatus(state, status) {
            state.isLoggedIn = status;
        },
    },
    actions: {
        login({ commit }, credentials) {
            return new Promise((resolve, reject) => {
                axiosPost('/api/v1/login', credentials,
                    (res) => {
                    commit('setLoginStatus', 'true')
                    router.push('/')
                    resolve(res)
                }, (err) => {
                    console.error(err)
                    reject(err)
                })
            });
        },
        logout({ commit }) {
            return new Promise((resolve, reject) => {
                axiosPost('/api/v1/logout',{},
                    (res) => {
                    commit('setLoginStatus', 'false')
                        location.reload()
                    resolve(res)
                }, (err) => {
                    console.error(err)
                    commit('setLoginStatus', 'false')
                    reject(err)
                })
            });
        },
    },
    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })],
});


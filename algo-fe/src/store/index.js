import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";
import * as AuthAPI from '@/services/auth.js'
import router from "@/router/router.js";

export default createStore({
    state: {
        isLoggedIn: false,
        nickname: null,
    },
    getters: {
        isLoggedIn: (state) => state.isLoggedIn,
        nickname: (state) => state.nickname
    },
    mutations: {
        setLoginStatus(state, status) {
            state.isLoggedIn = status;
        },
        setNickname(state, status) {
            state.nickname = status;
        }
    },
    actions: {
        login({ commit }, loginData) {
            return new Promise((resolve, reject) => {
                AuthAPI.login(loginData)
                    .then((res) => {
                        commit('setNickname', res.data.nickname)
                        commit('setLoginStatus', 'true')
                        resolve(res)
                    })
                    .catch((err) => {
                        reject(err)
                })
            });
        },
        logout({ commit }) {
            return new Promise((resolve, reject) => {
                AuthAPI.logout()
                    .then((res) => {
                        commit('setLoginStatus', 'false')
                        router.push('/').then(() => {
                            location.reload()
                        })
                        resolve(res)
                    })
                    .catch((err) => {
                        reject(err)
                    })
            });
        },
    },
    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })],
});


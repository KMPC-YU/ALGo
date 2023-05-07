import { createStore } from 'vuex';
import createPersistedState from "vuex-persistedstate";
import * as AuthAPI from '@/services/auth.js'

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
        login({ commit }, loginData) {
            return new Promise((resolve, reject) => {
                AuthAPI.login(loginData)
                    .then((res) => {
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
                        location.reload()
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


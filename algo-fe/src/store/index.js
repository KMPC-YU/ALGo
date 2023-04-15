import { reactive, toRefs } from 'vue';
import axios from 'axios';
import { createStore } from 'vuex';

export default createStore({
    state: reactive({
        accessToken: null,
        refreshToken: null,
        user: null,
        status: '',
    }),
    mutations: {
        setAccessToken(state, accessToken) {
            state.accessToken = accessToken;
        },
        setRefreshToken(state, refreshToken) {
            state.refreshToken = refreshToken;
        },
        setUser(state, user) {
            state.user = user;
        },
        setStatus(state, status) {
            state.status = status;
        },
    },
    actions: {
        login({ commit }, credentials) {
            return new Promise((resolve, reject) => {
                commit('setStatus', 'loading');
                axios.post('http://116.33.66.209:8080/api/v1/login', credentials)
                    .then(response => {
                        const accessToken = response.data.accessToken;
                        const refreshToken = response.data.refreshToken;
                        const user = response.data.user;
                        localStorage.setItem('accessToken', accessToken);
                        localStorage.setItem('refreshToken', refreshToken);
                        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                        commit('setAccessToken', accessToken);
                        commit('setRefreshToken', refreshToken);
                        commit('setUser', user);
                        commit('setStatus', 'success');
                        resolve(response);
                    })
                    .catch(error => {
                        commit('setStatus', 'error');
                        reject(error);
                    });
            });
        },
        logout({ commit }) {
            return new Promise((resolve) => {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                delete axios.defaults.headers.common['Authorization'];
                commit('setAccessToken', null);
                commit('setRefreshToken', null);
                commit('setUser', null);
                resolve();
            });
        },
        refreshToken({ commit, state }) {
            return new Promise((resolve, reject) => {
                axios.post('http://example.com/api/auth/refresh', { refreshToken: state.refreshToken })
                    .then(response => {
                        const accessToken = response.data.accessToken;
                        localStorage.setItem('accessToken', accessToken);
                        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                        commit('setAccessToken', accessToken);
                        resolve(response);
                    })
                    .catch(error => {
                        localStorage.removeItem('accessToken');
                        localStorage.removeItem('refreshToken');
                        delete axios.defaults.headers.common['Authorization'];
                        commit('setAccessToken', null);
                        commit('setRefreshToken', null);
                        commit('setUser', null);
                        reject(error);
                    });
            });
        },
        initialize({ commit }) {
            const accessToken = localStorage.getItem('accessToken');
            const refreshToken = localStorage.getItem('refreshToken');
            if (accessToken && refreshToken) {
                axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                commit('setAccessToken', accessToken);
                commit('setRefreshToken', refreshToken);
                commit('setStatus', 'success');
            }
        },
    },
    getters: {
        status: (state) => state.status,
        user: (state) => state.user,
        accessToken: (state) => state.accessToken,
        refreshToken: (state) => state.refreshToken,
        isLoggedIn: (state) => state.accessToken !== null,
    },
});


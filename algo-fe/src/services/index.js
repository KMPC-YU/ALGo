import axios from 'axios'

export function createInstance() {
    return axios.create({
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        baseURL: import.meta.env.VITE_APP_BACKEND_URL,
        withCredentials: true,
    });
}

export const instance = createInstance();
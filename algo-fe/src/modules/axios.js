import { axios } from '@bundled-es-modules/axios'

export default () => {
    const CONFIG = {
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        },
        withCredentials: true,
    }
    const createURL = (url) => {
        return url.toString().startsWith('http') ? url : process.env.BACKEND_URL + url
    }

    const axiosGet = async (url, onSuccess = null, onFailed = null) => {
        axios.get(createURL(url), CONFIG)
            .then((resp) => {
                if (onSuccess) {
                    onSuccess(resp)
                }
            }).catch((err) => {
            if (onFailed) {
                onFailed(err)
            }
        })
    }

    const axiosPost = async (url, data, onSuccess = null, onFailed = null) => {
        axios.post(createURL(url), data, CONFIG)
            .then((resp) => {
                if (onSuccess) {
                    onSuccess(resp)
                }
            }).catch((err) => {
            if (onFailed) {
                onFailed(err)
            }
        })
    }

    const axiosPut = async (url, data, onSuccess = null, onFailed = null) => {
        axios.put(createURL(url), data, CONFIG)
            .then((resp) => {
                if (onSuccess) {
                    onSuccess(resp)
                }
            }).catch((err) => {
            if (onFailed) {
                onFailed(err)
            }
        })
    }

    const axiosPatch = async (url, data, onSuccess = null, onFailed = null) => {
        axios.patch(createURL(url), data, CONFIG)
            .then((resp) => {
                if (onSuccess) {
                    onSuccess(resp)
                }
            }).catch((err) => {
            if (onFailed) {
                onFailed(err)
            }
        })
    }

    const axiosDelete = async (url, onSuccess = null, onFailed = null) => {
        axios.delete(createURL(url), CONFIG)
            .then((resp) => {
                if (onSuccess) {
                    onSuccess(resp)
                }
            }).catch((err) => {
            if (onFailed) {
                onFailed(err)
            }
        })
    }

    return {
        axiosGet,
        axiosPost,
        axiosPut,
        axiosPatch,
        axiosDelete,
    }
}
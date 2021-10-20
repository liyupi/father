import axios from "axios";

/**
 * 增加图片
 * @param {*} emoji
 */
export const addEmoji = (emoji) => {
    if (!emoji) {
        return false;
    }
    if (!emoji.name || !emoji.url) {
        return false;
    }
    if (emoji.tags) {
        emoji.tags = JSON.stringify(emoji.tags);
    }
    return axios.post('/emoji/add', emoji)
        .then(res => {
            console.log('addEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('addEmoji error', error);
            return false;
        });
}

/**
 * 搜索图片
 * @param {*} searchParams
 * @returns
 */
export const searchEmojis = (searchParams = {}) => {
    return axios.get('/emoji/search', {
        params: searchParams,
    })
        .then(res => {
            console.log('searchEmojis succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('searchEmojis error', error);
            return null;
        });
}

/**
 * 审核图片
 * @param {*} id
 * @param reviewStatus
 * @returns
 */
export const reviewEmoji = (id, reviewStatus) => {
    if (id <= 0 || reviewStatus < 0) {
        return false;
    }
    return axios.post('/emoji/review', {
        id,
        reviewStatus,
    })
        .then(res => {
            console.log('reviewEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('reviewEmoji error', error);
            return false;
        });
}

/**
 * 删除图片
 * @param {*} id
 * @returns
 */
export const deleteEmoji = (id) => {
    return axios.post('/emoji/delete', {
        id,
    })
        .then(res => {
            console.log('deleteEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('deleteEmoji error', error);
            return false;
        });
}

/**
 * 更新图片
 * @param {*} id
 * @param {*} emoji
 * @returns
 */
export const updateEmoji = (id, emoji) => {
    return axios.post('/emoji/update', {
        id,
        ...emoji,
    })
        .then(res => {
            console.log('updateEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('updateEmoji error', error);
            return false;
        });
}

/**
 * 上传表情文件
 *
 * @param file
 * @returns {Promise<AxiosResponse<FormData> | boolean>}
 */
export const uploadEmoji = (file) => {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/emoji/upload', formData)
        .then(res => {
            console.log('uploadEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('uploadEmoji error', error);
            return false;
        });
}
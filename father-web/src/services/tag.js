import axios from "axios";

/**
 * 增加标签
 * @param {*} tag
 */
export const addTag = (tag) => {
    if (!tag) {
        return false;
    }
    if (!tag.name) {
        return false;
    }
    return axios.post('/tag/add', tag)
        .then(res => {
            console.log('addTag succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('addTag error', error);
            return false;
        });
}

/**
 * 搜索标签
 * @param {*} searchParams
 * @returns
 */
export const searchTags = (searchParams = {}) => {
    return axios.get('/tag/search', {
        params: searchParams,
    })
        .then(res => {
            console.log('searchTags succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('searchTags error', error);
            return null;
        });
}

/**
 * 删除标签
 * @param {*} id
 * @returns
 */
export const deleteTag = (id) => {
    return axios.post('/tag/delete', {
        id,
    })
        .then(res => {
            console.log('deleteTag succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('deleteTag error', error);
            return false;
        });
}

/**
 * 更新标签
 * @param {*} id
 * @param {*} tag
 * @returns
 */
export const updateTag = (id, tag) => {
    return axios.post('/tag/update', {
        id,
        ...tag,
    })
        .then(res => {
            console.log('updateTag succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('updateTag error', error);
            return false;
        });
}

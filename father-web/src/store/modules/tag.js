/**
 * 标签管理
 *
 * @author liyupi
 */
// initial state
import {searchTags} from "@/services/tag";

const state = () => ({
    tags: []
})

// getters
const getters = {}

// actions
const actions = {
    async getAllTags ({ commit }) {
        const data = await searchTags({
            pageSize: 1000,
        });
        if (data) {
            commit('setTags', data.records);
        }
    }
}

// mutations
const mutations = {
    setTags (state, tags) {
        state.tags = tags
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
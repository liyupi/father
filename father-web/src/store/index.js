import { createStore, createLogger } from 'vuex'
import tag from './modules/tag'

const debug = process.env.NODE_ENV !== 'production'

export default createStore({
    modules: {
        tag,
    },
    strict: debug,
    plugins: debug ? [createLogger()] : []
})
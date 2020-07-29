'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
    NODE_ENV: '"development"',
    // API_ROOT: '"http://10.22.0.11:8085/api/"',
    API_ROOT: '"http://39.98.120.148/api/"',
})

module.exports = {
    root: true,
    env: {
        node: true,
        browser: true,
    },
    extends: [
        'plugin:vue/vue3-recommended',
        'eslint:recommended',
        'prettier'
    ],
    parserOptions: {
        parser: '@babel/eslint-parser',
        ecmaVersion: 2020,
    },
    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'vue/multi-word-component-names': 'off',
        'vue/max-attributes-per-line': ['error', {
            singleline: 3,
            multiline: 1
        }],
        'vue/html-indent': ['error', 2],
        'vue/script-indent': ['error', 2, { baseIndent: 0 }],
        'indent': ['error', 2],
        'quotes': ['error', 'single'],
        'semi': ['error', 'always']
    }
};
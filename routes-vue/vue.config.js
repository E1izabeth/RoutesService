module.exports = {
  devServer: {
    port: 8081,
  },
  publicPath: process.env.NODE_ENV === 'production' ? '/~s243891/' : '/',
  outputDir: './../../routes-front/webapp'
};

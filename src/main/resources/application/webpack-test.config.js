const path = require('path');
const webpack = require('webpack');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
  context: path.resolve(__dirname, './scripts/test'),
  resolve: {
    alias: {
      Main : path.resolve(__dirname, './scripts/main'),
      Test : path.resolve(__dirname, './scripts/test'),
      Styles  : path.resolve(__dirname, './styles'),
      Images  : path.resolve(__dirname, './images')
    }
  },
  entry: {
    'flex-space-around-helper-tests': './flex-space-around-helper-tests.js',
    'jquery.grid-collection-tests': './jquery.grid-collection-tests.js',
    'jquery.page-scroller-tests': './jquery.page-scroller-tests.js',
    'jquery.items-with-content-tests': './jquery.items-with-content-tests.js'
  },
  output: {
    path: path.resolve(__dirname, './scripts/tmp'),
    filename: '[name].bundle.js'
  },
  module: {
    loaders: [
      {
        test   : /\.css$/,
        exclude: /node_modules/,
        use    : ['style-loader', 'css-loader']
      },
      {
        test   : /\.js$/,
        exclude: /node_modules/,
        loader : 'babel-loader' 
      }
    ]
  },
  plugins: [
    new webpack.ProvidePlugin({
        jQuery: 'jquery',
        $: 'jquery',
        jquery: 'jquery'
    }),
    new CopyWebpackPlugin([
      { from: 'flex-space-around-helper-tests.html' },
      { from: 'jquery.grid-collection-tests.html' },
      { from: 'jquery.page-scroller-tests.html' },
      { from: 'jquery.items-with-content-tests.html' }
    ])
  ]
};
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

const BomPlugin = require('webpack-utf8-bom');

module.exports = {
  resolve: {
    alias: {
      Main : path.resolve(__dirname, './scripts/main'),
      Styles  : path.resolve(__dirname, './styles'),
      Images  : path.resolve(__dirname, './images'),
      Node_modules: path.resolve(__dirname, './node_modules')
    }
  },
  entry: {
    'application': 'Main/application.js',
    'base-page': 'Main/base-page.js'
  },
  output: {
    path: path.resolve(__dirname, '../static'),
    filename: '[name].bundle.js',
    publicPath: '../static/'
  },
  module: {
    loaders: [
      {
        test   : /\.(jpg|png)$/,
        loader : 'file-loader?name=[name].[ext]'
      },
      {
        test   : /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader : "url-loader?limit=10000&mimetype=application/font-woff&name=[name].[ext]"
      },
      { 
        test   : /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader : "file-loader?name=[name].[ext]"
      },
      { 
        test   : /\.scss$/,
        loaders: ['style', 'css', 'postcss', 'sass']
      },
      {
        test   : /\.css$/,
        use    : ['style-loader', 'css-loader', 'less-loader']
      },
      {
        test   : /\.js$/,
        exclude: /node_modules/,
        loader : 'babel-loader' 
      }
    ]
  },
  plugins: [
    //new UglifyJSPlugin(),
    new webpack.ProvidePlugin({
        jQuery: 'jquery',
        $: 'jquery',
        jquery: 'jquery',
        'window.jQuery': 'jquery'
    }),
    new BomPlugin(true)
  ]
};
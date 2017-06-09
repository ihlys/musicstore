const UglifyJSPlugin = require('uglifyjs-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
  resolve: {
    alias: {
      Main : path.resolve(__dirname, './scripts/main'),
      Styles  : path.resolve(__dirname, './styles'),
      Images  : path.resolve(__dirname, './images')
    }
  },
  entry: {
    main: 'Main/main.js',
    'music-content': 'Main/music-content.js',
    genres: 'Main/genres.js',
    artists: 'Main/artists.js',
    albums: 'Main/albums.js',
    songs: 'Main/songs.js',
    soundtracks: 'Main/soundtracks.js',
    'thematic-compilations': 'Main/thematic-compilations.js',
    greetings: 'Main/greetings.js'
  },
  output: {
    path: path.resolve(__dirname, '../static'),
    filename: '[name].bundle.js',
    publicPath: '../static/'
  },
  module: {
    loaders: [
      {
        test   : /\.png$/,
        loader : 'file-loader?name=[name].[ext]'
      },
      {
        test   : /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader : "url-loader?limit=10000&mimetype=application/font-woff"
      },
      { 
        test   : /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader : "file-loader"
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
        jquery: 'jquery'
    })
  ]
};
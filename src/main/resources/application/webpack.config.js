const UglifyJSPlugin = require('uglifyjs-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
  resolve: {
    alias: {
      Scripts: path.resolve(__dirname, './scripts'),
      Styles : path.resolve(__dirname, './styles'),
      Images : path.resolve(__dirname, './images')
    }
  },
  entry: {
    main: 'Scripts/main.js',
    'music-content': 'Scripts/music-content.js',
    genres: 'Scripts/genres.js',
    artists: 'Scripts/artists.js',
    albums: 'Scripts/albums.js',
    songs: 'Scripts/songs.js',
    soundtracks: 'Scripts/soundtracks.js',
    'thematic-compilations': 'Scripts/thematic-compilations.js',
    greetings: 'Scripts/greetings.js'
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
        test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, 
        loader: "url-loader?limit=10000&mimetype=application/font-woff" 
      },
      { 
        test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, 
        loader: "file-loader" 
      },
      { 
        test: /\.scss$/, 
        loaders: ['style', 'css', 'postcss', 'sass'] 
      },
      {
        test   : /\.css$/,
        exclude: /node_modules/,
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
    new UglifyJSPlugin(),
    new webpack.ProvidePlugin({
        jQuery: 'jquery',
        $: 'jquery',
        jquery: 'jquery'
    })
  ]
};
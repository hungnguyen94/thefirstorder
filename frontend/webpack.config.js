var path = require('path');
var webpack = require('webpack')
var ROOT = path.resolve(__dirname, 'src');
var SRC = path.resolve(ROOT, 'app');
var DEST = path.resolve(__dirname, '../backend/src/main/resources/static');

module.exports = {
  devtool: 'source-map',
  entry: {
    app: SRC + '/index.jsx',
  },
  resolve: {
    root: [
      path.resolve(ROOT, 'app'),
      path.resolve(ROOT, 'stylesheets')
    ],
    extensions: ['', '.js', '.jsx']
  },
  output: {
    path: DEST,
    filename: 'bundle.js',
    publicPath: 'dist/'
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,  // Notice the regex here. We're matching on js and jsx files.
        loader: 'babel',
        exclude: path.resolve(__dirname, 'node_modules'), 
        query: {
          presets: ['es2015', 'react']
        }, 
        include: SRC
      },

      {test: /\.css$/, loader: 'style-loader!css-loader'},
      {test: /\.less$/, loader: 'style!css!less'},

      // Needed for the css-loader when [bootstrap-webpack](https://github.com/bline/bootstrap-webpack)
      // loads bootstrap's css.
      {test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&amp;mimetype=application/font-woff'},
      {test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&amp;mimetype=application/octet-stream'},
      {test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: 'file'},
      {test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'url?limit=10000&amp;mimetype=image/svg+xml'}
    ]
  }
};

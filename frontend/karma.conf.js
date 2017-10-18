// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular/cli'],
    plugins: [
      require('karma-jasmine'),
      require('karma-phantomjs-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma')
    ],
    files: [
      {pattern: 'node_modules/core-js/client/core.js', included: true, watched: false},
      {pattern: 'node_modules/hammerjs/hammer.min.js', included: true, watched: false},
      {pattern: 'node_modules/hammerjs/hammer.min.js.map', included: false, watched: false},

      // Include all Angular dependencies
      {pattern: 'node_modules/@angular/**/*', included: false, watched: false},
      {pattern: 'node_modules/rxjs/**/*', included: false, watched: false},

      // Include a Material theme in the test suite.
      {pattern: 'dist/packages/**/core/theming/prebuilt/indigo-pink.css', included: true, watched: true},
      // Includes all package tests and source files into karma. Those files will be watched.
      // This pattern also matches all all sourcemap files and TypeScript files for debugging.
      {pattern: 'dist/packages/**/*', included: false, watched: true},
      { pattern: './src/test.ts', watched: false },
      { pattern: './node_modules/@angular/material/prebuilt-themes/indigo-pink.css' }
    ],
    client:{
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    coverageIstanbulReporter: {
      reports: [ 'html', 'lcovonly' ],
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: 'dev'
    },
    reporters: ['progress', 'kjhtml'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['PhantomJS'],
    singleRun: true
  });
};

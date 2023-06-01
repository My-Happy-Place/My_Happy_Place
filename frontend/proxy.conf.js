const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'https://myhappyplace-production.up.railway.app/',
    secure: false,
    logLevel: 'debug',
    pathRewrite: { '^/api': '' }
  }
];

module.exports = PROXY_CONFIG;

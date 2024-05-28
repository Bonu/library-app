export const oktaconfig = {
    clientId: '0oahbhnuni1Uig2cH5d7',
    issuer: 'https://dev-07192946.okta.com/oauth2/default',
    redirectUri:'http://localhost:3000/login/callback',
    scopes: ['openid','profile','email'],
    pkce: true,
    disableHttpsCheck: true,
}
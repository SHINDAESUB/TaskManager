// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
  'login test': function (browser){
    browser
      .url(process.env.VUE_DEV_SERVER_URL + 'login') // localhost:port/login 호출
      .waitForElementVisible('#app', 5000) //app 요소가 5초안에 보이는지 검증
      .assert.containsText('h1', 'TaskManager') //h1 요소가 'TaskManager' 포함하는지 검증
      .end()
  },
}

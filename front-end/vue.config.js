// port를 3000 으로 변경하고 , API 요청 (/api/)은 8080 으로 가도록
module.exports = {
  devServer: {
    port: 3000,
    proxy:{
      '/api/*': {
           target: 'http://localhost:8080' //모든 API 요청은 Back-end 에게 갈 수 있도록 설정
      },
      '/sc/*': {
            target: 'http://localhost:8080' //소켓 통신 프록시 설정
      }  
    }
  },
  configureWebpack: { // 웹팩에 새로운 진입점 만들기
    entry: {
      app: './src/main.js',
      style: [
        'bootstrap/dist/css/bootstrap.min.css'
      ]
    }
  }
}



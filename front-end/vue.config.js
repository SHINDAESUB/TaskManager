// port를 3000 으로 변경하고 , API 요청 (/api/)은 8080 으로 가도록
module.exports = {
  devServer: {
    port: 3000,

    proxy:{
      '/api/*': {
           target: 'http://localhost:8080' //모든 API 요청은 Back-end 에게 갈 수 있도록 설정
      }
    }
  }
}

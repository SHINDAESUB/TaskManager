export default{
    login (detail) {
        return new Promise((resolve, reject) => {
            (detail.username === "testname" || detail.username === "test@test.com") && detail.password === 'testpassword!' ? resolve({result: 'success'}) : reject(new Error('인증 실패'))
        })
    }
}
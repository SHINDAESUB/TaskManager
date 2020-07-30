import axios from 'axios'

export default{
    login (detail) {
        return new Promise((resolve,reject) => {
            axios.post('/login',detail)
              .then(({data}) => { 
                resolve(data)})
              .catch((error) => {
                reject(error)
            })
        })
    }
}
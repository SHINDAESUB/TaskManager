import axios from 'axios'
import errorParser from '@/utils/errorParser'

export default{
    login (detail) {
        return new Promise((resolve,reject) => {
            axios.post('/login',detail)
              .then(({data}) => { 
                resolve(data)})
              .catch((error) => {
                reject(errorParser.parse(error))
            })
        })
    }
}
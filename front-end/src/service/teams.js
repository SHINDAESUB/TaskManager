import axios from 'axios'
import errorParser from '@/utils/errorParser'

/* 팀생성  */
export default{
    create (detail) {
        return new Promise((resolve, reject) => {
            axios.post('/teams',detail).then(({data}) => {
                resolve(data)
            }).catch((error) =>{
                reject(errorParser.parse(error))
            })
        })
    }
}
import axios from 'axios'
import errorParser from '@/utils/errorParser'

/* 게시판 생성  */
export default{
    create (detail) {
        return new Promise((resolve, reject) => {
            axios.post('/boards',detail).then(({data}) => {
                resolve(data)
            }).catch((error) =>{
                reject(errorParser.parse(error))
            })
        })
    }
}
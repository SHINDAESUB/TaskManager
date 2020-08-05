import infoService from '@/service/info'

export const getInfoData = ({ commit }) => {
    infoService.getInfoData().then(data => {
    commit('updateInfoData', data)  // commit (mutation 에서 정의한 메소드 이름, data )
  })
}

export const addTeam = ({commit}, team) => {
  commit('addTeam', team)
}

export const addBoard = ({commit}, board) => {
  commit('addBoard', board)
}
export default {
    /* api/info를 통해 정보들을 업데이트 */
    updateInfoData (state, data) {
      state.user.name = data.user.name
      state.user.authenticated = true
      state.teams = data.teams
      state.boards = data.boards
    },
    logout (state) {
      state.user.name = ''
      state.user.authenticated = false
      state.teams = []
      state.boards = []
    },
    /* 새로 생성된 팀 추가 */
    addTeam (state, team) {
      state.teams.push(team)
    },
    /* 새로 생성된 게시판 추가 */
    addBoard (state, board) {
      state.boards.push(board)
    }
  }
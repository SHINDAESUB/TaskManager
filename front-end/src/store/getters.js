export const user = state => state.user
// export const user = state => { return {name: '관리자'} }

export const hasBoards = state => {
   return state.boards.length > 0
  // return true
}

export const myBoards = state => {
  return state.boards.filter(board => board.teamId === 0)
  // return [{
  //   id: 1,
  //   name: '개인 보드입니다.',
  //   description: '개인보드의 내용입니다. 이것은 2020년에 작성되었습니다.'
  // }]
}

export const teamBoards = state => {
  const teams = []
  
  state.teams.forEach(team => {
    teams.push({
      id: team.id,
      name: team.name,
      boards: state.boards.filter(board => board.teamId === team.id)
    })
  })
  
  return teams
  // return [{
  //   id: 1,
  //   name: '팀 게시판',
  //   boards: [{
  //     id: 2,
  //     name: '팀에 첫번쨰 계획',
  //     description: '2020년안에 프로젝트 마무리'
  //   }, {
  //     id: 3,
  //     name: '두번쨰 계획',
  //     description: '완벽한 프론트엔드 개발자 되기입니다.'
  //   }]
  // }]
}
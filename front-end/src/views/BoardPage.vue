<template>
  <div class="page">
    <Header />
    <div class="page-body">
      <div class="board-wrapper">
        <div class="board">
          <div class="board-header clearfix">
            <div class="board-name board-header-item">{{ board.name }}</div>
            <div class="board-header-divider"></div>
            <div class="team-name board-header-item">
              <span v-if="!board.personal">{{ team.name }}</span>
              <span v-if="board.personal">개인 게시물</span>
            </div>
            <div class="board-header-divider"></div>
            <div class="board-members board-header-item">
              <div class="member" v-for="member in members" v-bind:key="member.id">
                <span>{{ member.shortName }}</span>
              </div>
              <div class="member add-member-toggle" @click="openAddMember()">
                <span><font-awesome-icon icon="user-plus" /></span>
              </div>
            </div>
          </div>
           <div class="board-body">  
            <Draggable v-model="cardLists" class="list-container" @end="onCardListDragEnded" 
              :options="{handle: '.list-header', animation: 0, scrollSensitivity: 100, touchStartThreshold: 20}"> <!-- 카드 리스트 위치 변경 -->
              <div class="list-wrapper" v-for="cardList in cardLists" v-bind:key="cardList.id">
                <div class="list">
                  <div class="list-header">{{ cardList.name }}</div>
                  <Draggable class="cards" v-model="cardList.cards" @end="onCardDragEnded"
                    :options="{draggable: '.card-item', group: 'cards', ghostClass: 'ghost-card',
                    animation: 0, scrollSensitivity: 100, touchStartThreshold: 20}"
                    v-bind:data-list-id="cardList.id">  <!-- 카드 리스트 위치 변경 -->
                    <div class="card-item" v-for="card in cardList.cards" v-bind:key="card.id">
                      <div class="card-title">{{ card.title }}</div>
                    </div>
                    <div class="add-card-form-wrapper" v-if="cardList.cardForm.open">
                      <form @submit.prevent="addCard(cardList)" class="add-card-form">
                        <div class="form-group">
                          <textarea class="form-control" v-model="cardList.cardForm.title" v-bind:id="'cardTitle' + cardList.id"
                            @keydown.enter.prevent="addCard(cardList)" placeholder="Type card title here"></textarea>
                        </div>
                        <button type="submit" class="btn btn-sm btn-primary">추가</button>
                        <button type="button" class="btn btn-sm btn-link btn-cancel" @click="closeAddCardForm(cardList)">취소</button>
                      </form>
                    </div>
                  </Draggable>
                  <div class="add-card-button" v-show="!cardList.cardForm.open" @click="openAddCardForm(cardList)">+ 추가할 카드</div>
                </div>
              </div>
              <div class="list-wrapper add-list">
                <div class="add-list-button" v-show="!addListForm.open" @click="openAddListForm()">+ 추가할 리스트</div>
                <form @submit.prevent="addCardList()" v-show="addListForm.open" class="add-list-form">
                  <div class="form-group">
                    <input type="text" class="form-control" v-model="addListForm.name" id="cardListName" placeholder="Type list name here" />
                  </div>
                  <button type="submit" class="btn btn-sm btn-primary">Add List</button>
                  <button type="button" class="btn btn-sm btn-link btn-cancel" @click="closeAddListForm()">Cancel</button>
                </form>
              </div>
            </Draggable>
          </div>
        </div>
      </div>
    </div>
    <AddMemberModal
      :boardId="board.id"
      @added="onMemberAdded"/>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
import Draggable from 'vuedraggable'
import $ from 'jquery'
import AddMemberModal from '@/modal/AddMemberModal.vue'
import notify from '@/utils/notify'
import boardService from '@/service/boards'
import cardListService from '@/service/card-lists'
import cardService from '@/service/cards'

export default {
  name: 'BoardPage',
  data () {
    return {
      board: { id: 0, name: '', personal: false },
      cardLists: [/* {id, name, cards, cardForm} */],
      team: { name: '' },
      members: [/* {id, shortName} */],
      addListForm: {
        open: false,
        name: ''
      }
    }
  },
  components: {
    Header,
    AddMemberModal,
    Draggable
  },
  beforeRouteEnter (to, from, next) {
    boardService.getBoard(to.params.boardId).then(data => {
      next(vm => {
        vm.team.name = data.team ? data.team.name : ''
        vm.board.id = data.board.id
        vm.board.personal = data.board.personal
        vm.board.name = data.board.name
        data.members.forEach(member => {
          vm.members.push({
            id: member.userId,
            shortName: member.shortName
          })
        })
        data.cardLists.sort((list1, list2) => {
          return list1.position - list2.position
        })
        data.cardLists.forEach(cardList => {
          cardList.cards.sort((card1, card2) => {
            return card1.position - card2.position
          })
          vm.cardLists.push({
            id: cardList.id,
            name: cardList.name,
            cards: cardList.cards,
            cardForm: {
              open: false,
              title: ''
            }
          })
        })
      })
      vm.$sc.subscribe('/boards/' + vm.board.id, vm.onSocketClientUpdated)
    }).catch(error => {
      notify.error(error.message)
    })
  },
  mounted () {
    this.$el.addEventListener('click', this.dismissActiveForms)
  },
  beforeDestroy () {
    this.$el.removeEventListener('click', this.dismissActiveForms)
    this.$sc.unsubscribe('/boards/' + this.board.id, this.onSocketClientUpdated)
  },
  methods: {
    dismissActiveForms (event) {
      console.log('[BoardPage] forms 초기화')
      let dismissAddCardForm = true
      let dismissAddListForm = true
      if (event.target.closest('.add-card-form') || event.target.closest('.add-card-button')) {
        dismissAddCardForm = false
      }
      if (event.target.closest('.add-list-form') || event.target.closest('.add-list-button')) {
        dismissAddListForm = false
      }
      if (dismissAddCardForm) {
        this.cardLists.forEach((cardList) => { cardList.cardForm.open = false })
      }
      if (dismissAddListForm) {
        this.addListForm.open = false
      }
    },
    openAddMember () {
      $('#addMemberModal').modal('show')
    },
    onMemberAdded (member) {
      this.members.push(member)
    },
    addCardList () {
      if (!this.addListForm.name) {
        return
      }
      const cardList = {
        boardId: this.board.id,
        name: this.addListForm.name,
        position: this.cardLists.length + 1
      }
      cardListService.add(cardList).then(savedCardList => {
        this.cardLists.push({
          id: savedCardList.id,
          name: savedCardList.name,
          cards: [],
          cardForm: {
            open: false,
            title: ''
          }
        })
        this.closeAddListForm()
      }).catch(error => {
        notify.error(error.message)
      })
    },
    addCard (cardList) {
      if (!cardList.cardForm.title.trim()) {
        return
      }
      const card = {
        boardId: this.boardId,
        cardListId: cardList.id,
        title: cardList.cardForm.title,
        position: cardList.cards.length + 1
      }
      cardService.add(card).then(savedCard => {
        cardList.cards.push({
          id: savedCard.id,
          title: savedCard.title
        })
        cardList.cardForm.title = ''
        this.focusCardForm(cardList)
      }).catch(error => {
        notify.error(error.message)
      })
    },
    openAddListForm () {
      this.addListForm.open = true
      this.$nextTick(() => {
        $('#cardListName').trigger('focus')
      })
    },
    closeAddListForm () {
      this.addListForm.open = false
      this.addListForm.name = ''
    },
    openAddCardForm (cardList) {
      // Close other add card form
      this.cardLists.forEach((cardList) => { cardList.cardForm.open = false })
      cardList.cardForm.open = true
      this.focusCardForm(cardList)
    },
    focusCardForm (cardList) {
      this.$nextTick(() => { $('#cardTitle' + cardList.id).trigger('focus') })
    },
    closeAddCardForm (cardList) {
      cardList.cardForm.open = false
    },
    onCardListDragEnded (event) {
      console.log('[BoardPage] 카드 드래그 끝나면 실행 되는 이벤트', event)

      const positionChanges = {
        boardId: this.board.id,
        cardListPositions: []
      }
      this.cardLists.forEach((cardList, index) => {
        positionChanges.cardListPositions.push({
          cardListId: cardList.id,
          position: index + 1
        })
      })
      cardListService.changePositions(positionChanges).catch(error => {
        notify.error(error.message)
      })
    },
    onCardDragEnded (event) {
      console.log('[BoardPage] 카드 드래그 끝나면 실행 되는 이벤트', event)

      const fromListId = event.from.dataset.listId
      const toListId = event.to.dataset.listId
      const changedListIds = [fromListId]
      if (fromListId !== toListId) {
        changedListIds.push(toListId)
      }
      const positionChanges = {
        boardId: this.board.id,
        cardPositions: []
      }
      changedListIds.forEach(cardListId => {
        const cardList = this.cardLists.filter(cardList => { return cardList.id === parseInt(cardListId) })[0]
        cardList.cards.forEach((card, index) => {
          positionChanges.cardPositions.push({
            cardListId: cardListId,
            cardId: card.id,
            position: index + 1
          })
        })
      })
      cardService.changePositions(positionChanges).catch(error => {
        notify.error(error.message)
      })
    },
    onSocketClientUpdated (updates) {

    }
  }
}
</script>

<style lang="scss" scoped>
.page-body {
  flex-grow: 1;
  position: relative;
  overflow-y: auto;
  .board-wrapper {
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    .board {
      height: 100%;
      display: flex;
      flex-direction: column;
      .board-header {
        flex: none;
        height: auto;
        overflow: hidden;
        position: relative;
        padding: 8px 4px 8px 8px;
        .board-header-divider {
          float: left;
          border-left: 1px solid #ddd;
          height: 16px;
          margin: 8px 10px;
        }
        .board-header-item {
          float: left;
          height: 32px;
          line-height: 32px;
          margin: 0 4px 0 0;
        }
        .board-name {
          font-size: 18px;
          line-height: 32px;
          padding-left: 4px;
          text-decoration: none;
        }
        .board-members {
          .member {
            display: block;
            float: left;
            height: 30px;
            width: 30px;
            margin: 0 0 0 -2px;
            border-radius: 50%;
            background-color: #377EF6;
            position: relative;
            span {
              height: 30px;
              line-height: 30px;
              width: 30px;
              text-align: center;
              display: block;
              color: #fff;
            }
          }
          .add-member-toggle {
            margin-left: 5px;
            background-color: #eee;
            cursor: pointer;
            svg {
              font-size: 10px;
              position: absolute;
              top: 9px;
              left: 9px;
              color: #000;
            }
          }
          .add-member-toggle:hover {
            background-color: #666;
            svg {
              color: #fff;
            }
          }
        }
      }
      .board-body {
        position: relative;
        flex-grow: 1;
        .list-container {
          position: absolute;
          top: 0;
          left: 8px;
          right: 0;
          bottom: 0;
          overflow-x: auto;
          overflow-y: hidden;
          white-space: nowrap;
          margin-bottom: 6px;
          padding-bottom: 6px;
          .list-wrapper {
            width: 272px;
            margin: 0 4px;
            height: 100%;
            box-sizing: border-box;
            display: inline-block;
            vertical-align: top;
            white-space: nowrap;
            .list {
              background: #eee;
              border-radius: 3px;
              box-sizing: border-box;
              display: flex;
              flex-direction: column;
              max-height: 100%;
              white-space: normal;
              position: relative;
              .list-header {
                padding: .55rem .75rem;
                font-weight: 600;
                cursor: pointer;
              }
              .add-card-button {
                padding: 8px 10px;
                color: #888;
                cursor: pointer;
                border-bottom-left-radius: 3px;
                border-bottom-right-radius: 3px;
              }
              .add-card-button:hover {
                background: #dfdfdf;
                color: #333;
              }
              .add-card-form-wrapper {
                padding: 0 8px 8px;
                .form-group {
                  margin-bottom: 5px;
                  textarea {
                    resize: none;
                    padding: 0.30rem 0.50rem;
                    box-shadow: none;
                  }
                }
              }
              .cards {
                overflow-y: auto;
                min-height: 1px;
                .card-item {
                  overflow: hidden;
                  background: #fff;
                  padding: 5px 8px;
                  border-radius: 4px;
                  margin: 0 8px 8px;
                  box-shadow: 0 1px 0 #ccc;
                  cursor: pointer;
                  .card-title {
                    margin: 0;
                  }
                }
                .ghost-card {
                  background-color: #ccc !important;
                  color: #377EF6 !important;
                }
              }
            }
            .ghost-list .list {
              background: #aaa;
            }
          }
          .list-wrapper.add-list {
            background: #f4f4f4;
            border-radius: 3px;
            box-sizing: border-box;
            height: auto;
            color: #888;
            margin-right: 8px;
            .add-list-button {
              padding: 8px 10px;
            }
            .add-list-button:hover {
              background: #ddd;
              cursor: pointer;
              border-radius: 3px;
              color: #333;
            }
            form  {
              padding: 5px;
              .form-group {
                margin-bottom: 5px;
                .form-control {
                  height: calc(1.80rem + 2px);
                  padding: .375rem .3rem;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
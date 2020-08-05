<template>
    <form @submit.prevent="saveBoard">
    <div class="modal" tabindex="-1" role="dialog" backdrop="static" id="createBoardModal">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">게시판 생성</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group">
              <input type="text" class="form-control" id="boardNameInput" v-model="board.name" placeholder="게시판 제목" maxlength="128">
              <div class="field-error" v-if="$v.board.name.$dirty">
                <div class="error" v-if="!$v.board.name.required">제목 입력이 필요합니다.</div>
              </div>
            </div>
            <div class="form-group">
              <textarea class="form-control" v-model="board.description" placeholder="게시판에 대한 설명을 해주세요"></textarea>
              <div class="field-error" v-if="$v.board.description.$dirty">
                <div class="error" v-if="!$v.board.description.required">설명을 입력해주세요</div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">생성</button>
            <button type="button" class="btn btn-default btn-cancel" @click="close">취소</button>
          </div>
        </div>
      </div>
    </div>
    </form>
</template>

<script>
import $ from 'jquery'
import { required } from 'vuelidate/lib/validators'
import boardService from '@/service/borders'

export default {
    name: 'CreateBoardModal',
    props: ['teamId'], /* Homepage.vue 에서 받아옴 */
    data() {
        return{
            board: {
                name: '',
                description: ''
            },
            errorMessage: ''
        }
    },
    validations: {
        board: {
            name: {
                required
            },
            description: {
                required
            }
        }
    },
  mounted () {
    $('#createTeamModal').on('shown.bs.modal', () => {
      $('#teamNameInput').trigger('focus')
    })
  },
    methods: {
        saveBoard () {
        this.$v.$touch()
        if (this.$v.$invalid) {
            return
        }
        const board = {
            teamId: this.teamId,
            name: this.board.name,
            description: this.board.description
        }
        boardService.create(board).then((createdBoard) => {
            this.$store.dispatch('addBoard', createdBoard)
            this.$emit('created', createdBoard.id)  /* Homepage.vue 에게 보냄 */
            this.close()
            }).catch(error => {
                this.errorMessage = error.message
            })
        },
        close () {
            this.$v.$reset()
            this.board.name = ''
            this.board.description = ''
            this.errorMessage = ''
            $('#createBoardModal').modal('hide')
        }
    }
}
</script>

<style lang="scss" scoped>
.modal {
  .modal-dialog {
    width: 400px;
  }
}
</style>
<template>
  <form @submit.prevent="saveTeam">
    <div class="modal" tabindex="-1" role="dialog" backdrop="static" id="createTeamModal">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">팀 생성</h5>
            <button type="button" class="close" @click="close" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
            <div class="form-group">
              <input type="text" class="form-control" id="teamNameInput" v-model="team.name" placeholder="팀 이름을 입력해주세요" maxlength="128">
              <div class="field-error" v-if="$v.team.name.$dirty">
                <div class="error" v-if="!$v.team.name.required">팀 이름을 입력해주세요</div>
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
import teamService from '@/service/teams'

export default {
  name: 'CreateTeamModal',
  data () {
    return {
      team: {
        name: ''
      },
      errorMessage: ''
    }
  },
  validations: {
    team: {
      name: {
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
    saveTeam () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }
      teamService.create(this.team).then((createdTeam) => {
        this.$store.dispatch('addTeam', createdTeam)
        this.close()
      }).catch(error => {
        this.errorMessage = error.message
      })
    },
    close () {
      this.$v.$reset()
      this.team.name = ''
      this.errorMessage = ''
      $('#createTeamModal').modal('hide')
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
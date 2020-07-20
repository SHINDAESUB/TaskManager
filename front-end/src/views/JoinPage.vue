<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="join-form">
        <div class="join-title">
          <h1>계정생성</h1>
        </div>
        <form  @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">
            {{errorMessage}}
          </div>
          <div class="form-group">
	         <label for="username">이름</label>
           <input type="text" class="form-control"  id="username" v-model="form.username">
          </div>
          <div class="form-group">
           <label for="emailAddress">메일</label>
           <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
          </div>
          <div class="form-group">
           <label for="password">비밀번호</label>
           <input type="password" class="form-control" id="password" v-model="form.password">
          </div>
          <button type="submit" class="btn btn-primary btn-block">계정생성</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import joinService from '@/service/join'

export default {
  name: 'JoinPage',
  data(){
    return {
      form : {
        username: '',
        emailAddress : '',
        password : '',
      },
      errorMessage : ''
    }
  },
  methods: {
    submitForm(){
      joinService.join(this.form).then(()=>{
        this.$router.push({name: 'LoginPage'})
      }).catch((error) => {
        this.errorMessage = "등록에 실패 했습니다." + (error.massage ? error.message : '알수 없는 이유')
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.container {
  max-width: 900px;
}
.join-form {
  margin-top: 50px;
  max-width: 320px;
}
.join-form {
  .form-group label {
    font-weight: bold;
    color: #555;
  }
  .accept-terms {
    margin: 20px 0 40px 0;
  }
}

.join-title {
  margin-bottom: 50px;
}

</style>

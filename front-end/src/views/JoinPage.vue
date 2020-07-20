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
           	<div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">이름을 입력해주세요</div>
              <div class="error" v-if="!$v.form.username.alphaNum">이름은 문자나 숫자만 입력가능합니다</div>
              <div class="error" v-if="!$v.form.username.minLength">이름은 적어도 {{$v.form.username.$params.minLength.min}} 이상 입력하셔야 합니다.</div>
              <div class="error" v-if="!$v.form.username.maxLength">이름은 최대 {{$v.form.username.$params.maxLength.max}} 가능합니다.</div>
            </div>
          </div>
          <div class="form-group">
           <label for="emailAddress">메일</label>
           <input type="email" class="form-control" id="emailAddress" v-model="form.emailAddress">
            <div class="field-error" v-if="$v.form.emailAddress.$dirty">
              <div class="error" v-if="!$v.form.emailAddress.required">이메일을 입력해주세요</div>
              <div class="error" v-if="!$v.form.emailAddress.email">이메일 형식에 맞게 적어주세요</div>
              <div class="error" v-if="!$v.form.emailAddress.maxLength">이메일은 최대 {{$v.form.emailAddress.$params.maxLength.max}} 입력가능합니다.</div>
            </div>
          </div>
          <div class="form-group">
           <label for="password">비밀번호</label>
           <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">비밀번호를 입력해주세요</div>
              <div class="error" v-if="!$v.form.password.minLength">비밀번호는 최소한 {{$v.form.password.$params.minLength.min}} 이상 입력해주세요</div>
              <div class="error" v-if="!$v.form.password.maxLength">비밀번호는 최대 {{$v.form.password.$params.maxLength.max}} 이하로 입력해주세요</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">계정생성</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import joinService from '@/service/join'
import { required , email , minLength , maxLength , alphaNum}  from 'vuelidate/lib/validators'

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

  validations: {
    form: {
      username:{
        required,
        minLength: minLength(2),
        maxLength: maxLength(50),
        alphaNum
      },
      emailAddress:{
        required,
        email,
        maxLength: maxLength(100)
      },
      password:{
        required,
        minLength: minLength(6),
        maxLength: maxLength(30)
      }
    }
  },
  methods: {
    submitForm(){
      //'$v' 객체는 Vuelidata 현재 상태
      this.$v.$touch() //데이터 검증 시작하는 메소드
      if(this.$v.$invalid){ //결과 확인 (검증 실패일 경우 'true')
        return
      }

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

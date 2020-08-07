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
	         <label for="username">ID</label>
           <input type="text" class="form-control"  id="username" v-model="form.username">
           	<div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">ID를 입력해주세요</div>
              <div class="error" v-if="!$v.form.username.alphaNum">ID은 문자나 숫자만 입력가능합니다</div>
              <div class="error" v-if="!$v.form.username.minLength">ID는 적어도 {{$v.form.username.$params.minLength.min}} 이상 입력하셔야 합니다.</div>
              <div class="error" v-if="!$v.form.username.maxLength">ID는 최대 {{$v.form.username.$params.maxLength.max}} 가능합니다.</div>
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
            <label for="firstName">성</label>
            <input type="text" class="form-control" id="firstName" v-model="form.firstName">
            <div class="field-error" v-if="$v.form.firstName.$dirty">
              <div class="error" v-if="!$v.form.firstName.required">성을 입력해주세요</div>
              <div class="error" v-if="!$v.form.firstName.alpha">성은 문자나 숫자만 입력 가능합니다.</div>
              <div class="error" v-if="!$v.form.firstName.minLength">성은 적어도{{$v.form.firstName.$params.minLength.min}} 입력 해야합니다.</div>
              <div class="error" v-if="!$v.form.firstName.maxLength">성은 최대 {{$v.form.firstName.$params.maxLength.max}} 입력 가능합니다.</div>
            </div>
          </div>
          <div class="form-group">
            <label for="lastName">이름</label>
            <input type="text" class="form-control" id="lastName" v-model="form.lastName">
            <div class="field-error" v-if="$v.form.lastName.$dirty">
              <div class="error" v-if="!$v.form.lastName.required">이름을 입력해주세요</div>
              <div class="error" v-if="!$v.form.lastName.alpha">이름은 문자만 입력 가능합니다.</div>
              <div class="error" v-if="!$v.form.lastName.minLength">이름은 적어도{{$v.form.lastName.$params.minLength.min}}입력 해야합니다.</div>
              <div class="error" v-if="!$v.form.lastName.maxLength">이름은 최대{{$v.form.lastName.$params.maxLength.max}}입력 가능합니다.</div>
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
    <Footer/>
  </div>
</template>

<script>
import joinService from '@/service/join'
import { required, email, minLength, maxLength, alphaNum, alpha } from 'vuelidate/lib/validators'
import Footer from '@/components/Footer.vue'

export default {
  name: 'JoinPage',
  data(){
    return {
      form : {
        username: '',
        emailAddress : '',
        firstName: '',
        lastName: '',
        password : '',
      },
      errorMessage : ''
    }
  },
  components: {
    Footer
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
      firstName: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(45),
        alpha
      },
      lastName: {
        required,
        minLength: minLength(1),
        maxLength: maxLength(45),
        alpha
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
        this.$router.push({name: 'login'})
      }).catch((error) => {
        this.errorMessage = "등록에 실패 했습니다." + (error.massage ? error.message : '알수 없는 이유')
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.accept-terms {
  max-width: 900px;	 
   margin: 20px 0 40px 0;
}
</style>

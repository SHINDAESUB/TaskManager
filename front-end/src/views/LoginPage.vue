<template>
  <div class="container public">
    <div class="row justify-content-center">
      <div class="form">
        <form @submit.prevent="submitForm">
          <div v-show="errorMessage" class="alert alert-danger failed">{{ errorMessage }}</div>
          <div class="form-group">
            <label for="username">이름 or 이메일</label>
            <input type="text" class="form-control" id="username" v-model="form.username">
            <div class="field-error" v-if="$v.form.username.$dirty">
              <div class="error" v-if="!$v.form.username.required">이름 또는 이메일을 입력해주세요</div>
            </div>
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" v-model="form.password">
            <div class="field-error" v-if="$v.form.password.$dirty">
              <div class="error" v-if="!$v.form.password.required">비밀번호 입력해주세요</div>
            </div>
          </div>
          <button type="submit" class="btn btn-primary btn-block">로그인</button>
          <div class="links">
            <p class="sign-up text-muted">Don't have an account yet? <a href="/join" class="link-sign-up">회원가입</a></p>
            <p class="link-forgot-password"><a href="#">비밀번호 분실</a></p>
          </div>
        </form>
      </div>
    </div>
    <Footer/>
  </div>              
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import loginService from '@/service/login'
import Footer from '@/components/Footer.vue'

export default {
  name: 'LoginPage',
  data(){
    return {
      form: {
        username: '',
        password: '' 
      },
      errorMessage: ''
    }
  },
  components: {
    Footer
  },
  validations: {
    form: {
      username: {
        required
      },
      password: {
        required
      }
    }
  },  
  methods: {
    submitForm () {
      this.$v.$touch()
      if (this.$v.$invalid) {
        return
      }
      loginService.login(this.form).then(() => {
        this.$router.push({name: 'home'})
      }).catch((error) => {
        this.errorMessage = error.message
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.links {
  margin: 30px 0 50px 0;
  text-align: center;
}
</style>
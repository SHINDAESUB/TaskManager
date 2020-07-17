<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="register-form">
        <div class="register-title">
          <h1>계정생성</h1>
        </div>
        <form>
          <div class="form-group">
	         <label for="username">이름</label>
           <input type="text" class="form-control" id="username">
          </div>
          <div class="form-group">
           <label for="emailAddress">메일</label>
           <input type="email" class="form-control" id="emailAddress">
          </div>
          <div class="form-group">
           <label for="password">비밀번호</label>
           <input type="password" class="form-control" id="password">
          </div>
          <button type="submit" class="btn btn-primary btn-block">계정생성</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RegisterPage'
}
</script>

<style lang="scss" scoped>
.container {
  max-width: 900px;
}
.register-form {
  margin-top: 50px;
  max-width: 320px;
}
.register-form {
  .form-group label {
    font-weight: bold;
    color: #555;
  }
  .accept-terms {
    margin: 20px 0 40px 0;
  }
}

.register-title {
  margin-bottom: 50px;
}

</style>

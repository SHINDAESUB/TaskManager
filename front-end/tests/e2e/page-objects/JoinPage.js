module.exports = {
  url: function () {
    return this.api.launchUrl + 'join'
  },
  elements: {
    app: {
      selector: '#app'
    },
    usernameInput: {
      selector: '#username'
    },
    emailAddressInput: {
      selector: '#emailAddress'
    },
    passwordInput: {
      selector: '#password'
    },
    submitButton: {
      selector: 'button[type=submit]'
    },
    formError: {
      selector: '.failed'
    }
  },
  commands: [{
    register: function (username, emailAddress, password) {
      this
        .setValue('@usernameInput', username)
        .setValue('@emailAddressInput', emailAddress)
        .setValue('@passwordInput', password)
        .click('@submitButton')
    }
  }]
}

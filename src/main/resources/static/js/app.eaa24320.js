(function(e){function r(r){for(var a,o,i=r[0],m=r[1],u=r[2],c=0,d=[];c<i.length;c++)o=i[c],Object.prototype.hasOwnProperty.call(s,o)&&s[o]&&d.push(s[o][0]),s[o]=0;for(a in m)Object.prototype.hasOwnProperty.call(m,a)&&(e[a]=m[a]);l&&l(r);while(d.length)d.shift()();return n.push.apply(n,u||[]),t()}function t(){for(var e,r=0;r<n.length;r++){for(var t=n[r],a=!0,i=1;i<t.length;i++){var m=t[i];0!==s[m]&&(a=!1)}a&&(n.splice(r--,1),e=o(o.s=t[0]))}return e}var a={},s={app:0},n=[];function o(r){if(a[r])return a[r].exports;var t=a[r]={i:r,l:!1,exports:{}};return e[r].call(t.exports,t,t.exports,o),t.l=!0,t.exports}o.m=e,o.c=a,o.d=function(e,r,t){o.o(e,r)||Object.defineProperty(e,r,{enumerable:!0,get:t})},o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,r){if(1&r&&(e=o(e)),8&r)return e;if(4&r&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(o.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&r&&"string"!=typeof e)for(var a in e)o.d(t,a,function(r){return e[r]}.bind(null,a));return t},o.n=function(e){var r=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(r,"a",r),r},o.o=function(e,r){return Object.prototype.hasOwnProperty.call(e,r)},o.p="/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],m=i.push.bind(i);i.push=r,i=i.slice();for(var u=0;u<i.length;u++)r(i[u]);var l=m;n.push(["56d7","chunk-vendors"]),t()})({"56d7":function(e,r,t){"use strict";t.r(r);t("d3b7"),t("e260"),t("e6cf"),t("cca6"),t("a79d");var a=t("2b0e"),s=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},n=[],o={name:"App"},i=o,m=(t("5c0b"),t("2877")),u=Object(m["a"])(i,s,n,!1,null,null,null),l=u.exports,c=t("8c4f"),d=function(){var e=this,r=e.$createElement;e._self._c;return e._m(0)},f=[function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",[t("h1",[e._v("TaskManager")])])}],v={name:"LoginPage"},p=v,h=Object(m["a"])(p,d,f,!1,null,null,null),g=h.exports,_=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"container"},[t("div",{staticClass:"row justify-content-center"},[t("div",{staticClass:"join-form"},[e._m(0),t("form",{on:{submit:function(r){return r.preventDefault(),e.submitForm(r)}}},[t("div",{directives:[{name:"show",rawName:"v-show",value:e.errorMessage,expression:"errorMessage"}],staticClass:"alert alert-danger failed"},[e._v(" "+e._s(e.errorMessage)+" ")]),t("div",{staticClass:"form-group"},[t("label",{attrs:{for:"username"}},[e._v("이름")]),t("input",{directives:[{name:"model",rawName:"v-model",value:e.form.username,expression:"form.username"}],staticClass:"form-control",attrs:{type:"text",id:"username"},domProps:{value:e.form.username},on:{input:function(r){r.target.composing||e.$set(e.form,"username",r.target.value)}}}),e.$v.form.username.$dirty?t("div",{staticClass:"field-error"},[e.$v.form.username.required?e._e():t("div",{staticClass:"error"},[e._v("이름을 입력해주세요")]),e.$v.form.username.alphaNum?e._e():t("div",{staticClass:"error"},[e._v("이름은 문자나 숫자만 입력가능합니다")]),e.$v.form.username.minLength?e._e():t("div",{staticClass:"error"},[e._v("이름은 적어도 "+e._s(e.$v.form.username.$params.minLength.min)+" 이상 입력하셔야 합니다.")]),e.$v.form.username.maxLength?e._e():t("div",{staticClass:"error"},[e._v("이름은 최대 "+e._s(e.$v.form.username.$params.maxLength.max)+" 가능합니다.")])]):e._e()]),t("div",{staticClass:"form-group"},[t("label",{attrs:{for:"emailAddress"}},[e._v("메일")]),t("input",{directives:[{name:"model",rawName:"v-model",value:e.form.emailAddress,expression:"form.emailAddress"}],staticClass:"form-control",attrs:{type:"email",id:"emailAddress"},domProps:{value:e.form.emailAddress},on:{input:function(r){r.target.composing||e.$set(e.form,"emailAddress",r.target.value)}}}),e.$v.form.emailAddress.$dirty?t("div",{staticClass:"field-error"},[e.$v.form.emailAddress.required?e._e():t("div",{staticClass:"error"},[e._v("이메일을 입력해주세요")]),e.$v.form.emailAddress.email?e._e():t("div",{staticClass:"error"},[e._v("이메일 형식에 맞게 적어주세요")]),e.$v.form.emailAddress.maxLength?e._e():t("div",{staticClass:"error"},[e._v("이메일은 최대 "+e._s(e.$v.form.emailAddress.$params.maxLength.max)+" 입력가능합니다.")])]):e._e()]),t("div",{staticClass:"form-group"},[t("label",{attrs:{for:"password"}},[e._v("비밀번호")]),t("input",{directives:[{name:"model",rawName:"v-model",value:e.form.password,expression:"form.password"}],staticClass:"form-control",attrs:{type:"password",id:"password"},domProps:{value:e.form.password},on:{input:function(r){r.target.composing||e.$set(e.form,"password",r.target.value)}}}),e.$v.form.password.$dirty?t("div",{staticClass:"field-error"},[e.$v.form.password.required?e._e():t("div",{staticClass:"error"},[e._v("비밀번호를 입력해주세요")]),e.$v.form.password.minLength?e._e():t("div",{staticClass:"error"},[e._v("비밀번호는 최소한 "+e._s(e.$v.form.password.$params.minLength.min)+" 이상 입력해주세요")]),e.$v.form.password.maxLength?e._e():t("div",{staticClass:"error"},[e._v("비밀번호는 최대 "+e._s(e.$v.form.password.$params.maxLength.max)+" 이하로 입력해주세요")])]):e._e()]),t("button",{staticClass:"btn btn-primary btn-block",attrs:{type:"submit"}},[e._v("계정생성")])])])])])},b=[function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"join-title"},[t("h1",[e._v("계정생성")])])}],$=(t("a15b"),t("bc3a")),w=t.n($),x={join:function(e){return new Promise((function(r,t){w.a.post("/join",e).then((function(e){var t=e.data;r(t)})).catch((function(e){t(e)}))}))}},y=t("b5ae"),j={name:"JoinPage",data:function(){return{form:{username:"",emailAddress:"",password:""},errorMessage:""}},validations:{form:{username:{required:y["required"],minLength:Object(y["minLength"])(2),maxLength:Object(y["maxLength"])(50),alphaNum:y["alphaNum"]},emailAddress:{required:y["required"],email:y["email"],maxLength:Object(y["maxLength"])(100)},password:{required:y["required"],minLength:Object(y["minLength"])(6),maxLength:Object(y["maxLength"])(30)}}},methods:{submitForm:function(){var e=this;this.$v.$touch(),this.$v.$invalid||x.join(this.form).then((function(){e.$router.push({name:"LoginPage"})})).catch((function(r){e.errorMessage="등록에 실패 했습니다."+(r.massage?r.message:"알수 없는 이유")}))}}},C=j,L=(t("9308"),Object(m["a"])(C,_,b,!1,null,"74db8785",null)),O=L.exports;a["a"].use(c["a"]);var P=new c["a"]({mode:"history",base:"/",routes:[{path:"/login",name:"LoginPage",component:g},{path:"/join",name:"JoinPage",component:O}]}),A=t("2f62");a["a"].use(A["a"]);var M=new A["a"].Store({state:{},mutations:{},actions:{},modules:{}}),q=t("1dce"),N=t.n(q);w.a.defaults.baseURL="/api",w.a.defaults.headers.common.Accept="application/json",w.a.interceptors.response.use((function(e){return e}),(function(e){return Promise.reject(e)})),a["a"].use(N.a),a["a"].config.productionTip=!1,new a["a"]({router:P,store:M,render:function(e){return e(l)}}).$mount("#app")},"5c0b":function(e,r,t){"use strict";var a=t("9c0c"),s=t.n(a);s.a},9308:function(e,r,t){"use strict";var a=t("def9"),s=t.n(a);s.a},"9c0c":function(e,r,t){},def9:function(e,r,t){}});
//# sourceMappingURL=app.eaa24320.js.map
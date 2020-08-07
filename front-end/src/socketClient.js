import Vue from 'vue'
import SockJS from 'sockjs-client'
import globalBus from '@/eventBus'

/**  
 * 주의점
 * 1.하나의 클라이언트만 만들었기 떄문에 연결을 초기화 장소는 App.vue 
 * 2.App.vue 의 라이프 사이클 created() 호출한다. 
 * 3.초기화 시점은 getInfoData API(api/info) 응답을 처리하는 곳이다.
 * 4.getInfoData API(api/info) 수신하면 전역 이벤트 버스를 활용해서 App.vue 에게 알려준다.
*/
class SocketClient {
    constructor(){
        this.serverUrl = null /* 웹 소켓 서버의 URL */
        this.token = null     /* 실시간 토큰 : 클라이언트가 서버 쪽에서 인증을 수행하는데 사용된다. JWT 토큰 문자열 사용*/
        this.socket = null    /* SockJS의 인스턴스 미며 connect() 메소드에서 생성한다. */
        this.authenticated = false /* 클라이언트가 서버에 인증 됐는지 여부를 나타냄 */
        this.loggedOut = false  /* 사용자가 로그아웃햇고 그로 인해 소켓이 종료됬는지 여부를 나타냄 */
        this.$bus = new Vue()  /* 실시간 통신에 이용하기위해 이벤트버스를 사용한다.  */
        /* 구독 시작 */
        this.subscribeQueue = { 
            /* channel: [handler1, handler2] */
        }
        /* 구독 종료 */
        this.unsubscribeQueue = {
            /* channel: [handler1, handler2] */
        }  
    }
    /* 한번 연결 되면 초기화 되지 않도록 메소드의 시작점에서 보호 */
    init (serverUrl, token) {
        if (this.authenticated) {
          console.warn('[SocketClient] 이미 연결 인증 됨')
          return
        }
        console.log('[SocketClient] 초기화')
        this.serverUrl = serverUrl
        this.token = token
        this.connect()
    }
    /* 소켓의 프로퍼티를 초기화 하고 소켓을 닫아서 클라이언트의 상태를 제거한다. */
    logout () {
        console.log('[SocketClient] 종료')
        this.subscribeQueue = {}
        this.unsubscribeQueue = {}
        this.authenticated = false
        this.loggedOut = true
        this.socket && this.socket.close()
    }    
    connect () {
        console.log('[SocketClient] 연결 됨 ' + this.serverUrl)
        this.socket = new SockJS(this.serverUrl + '?token=' + this.token)
        this.socket.onopen = () => {
          /* 연결이 설정되면 항상 클라이언트를 인증 됨으로 설정하십시오. */
          this.authenticated = true
          this._onConnected()
        }
        this.socket.onmessage = (event) => {
          this._onMessageReceived(event)
        }
        this.socket.onerror = (error) => {
          this._onSocketError(error)
        }
        this.socket.onclose = (event) => {
          this._onClosed(event)
        }
    }    
    /* 관심있는 채널을 구독하기 위한 API  */
    subscribe (channel, handler) {
        /* 클라이언트가 서버에 연결 됬는지 확인 */
        if (!this._isConnected()) {  
          this._addToSubscribeQueue(channel, handler)
          return
        }
        /* 연결되지 않을 경우, 서버와 연결이 성립 되거나 복구된 후에 
           처리되도록 큐에 액션에 'subscribe' 추가한다  */
        const message = {
          action: 'subscribe',
          channel
        }
        /* 구독 메시지를 서버로 전송하기위해 내부 _send() 호출 
        *  channel 메시지를 핸들러를 내부 이벤트 버스에 바인드한다.  
        *  서버로 부터 메시지를 받으면 클라이언트는 내부 이벤트 버스의 
        *  $emit() 메소드를 호출 채널의 모든 핸들러에게 알려 줄 수 있다.
        * */
        this._send(message)
        this.$bus.$on(this._channelEvent(channel), handler)
        console.log('[SocketClient] 채널 구독 ' + channel)
    }   
    /*  */ 
    unsubscribe (channel, handler) {
        /* 이미 로그아웃 했을 경우엔 구독을 해지할 필요가 없음*/
        if (this.loggedOut) {
          return
        }
        /* 로그아웃 하지 않았을 경우엔 서버와 연결 확인*/
        if (!this._isConnected()) {
          this._addToUnsubscribeQueue(channel, handler)
          return
        }
        /* 서버와 연결되지 않은 경우 나중에 처리하기위해
        *  큐에 액션에 'unsubscribe' 추가한다  */
        const message = {
          action: 'unsubscribe',
          channel
        }
        /* 구독 해제 메시지를  서버로 전송하기위해 내부 _send() 호출 
        *  이벤트 버스에서 핸들러를 제거한다.
        * */
        this._send(message)
        this.$bus.$off(this._channelEvent(channel), handler)
        console.log('[SocketClient] 채널 구독 취소 ' + channel)
    }
    /** 여기부터 메소드 */
    _isConnected () {
        return this.socket && this.socket.readyState === SockJS.OPEN
    }    
    _onConnected () {
        globalBus.$emit('SocketClient.연결됨')
        console.log('[SocketClient] 연결됨')
        // 구독 및 구독 취소 대기열 처리
        this._processQueues()
     }
    _onMessageReceived (event) {
        const message = JSON.parse(event.data)
        console.log('[SocketClient] 메시지를 받음', message)
    
        if (message.channel) {
          this.$bus.$emit(this._channelEvent(message.channel), message.payload)
        }
    }
    _send (message) {
        this.socket.send(JSON.stringify(message))
    }
    _onSocketError (error) {
        console.error('[SocketClient] 소켓 에러', error)
    }
    _onClosed (event) {
        console.log('[SocketClient] 닫기 이벤트 수신', event)
        if (this.loggedOut) {
          // 수동 로그 아웃, 다시 연결할 필요 없음
           console.log('[SocketClient] 로그아웃')
           globalBus.$emit('SocketClient.로그아웃')
        } else {
          // 일시적으로 연결 해제 됨, 다시 연결 시도
           console.log('[SocketClient] 연결 해제')
           globalBus.$emit('SocketClient.연결 해제')
    
          setTimeout(() => {
            console.log('[SocketClient] 다시 연결중')
            globalBus.$emit('SocketClient. 다시 연결중')
            this.connect()
          }, 1000)
        }
    }    
    _channelEvent (channel) {
        return 'channel:' + channel
    }
    _processQueues () {
        console.log('[SocketClient] 구독 / 구독 취소 대기열 처리')
    
        //구독 대기열 처리
        const subscribeChannels = Object.keys(this.subscribeQueue)
        subscribeChannels.forEach(channel => {
          const handlers = this.subscribeQueue[channel]
          handlers.forEach(handler => {
            this.subscribe(channel, handler)
            this._removeFromQueue(this.subscribeQueue, channel, handler)
          })
        })
    
        // 구독 취소 대기열 처리
        const unsubscribeChannels = Object.keys(this.unsubscribeQueue)
        unsubscribeChannels.forEach(channel => {
          const handlers = this.unsubscribeQueue[channel]
          handlers.forEach(handler => {
            this.unsubscribe(channel, handler)
            this._removeFromQueue(this.unsubscribeQueue, channel, handler)
          })
        })
    }
    _addToSubscribeQueue (channel, handler) {
        console.log('[SocketClient] queue에 채널 구독을 추가합니다. 채널: ' + channel)
        // 구독 취소가 서버로 전송되지 않도록하려면
        this._removeFromQueue(this.unsubscribeQueue, channel, handler)
        const handlers = this.subscribeQueue[channel]
        if (!handlers) {
          this.subscribeQueue[channel] = [handler]
        } else {
          handlers.push(handler)
        }
    }    
    _addToUnsubscribeQueue (channel, handler) {
        console.log('[SocketClient] queue에 채널 구독 취소를 추가하는 중입니다. 채널: ' + channel)
        // 구독이 서버로 전송되지 않도록하려면
        this._removeFromQueue(this.subscribeQueue, channel, handler)
        const handlers = this.unsubscribeQueue[channel]
        if (!handlers) {
          this.unsubscribeQueue[channel] = [handler]
        } else {
          handlers.push(handlers)
        }
      }
    _removeFromQueue (queue, channel, handler) {
        const handlers = queue[channel]
        if (handlers) {
          let index = handlers.indexOf(handler)
          if (index > -1) {
            handlers.splice(index, 1)
          }
        }
    }  
}

export default new SocketClient()
官网给出的指南

https://developer.github.com/apps/building-your-first-github-app/

step1:
    github上面注册，获取到client_id ，client_secret，此步骤最好设置redirect_uri（鉴权之后重定向的url）
    与scope（需要的用户数据范围）

step2:
    GET https://github.com/login/oauth/authorize
    鉴权。提交的参数：client_id，redirect_uri，scope和state(随机字符串)
    用户将重定向到redirect_uri示意用户授权，之后将返回之前的网页并携带临时参数code与state（提交的参数）
    状态不匹配（参数？？）请求将被取消

step3:
    POST https://github.com/login/oauth/access_token
    获取access_token（令牌）。提交的参数：client_id，client_secret，code，redirect_uri，state
    返回access_token。
    之后便可以访问github的api，如：
    GET https://api.github.com/user?access_token=...


相关的api以及参数
    https://developer.github.com/apps/building-github-apps/identifying-and-authorizing-users-for-github-apps/

    https://developer.github.com/v3/



拦截器相关:https://blog.csdn.net/qq_33463102/article/details/60869879


RxAndroid : https://www.jianshu.com/p/a7635e39c5ac

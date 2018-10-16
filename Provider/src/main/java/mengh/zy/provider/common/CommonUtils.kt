package mengh.zy.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import mengh.zy.base.utils.HawkUtils
import mengh.zy.base.utils.UserHawkUtils
import mengh.zy.provider.router.RouterPath.UserCenter.Companion.PATH_LOGIN

/*
    顶级函数，判断是否登录
 */
fun isLogin():Boolean{
    return UserHawkUtils.getUserToken().isNotEmpty()
}

/*
    如果已经登录，进行传入的方法处理
    如果没有登录，进入登录界面
 */
fun afterLogin(method:()->Unit){
    if (isLogin()){
        method()
    }else{
        ARouter.getInstance().build(PATH_LOGIN).navigation()
    }
}

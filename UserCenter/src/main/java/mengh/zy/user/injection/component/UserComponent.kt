package mengh.zy.user.injection.component

import dagger.Component
import mengh.zy.user.injection.module.UserModule
import mengh.zy.base.injection.PerComponentScope
import mengh.zy.base.injection.component.ActivityComponent
import mengh.zy.user.ui.activity.LoginActivity
import mengh.zy.user.ui.activity.RegisterActivity
import mengh.zy.user.ui.activity.UserInfoActivity
import mengh.zy.user.ui.fragment.CollectImgFragment
import mengh.zy.user.ui.fragment.UserFragment

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:user的联系类
 */
@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(UserModule::class)])
interface UserComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: UserInfoActivity)
    fun inject(fragment: UserFragment)
    fun inject(fragment: CollectImgFragment)
}
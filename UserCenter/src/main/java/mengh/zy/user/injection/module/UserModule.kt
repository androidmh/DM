package mengh.zy.user.injection.module

import dagger.Module
import dagger.Provides
import mengh.zy.user.service.UserService
import mengh.zy.user.service.impl.UserServiceImpl

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe: User的Module注解
 */
@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): UserService {
        return service
    }
}
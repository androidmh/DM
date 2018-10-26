package mengh.zy.dm.injection.module

import dagger.Module
import dagger.Provides
import mengh.zy.dm.service.IndexService
import mengh.zy.dm.service.impl.IndexServiceImpl

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe: User的Module注解
 */
@Module
class IndexModule {

    @Provides
    fun providesUserService(service: IndexServiceImpl): IndexService {
        return service
    }
}
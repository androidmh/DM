package mengh.zy.media.injection.module

import dagger.Module
import dagger.Provides
import mengh.zy.media.service.ImageService
import mengh.zy.media.service.impl.ImageServiceImpl

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe: Media的Module注解
 */
@Module
class MediaModule {
    @Provides
    fun providesMediaService(service: ImageServiceImpl): ImageService {
        return service
    }
}
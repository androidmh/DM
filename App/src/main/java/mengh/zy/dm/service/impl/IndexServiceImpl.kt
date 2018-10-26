package mengh.zy.dm.service.impl

import io.reactivex.Observable
import mengh.zy.base.ext.convert
import mengh.zy.dm.data.protocol.IndexBean
import mengh.zy.dm.service.IndexService
import mengh.zy.dm.data.repository.IndexRepository
import javax.inject.Inject

/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/8/10.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:请求服务类，调起请求并返回值的类
 */

class IndexServiceImpl @Inject constructor() : IndexService {
    @Inject
    lateinit var repository: IndexRepository

    override fun getIndex(): Observable<IndexBean> {
        return repository.getIndex().convert()
    }
}

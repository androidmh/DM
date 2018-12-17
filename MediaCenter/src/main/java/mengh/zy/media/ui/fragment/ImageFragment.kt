package mengh.zy.media.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.Toolbar
import com.afollestad.materialdialogs.customview.getCustomView
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.entity.impl.ImageMedia
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_image.*
import mengh.zy.base.common.BaseConstant.Companion.IMG_TAB
import mengh.zy.base.common.BaseConstant.Companion.TAB_KEY
import mengh.zy.base.common.ResultCode
import mengh.zy.base.ext.getCompressFile
import mengh.zy.base.ext.getToString
import mengh.zy.base.ext.loadUrl
import mengh.zy.base.ext.onClick
import mengh.zy.base.ui.fragment.BaseFragment
import mengh.zy.base.ui.fragment.BaseMvpFragment
import mengh.zy.base.utils.BoxingUtils
import mengh.zy.base.utils.DMUtils
import mengh.zy.base.utils.MaterialDialogUtils
import mengh.zy.media.R
import mengh.zy.media.injection.component.DaggerMediaComponent
import mengh.zy.media.injection.module.MediaModule
import mengh.zy.media.presenter.UploadImgPresenter
import mengh.zy.media.presenter.view.UploadImgView
import mengh.zy.media.ui.activity.SearchActivity
import mengh.zy.media.ui.adapter.ImageAdapter
import mengh.zy.provider.common.afterLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList


/**
 * @author by mengh
 * @email menghedianmo@163.com
 * @date on 2018/9/28$.
 * PS: Not easy to write code, please indicate.
 *
 *   Describe:
 */
class ImageFragment : BaseMvpFragment<UploadImgPresenter>(), UploadImgView {

    override val layoutId: Int
        get() = R.layout.fragment_image

    override fun initView(v: View) {
        setHasOptionsMenu(true)
    }

    override fun initData() {
        val toolbar = find<Toolbar>(R.id.dmToolbar)
        initToolbar(toolbar, "图片")
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_item -> {
                    startActivityAnimation(SearchActivity::class.java)
                }
                R.id.upload_item -> {
                    afterLogin {
                        BoxingUtils.selectSingleCutImg(this, ResultCode.REQUEST_CODE)
                    }
                }
            }
            true
        }

        val fragments = ArrayList<BaseFragment>()
        val list = ArrayList<String>()
        for (tab in IMG_TAB) {
            val bundle = Bundle()
            bundle.putString(TAB_KEY, tab)
            val imgListFragment = ImgListFragment()
            imgListFragment.arguments = bundle
            fragments.add(imgListFragment)
            list.add(tab)
        }
        val adapter = ImageAdapter(childFragmentManager, fragments, list)
        imageVp.adapter = adapter
        imageVp.offscreenPageLimit = adapter.count
        imageTab.setupWithViewPager(imageVp)
    }

    private fun showUpDialog(imageMedia: ImageMedia) {
        val uploadDialog = MaterialDialogUtils.getCustomDialogs(mActivity, "上传", R.layout.dialog_upload)
        val customView = uploadDialog.getCustomView()
        val chooseBtn = customView?.find<Button>(R.id.chooseBtn)
        val uploadBtn = customView?.find<Button>(R.id.uploadBtn)
        val sortSp = customView?.find<AppCompatSpinner>(R.id.sortSp)
        val desEt = customView?.find<TextInputEditText>(R.id.desEt)
        val upProgressBar = customView?.find<ProgressBar>(R.id.upProgressBar)
        val userChooseImg = customView!!.find<ImageView>(R.id.userChooseImg)

        val file = imageMedia.getCompressFile(mActivity)
        val imageBody = RequestBody.create(null, file)
        val createFormData = MultipartBody.Part.createFormData("img", file.name, imageBody)
        userChooseImg.loadUrl(imageMedia.path,false)

        val adapter = ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, IMG_TAB)
        sortSp?.adapter = adapter
        chooseBtn?.onClick {
            BoxingUtils.selectSingleCutImg(this, ResultCode.REQUEST_CODE)
        }

        uploadBtn?.onClick {
            val des = desEt?.getToString()
            when {
                createFormData == null -> toast("请选择图片")
                des.isNullOrEmpty() -> toast("请输入描述")
                else -> {
                    mPresenter.uploadImage(createFormData, mutableMapOf(
                            Pair("sort", DMUtils.stringToRequestBody(sortSp!!.selectedItem.toString())),
                            Pair("describe", DMUtils.stringToRequestBody(des)),
                            Pair("label", DMUtils.stringToRequestBody("1,2"))),
                            upProgressBar
                    )
                }
            }
        }
        uploadDialog.show()
    }

    override fun injectComponent() {
        DaggerMediaComponent.builder().activityComponent(activityComponent).mediaModule(MediaModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUploadResult(result: String) {
        toast(result)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val medias = Boxing.getResult(data)
        if (resultCode == Activity.RESULT_OK && requestCode == ResultCode.REQUEST_CODE) {
            val imageMedia = medias?.get(0) as ImageMedia
            showUpDialog(imageMedia)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_image, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun widgetClick(v: View) {}
}
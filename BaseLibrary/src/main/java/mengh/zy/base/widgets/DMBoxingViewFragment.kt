package mengh.zy.base.widgets

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bilibili.boxing.AbsBoxingViewFragment
import com.bilibili.boxing.Boxing
import com.bilibili.boxing.model.BoxingManager
import com.bilibili.boxing.model.config.BoxingConfig
import com.bilibili.boxing.model.entity.AlbumEntity
import com.bilibili.boxing.model.entity.BaseMedia
import com.bilibili.boxing.model.entity.impl.ImageMedia
import com.bilibili.boxing_impl.WindowManagerHelper
import com.bilibili.boxing_impl.adapter.BoxingAlbumAdapter
import com.bilibili.boxing_impl.adapter.BoxingMediaAdapter
import com.bilibili.boxing_impl.ui.BoxingViewActivity
import com.bilibili.boxing_impl.view.HackyGridLayoutManager
import com.bilibili.boxing_impl.view.MediaItemLayout
import com.bilibili.boxing_impl.view.SpacesItemDecoration

import java.util.ArrayList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import mengh.zy.base.common.BaseConstant.Companion.DCIM_PHOTO_DIR
import mengh.zy.base.ext.setVisible

class DMBoxingViewFragment : AbsBoxingViewFragment(), View.OnClickListener {

    private var mIsPreview: Boolean = false
    private var mIsCamera: Boolean = false

    private var mPreBtn: Button? = null
    private var mOkBtn: Button? = null
    private var mRecycleView: RecyclerView? = null
    var mediaAdapter: BoxingMediaAdapter? = null
        private set
    private var mAlbumWindowAdapter: BoxingAlbumAdapter? = null
    private var mDialog: ProgressBar ? = null
    private var mEmptyTxt: TextView? = null
    private var mTitleTxt: TextView? = null
    private var mAlbumPopWindow: PopupWindow? = null
    private var mLoadingView: ProgressBar? = null

    private var mMaxCount: Int = 0

    override fun onCreateWithSelectedMedias(savedInstanceState: Bundle?, selectedMedias: List<BaseMedia>?) {
        mAlbumWindowAdapter = BoxingAlbumAdapter(context)
        mediaAdapter = BoxingMediaAdapter(context)
        mediaAdapter!!.selectedMedias = selectedMedias
        mMaxCount = maxCount
    }

    override fun startLoading() {
        loadMedias()
        loadAlbum()
    }

    override fun onRequestPermissionError(permissions: Array<String>?, e: Exception?) {
        if (permissions!!.isNotEmpty()) {
            if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                Toast.makeText(context, com.bilibili.boxing_impl.R.string.boxing_storage_permission_deny, Toast.LENGTH_SHORT).show()
                showEmptyData()
            } else if (permissions[0] == Manifest.permission.CAMERA) {
                Toast.makeText(context, com.bilibili.boxing_impl.R.string.boxing_camera_permission_deny, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionSuc(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (permissions[0] == AbsBoxingViewFragment.STORAGE_PERMISSIONS[0]) {
            startLoading()
        } else if (permissions[0] == AbsBoxingViewFragment.CAMERA_PERMISSIONS[0]) {
            startCamera(activity, this, null)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.bilibili.boxing_impl.R.layout.fragmant_boxing_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews(view: View) {
        mEmptyTxt = view.findViewById<View>(com.bilibili.boxing_impl.R.id.empty_txt) as TextView
        mRecycleView = view.findViewById<View>(com.bilibili.boxing_impl.R.id.media_recycleview) as RecyclerView
        mRecycleView!!.setHasFixedSize(true)
        mLoadingView = view.findViewById<View>(com.bilibili.boxing_impl.R.id.loading) as ProgressBar
        initRecycleView()

        val isMultiImageMode = BoxingManager.getInstance().boxingConfig.isMultiImageMode
        val multiImageLayout = view.findViewById<View>(com.bilibili.boxing_impl.R.id.multi_picker_layout)
        multiImageLayout.visibility = if (isMultiImageMode) View.VISIBLE else View.GONE
        if (isMultiImageMode) {
            mPreBtn = view.findViewById<View>(com.bilibili.boxing_impl.R.id.choose_preview_btn) as Button
            mOkBtn = view.findViewById<View>(com.bilibili.boxing_impl.R.id.choose_ok_btn) as Button

            mPreBtn!!.setOnClickListener(this)
            mOkBtn!!.setOnClickListener(this)
            updateMultiPickerLayoutState(mediaAdapter!!.selectedMedias)
        }
    }

    private fun initRecycleView() {
        val gridLayoutManager = HackyGridLayoutManager(activity, GRID_COUNT)
        gridLayoutManager.isSmoothScrollbarEnabled = true
        mRecycleView!!.layoutManager = gridLayoutManager
        mRecycleView!!.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelOffset(com.bilibili.boxing_impl.R.dimen.boxing_media_margin), GRID_COUNT))
        mediaAdapter!!.setOnCameraClickListener(OnCameraClickListener())
        mediaAdapter!!.setOnCheckedListener(OnMediaCheckedListener())
        mediaAdapter!!.setOnMediaClickListener(OnMediaClickListener())
        mRecycleView!!.adapter = mediaAdapter
        mRecycleView!!.addOnScrollListener(ScrollListener())
    }

    override fun showMedia(medias: List<BaseMedia>?, allCount: Int) {
        if (medias == null || isEmptyData(medias) && isEmptyData(mediaAdapter!!.allMedias)) {
            showEmptyData()
            return
        }
        showData()
        mediaAdapter!!.addAllData(medias)
        checkSelectedMedia(medias, mediaAdapter!!.selectedMedias)
    }

    private fun isEmptyData(medias: List<BaseMedia>): Boolean {
        return medias.isEmpty() && !BoxingManager.getInstance().boxingConfig.isNeedCamera
    }

    private fun showEmptyData() {
        mLoadingView!!.visibility = View.GONE
        mEmptyTxt!!.visibility = View.VISIBLE
        mRecycleView!!.visibility = View.GONE
    }

    private fun showData() {
        mLoadingView!!.visibility = View.GONE
        mEmptyTxt!!.visibility = View.GONE
        mRecycleView!!.visibility = View.VISIBLE
    }

    override fun showAlbum(albums: List<AlbumEntity>?) {
        if ((albums == null || albums.isEmpty()) && mTitleTxt != null) {
            mTitleTxt!!.setCompoundDrawables(null, null, null, null)
            mTitleTxt!!.setOnClickListener(null)
            return
        }
        mAlbumWindowAdapter!!.addAllData(albums)
    }

    override fun clearMedia() {
        mediaAdapter!!.clearData()
    }

    private fun updateMultiPickerLayoutState(medias: List<BaseMedia>) {
        updateOkBtnState(medias)
        updatePreviewBtnState(medias)
    }

    private fun updatePreviewBtnState(medias: List<BaseMedia>?) {
        if (mPreBtn == null || medias == null) {
            return
        }
        val enabled = medias.size in 1..mMaxCount
        mPreBtn!!.isEnabled = enabled
    }

    private fun updateOkBtnState(medias: List<BaseMedia>?) {
        if (mOkBtn == null || medias == null) {
            return
        }
        val enabled = medias.size in 1..mMaxCount
        mOkBtn!!.isEnabled = enabled
        mOkBtn!!.text = if (enabled)
            getString(com.bilibili.boxing_impl.R.string.boxing_image_select_ok_fmt, medias.size.toString(), mMaxCount.toString())
        else
            getString(com.bilibili.boxing_impl.R.string.boxing_ok)
    }

    override fun onCameraFinish(media: BaseMedia?) {
        dismissProgressDialog()
        mIsCamera = false
        if (media == null) {
            return
        }
        if (hasCropBehavior()) {
            startCrop(media, IMAGE_CROP_REQUEST_CODE)
        } else if (mediaAdapter != null && mediaAdapter!!.selectedMedias != null) {
            val selectedMedias = mediaAdapter!!.selectedMedias
            selectedMedias.add(media)
            onFinish(selectedMedias)
        }
    }

    override fun onCameraError() {
        mIsCamera = false
        dismissProgressDialog()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == com.bilibili.boxing_impl.R.id.choose_ok_btn) {
            onFinish(mediaAdapter!!.selectedMedias)
        } else if (id == com.bilibili.boxing_impl.R.id.choose_preview_btn) {
            if (!mIsPreview) {
                mIsPreview = true
                val medias = mediaAdapter!!.selectedMedias as ArrayList<BaseMedia>
                Boxing.get().withIntent(activity, BoxingViewActivity::class.java, medias)
                        .start(this, DMBoxingViewFragment.IMAGE_PREVIEW_REQUEST_CODE, BoxingConfig.ViewMode.PRE_EDIT)

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PREVIEW_REQUEST_CODE) {
            mIsPreview = false
            val isBackClick = data.getBooleanExtra(BoxingViewActivity.EXTRA_TYPE_BACK, false)
            val selectedMedias = data.getParcelableArrayListExtra<BaseMedia>(Boxing.EXTRA_SELECTED_MEDIA)
            onViewActivityRequest(selectedMedias, mediaAdapter!!.allMedias, isBackClick)
            if (isBackClick) {
                mediaAdapter!!.selectedMedias = selectedMedias
            }
            updateMultiPickerLayoutState(selectedMedias)
        }

    }

    private fun onViewActivityRequest(selectedMedias: List<BaseMedia>, allMedias: List<BaseMedia>, isBackClick: Boolean) {
        if (isBackClick) {
            checkSelectedMedia(allMedias, selectedMedias)
        } else {
            onFinish(selectedMedias)
        }
    }


    override fun onCameraActivityResult(requestCode: Int, resultCode: Int) {
        showProgressDialog()
        super.onCameraActivityResult(requestCode, resultCode)
    }

    private fun showProgressDialog() {
        if (mDialog == null) {
            mDialog = ProgressBar(activity)
            mDialog!!.isIndeterminate = true
//            mDialog!!.setMessage(getString(com.bilibili.boxing_impl.R.string.boxing_handling))
        }
        if (mDialog!!.visibility==View.GONE) {
            mDialog!!.setVisible(true)
        }
    }

    private fun dismissProgressDialog() {
        if (mDialog != null && mDialog!!.visibility==View.VISIBLE) {
            mDialog!!.visibility=View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val medias = mediaAdapter!!.selectedMedias as ArrayList<BaseMedia>
        onSaveMedias(outState, medias)
    }

    fun setTitleTxt(titleTxt: TextView) {
        mTitleTxt = titleTxt
        mTitleTxt!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                if (mAlbumPopWindow == null) {
                    val height = WindowManagerHelper.getScreenHeight(v.context) - (WindowManagerHelper.getToolbarHeight(v.context) + WindowManagerHelper.getStatusBarHeight(v.context))
                    val windowView = createWindowView()
                    mAlbumPopWindow = PopupWindow(windowView, ViewGroup.LayoutParams.MATCH_PARENT,
                            height, true)
                    mAlbumPopWindow!!.animationStyle = com.bilibili.boxing_impl.R.style.Boxing_PopupAnimation
                    mAlbumPopWindow!!.isOutsideTouchable = true
                    mAlbumPopWindow!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(v.context, com.bilibili.boxing_impl.R.color.boxing_colorPrimaryAlpha)))
                    mAlbumPopWindow!!.contentView = windowView
                }
                mAlbumPopWindow!!.showAsDropDown(v, 0, 0)
            }

            @SuppressLint("WrongConstant")
            private fun createWindowView(): View {
                val view = LayoutInflater.from(activity).inflate(com.bilibili.boxing_impl.R.layout.layout_boxing_album, null)
                val recyclerView = view.findViewById<RecyclerView>(com.bilibili.boxing_impl.R.id.album_recycleview)
                recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                recyclerView.addItemDecoration(SpacesItemDecoration(2, 1))

                val albumShadowLayout = view.findViewById<View>(com.bilibili.boxing_impl.R.id.album_shadow)
                albumShadowLayout.setOnClickListener { dismissAlbumWindow() }
                mAlbumWindowAdapter!!.setAlbumOnClickListener(OnAlbumItemOnClickListener())
                recyclerView.adapter = mAlbumWindowAdapter
                return view
            }
        })
    }

    private fun dismissAlbumWindow() {
        if (mAlbumPopWindow != null && mAlbumPopWindow!!.isShowing) {
            mAlbumPopWindow!!.dismiss()
        }
    }

    private inner class ScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val childCount = recyclerView.childCount
            if (childCount > 0) {
                val lastChild = recyclerView.getChildAt(childCount - 1)
                val outerAdapter = recyclerView.adapter
                val lastVisible = recyclerView.getChildAdapterPosition(lastChild)
                if (lastVisible == outerAdapter!!.itemCount - 1 && hasNextPage() && canLoadNextPage()) {
                    onLoadNextPage()
                }
            }
        }
    }

    private inner class OnMediaClickListener : View.OnClickListener {

        override fun onClick(v: View) {
            val media = v.tag as BaseMedia
            val pos = v.getTag(com.bilibili.boxing_impl.R.id.media_item_check) as Int
            val mode = BoxingManager.getInstance().boxingConfig.mode
            when (mode!!) {
                BoxingConfig.Mode.SINGLE_IMG -> singleImageClick(media)
                BoxingConfig.Mode.MULTI_IMG -> multiImageClick(pos)
                BoxingConfig.Mode.VIDEO -> videoClick(media)
            }
        }

        private fun videoClick(media: BaseMedia) {
            val iMedias = ArrayList<BaseMedia>()
            iMedias.add(media)
            onFinish(iMedias)
        }

        private fun multiImageClick(pos: Int) {
            if (!mIsPreview) {
                val albumMedia = mAlbumWindowAdapter!!.currentAlbum
                val albumId = if (albumMedia != null) albumMedia.mBucketId else AlbumEntity.DEFAULT_NAME
                mIsPreview = true

                val medias = mediaAdapter!!.selectedMedias as ArrayList<BaseMedia>

                Boxing.get().withIntent(context, BoxingViewActivity::class.java, medias, pos, albumId)
                        .start(this@DMBoxingViewFragment, DMBoxingViewFragment.IMAGE_PREVIEW_REQUEST_CODE, BoxingConfig.ViewMode.EDIT)

            }
        }

        private fun singleImageClick(media: BaseMedia) {
            val iMedias = ArrayList<BaseMedia>()
            iMedias.add(media)
            if (hasCropBehavior()) {
                startCrop(media, IMAGE_CROP_REQUEST_CODE)
            } else {
                onFinish(iMedias)
            }
        }
    }


    private inner class OnCameraClickListener : View.OnClickListener {

        override fun onClick(v: View) {
            if (!mIsCamera) {
                mIsCamera = true
                startCamera(activity, this@DMBoxingViewFragment, DCIM_PHOTO_DIR)
            }
        }
    }

    private inner class OnMediaCheckedListener : BoxingMediaAdapter.OnMediaCheckedListener {

        override fun onChecked(view: View, iMedia: BaseMedia) {
            if (iMedia !is ImageMedia) {
                return
            }
            val isSelected = !iMedia.isSelected
            val layout = view as MediaItemLayout
            val selectedMedias = mediaAdapter!!.selectedMedias
            if (isSelected) {
                if (selectedMedias.size >= mMaxCount) {
                    val warning = getString(com.bilibili.boxing_impl.R.string.boxing_too_many_picture_fmt, mMaxCount)
                    Toast.makeText(activity, warning, Toast.LENGTH_SHORT).show()
                    return
                }
                if (!selectedMedias.contains(iMedia)) {
                    if (iMedia.isGifOverSize) {
                        Toast.makeText(activity, com.bilibili.boxing_impl.R.string.boxing_gif_too_big, Toast.LENGTH_SHORT).show()
                        return
                    }
                    selectedMedias.add(iMedia)
                }
            } else {
                if (selectedMedias.size >= 1 && selectedMedias.contains(iMedia)) {
                    selectedMedias.remove(iMedia)
                }
            }
            iMedia.isSelected = isSelected
            layout.setChecked(isSelected)
            updateMultiPickerLayoutState(selectedMedias)
        }
    }

    private inner class OnAlbumItemOnClickListener : BoxingAlbumAdapter.OnAlbumClickListener {

        override fun onClick(view: View, pos: Int) {
            val adapter = mAlbumWindowAdapter
            if (adapter != null && adapter.currentAlbumPos != pos) {
                val albums = adapter.alums
                adapter.currentAlbumPos = pos

                val albumMedia = albums[pos]
                loadMedias(0, albumMedia.mBucketId)
                mTitleTxt!!.text = if (albumMedia.mBucketName == null) getString(com.bilibili.boxing_impl.R.string.boxing_default_album_name) else albumMedia.mBucketName

                for (album in albums) {
                    album.mIsSelected = false
                }
                albumMedia.mIsSelected = true
                adapter.notifyDataSetChanged()
            }
            dismissAlbumWindow()
        }
    }

    companion object {
        const val TAG = "mengh.zy.base.widgets.DMBoxingViewFragment"
        private const val IMAGE_PREVIEW_REQUEST_CODE = 9086
        private const val IMAGE_CROP_REQUEST_CODE = 9087

        private const val GRID_COUNT = 3

        fun newInstance(): DMBoxingViewFragment {
            return DMBoxingViewFragment()
        }
    }

}

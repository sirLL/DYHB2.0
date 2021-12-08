package cn.dianyinhuoban.app2.mvp.data_analysis.view

import android.os.Bundle
import cn.dianyinhuoban.app2.R
import com.wareroom.lib_base.mvp.IPresenter
import com.wareroom.lib_base.ui.BaseActivity

class DataAnalysisActivity : BaseActivity<IPresenter?>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_analysis)
        setTitle("数据分析")
    }

    override fun getPresenter(): IPresenter? {
        return null
    }
}
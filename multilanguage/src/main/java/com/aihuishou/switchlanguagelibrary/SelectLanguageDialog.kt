package com.aihuishou.switchlanguagelibrary

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.from
import android.view.View
import com.aihuishou.switchlanguagelibrary.utils.LanguageSwitchUtils


/**
 *@Author Li Gui Yun
 *@date 2018年08月28日14:25
 *@email guiyun.li@aihuishou.com
 *@ClassName:
 **/
class SelectLanguageDialog(context: Context, mListData: List<LanguageBean>, switchLanguageListener: SwitchLanguageListener) {

    private val mContext: Context = context//上下文

    private val switchLanguageListener = switchLanguageListener

    private lateinit var inflateView: View//填充AlertDialog 中的View

    private lateinit var dialog: AlertDialog//创建对话框

    private lateinit var rvLanguage: RecyclerView//语言列表

    private lateinit var languageAdapter: SelectLanguageAdapter

    private val mListData = mListData


    init {
        initView()
        iniData()
    }

    /**
     * 获取View
     */
    private fun initView() {
        inflateView = from(mContext).inflate(R.layout.dialog_select_language_layout, null, false)
        dialog = AlertDialog.Builder(mContext).setView(inflateView).setPositiveButton(R.string.confirm) { _, _ ->
            val languageBean = languageAdapter.getSelectLanguageBean()
            if (!LanguageSwitchUtils.isSameWithSetting(mContext, languageBean.local)) {
                switchLanguageListener.onSwitchLanguageSuccess(languageBean.local)
            }
        }.setNegativeButton(R.string.cancel) { _, _ ->
        }.create()
        rvLanguage = inflateView.findViewById(R.id.rvLanguage)
        rvLanguage.layoutManager = LinearLayoutManager(mContext)

    }

    /**
     * 填充数据
     */
    private fun iniData() {
        languageAdapter = SelectLanguageAdapter(mContext, mListData)
        rvLanguage.adapter = languageAdapter
        languageAdapter.notifyPosition()
    }


    fun show() {
        dialog.show()
    }

}
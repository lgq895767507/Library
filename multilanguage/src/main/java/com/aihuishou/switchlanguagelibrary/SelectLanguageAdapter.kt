package com.aihuishou.switchlanguagelibrary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.aihuishou.switchlanguagelibrary.utils.LanguageSwitchUtils
import java.util.*

/**
 *@Author Li Gui Yun
 *@date 2018年08月28日14:40
 *@email guiyun.li@aihuishou.com
 *@ClassName:
 **/
class SelectLanguageAdapter(context: Context, mListData: List<LanguageBean>) : RecyclerView.Adapter<SelectLanguageAdapter.SelectLanguageHolder>() {
    private val mContext = context

    private val mListData = mListData

    private var selectPosition = 0//选择单选框位置

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SelectLanguageHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_select_language, p0, false)
        return SelectLanguageHolder(view)
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    override fun onBindViewHolder(viewHolder: SelectLanguageHolder, position: Int) {
        viewHolder.tvLanguage.text = mListData[position].languageName
        viewHolder.rbSelect.isChecked = selectPosition == position
        viewHolder.rbSelect.setOnClickListener {
            selectPosition = position
            notifyDataSetChanged()
        }
    }

    fun getSelectLanguageBean(): LanguageBean {
        return mListData[selectPosition]
    }

    fun notifyPosition() {
        val locale = LanguageSwitchUtils.getAppLocale(mContext) ?: return
        mListData.forEachIndexed { index, languageBean ->
            if (languageBean.local == locale) {
                selectPosition = index
                notifyDataSetChanged()
                return@forEachIndexed
            }
        }
    }

    inner class SelectLanguageHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvLanguage: TextView = view.findViewById(R.id.tvLanguage)

        var rbSelect: RadioButton = view.findViewById(R.id.rbSelect)
    }
}
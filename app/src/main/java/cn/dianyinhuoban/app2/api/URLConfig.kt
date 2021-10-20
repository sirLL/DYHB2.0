package cn.dianyinhuoban.app2.api

import com.wareroom.lib_base.api.BaseURLConfig

interface URLConfig {
    companion object {
        fun appendHostURL(url: String?): String {
            if (url.isNullOrEmpty()) return ""
            return if (!url.startsWith("http")) {
                if (url.startsWith("/")) {
                    BaseURLConfig.BASE_URL + url.replaceFirst("/", "")
                } else {
                    BaseURLConfig.BASE_URL + url
                }
            } else {
                url
            }
        }
    }
}
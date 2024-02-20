package com.berdimyradov.news.data.source.remote.response

import com.berdimyradov.news.data.source.remote.dto.SourceDto
import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("sources") val sources: List<SourceDto>? = null
)

package com.example.madlevel6task2.model

import com.example.madlevel6task2.rest.MovieDBConfig

class ImageUrlBuilder {
    companion object {
        fun buildImageUrl(filePath: String): String {
            return MovieDBConfig.IMAGE_BASE_URL + filePath
        }
    }
}
package com.priyank.circulate.main.model

sealed class UploadStatus() {
    class UpLoading() : UploadStatus()
    class Success() : UploadStatus()
    class Error() : UploadStatus()
}

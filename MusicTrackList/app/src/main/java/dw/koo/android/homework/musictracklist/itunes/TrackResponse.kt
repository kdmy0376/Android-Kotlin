package dw.koo.android.homework.musictracklist.itunes

import okhttp3.Headers

class TrackResponse {
    private var error: Int
    private var code: Int
    private var headers: Headers?
    var body: String? = null
    fun responseOk(): Boolean {
        return error == 0 && code >= 200 && code < 300
    }

    fun setError(error: Int): TrackResponse {
        this.error = error
        return this
    }

    fun setCode(code: Int): TrackResponse {
        this.code = code
        return this
    }

    fun setHeaders(headers: Headers?): TrackResponse {
        this.headers = headers
        return this
    }

    override fun toString(): String {
        return "TrackResponse{" +
                "error=" + error +
                ", code=" + code +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}'
    }

    init {
        error = -99
        code = -1
        headers = null
        body = ""
    }
}
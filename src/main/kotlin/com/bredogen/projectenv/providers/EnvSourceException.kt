package com.bredogen.projectenv.providers

class EnvSourceException : Exception {
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}

package com.example.usecase.common

import java.lang.Exception

class CurrentStickyNoteNotFoundException(
    message: String,
) : Exception(message)

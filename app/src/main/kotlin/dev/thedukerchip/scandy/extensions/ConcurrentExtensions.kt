package dev.thedukerchip.scandy.extensions

import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor

fun <T> ListenableFuture<T>.addListener(executor: Executor, listener: Runnable) {
    addListener(listener, executor)
}

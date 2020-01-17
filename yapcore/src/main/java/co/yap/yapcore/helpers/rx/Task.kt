package co.yap.yapcore.helpers.rx

import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class Task(// action to be run
    private val action: Action
) {
    // action run after operation completed
    private var onComplete: Action? = null
    // true if should observe on main thread
    private var observeMainThread = false
    // Scheduler on which action run, default is a new thread (background)
    private var scheduler = Schedulers.newThread()

    // action will be observed on main thread
    fun observeMainThread(): Task {
        observeMainThread = true
        return this
    }

    fun observeMainThread(observeMainThread: Boolean): Task {
        this.observeMainThread = observeMainThread
        return this
    }

    /**
     * @param onComplete action on complete
     */
    fun onComplete(onComplete: Action): Task {
        this.onComplete = onComplete
        return this
    }

    /**
     * @param scheduler which action will be run on
     */
    fun onScheduler(scheduler: Scheduler): Task {
        this.scheduler = scheduler
        return this
    }

    /**
     * Convert a task to a [Completable]
     * @param safe true if should ignored all exception
     * @return completable
     */
    fun toCompletable(safe: Boolean): Completable {
        var completable =
            Completable.create { e: CompletableEmitter ->
                if (safe) {
                    try {
                        action.run()
                    } catch (ignored: Exception) {
                        ignored.printStackTrace()
                    }
                } else {
                    action.run()
                }
                e.onComplete()
            }
        if (observeMainThread) {
            completable = completable.observeOn(AndroidSchedulers.mainThread())
        }
        return completable.subscribeOn(scheduler)
    }

    /**
     * Run the action
     */
    fun run() {
        run(false)
    }

    /**
     * Run the action safely (all [Exception] will be ignored)
     */
    fun runSafely() {
        run(true)
    }

    /**
     * Run the action
     * @param safe true if should ignore exceptions
     */
    private fun run(safe: Boolean) {
        val completable = toCompletable(safe)
        if (onComplete != null) {
            completable.subscribe(onComplete)
        } else {
            completable.subscribe()
        }
    }

    companion object {
        /**
         * Do a task on a new thread (background thread)
         * DON'T create too much task by this function, this might cause thread count exceed Exception
         * @param action action to be run
         */
        fun run(action: Action) {
            Task(action).run()
        }

        /**
         * Do a task on a new thread (background thread)
         * DON'T create too much task by this function, this might cause thread count exceed Exception
         * @param action action to be run (upstream)
         * @param onComplete complete action (downstream)
         * @param observeMainThread true if should observe on main thread
         */
        fun run(
            action: Action,
            onComplete: Action,
            observeMainThread: Boolean
        ) {
            Task(action).onComplete(onComplete)
                .observeMainThread(observeMainThread).run()
        }

        /**
         * Do a task safely on a new thread (background thread) (All exception will be ignored)
         * DON'T create too much task by this function, this might cause thread count exceed Exception
         * @param action action to be run
         */
        fun runSafely(action: Action) {
            Task(action).runSafely()
        }

        /**
         * Do a task safely on a new thread (background thread) (All exception will be ignored)
         * DON'T create too much task by this function, this might cause thread count exceed Exception
         * @param action action to be run (upstream)
         * @param onComplete complete action (downstream)
         * @param observeMainThread true if should observe on main thread
         */
        fun runSafely(
            action: Action,
            onComplete: Action,
            observeMainThread: Boolean
        ) {
            Task(action).onComplete(onComplete)
                .observeMainThread(observeMainThread).runSafely()
        }
    }

}
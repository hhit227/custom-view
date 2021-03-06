/*
 * Copyright (C) 2018 CoorChice <icechen_@outlook.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * <p>
 * Last modified 16-11-24 上午11:38
 */

package com.chcit.custom.view.text.one.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @hide
 */
public class ThreadPool {

  private ExecutorService threadPool;

  private ThreadPool() {
    if (threadPool == null) {
      threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }
  }

  private static final class Holder {
    private static final ThreadPool instance = new ThreadPool();
  }

  public static final ThreadPool get() {
    return Holder.instance;
  }

  public static void run(Runnable runnable) {
    ThreadPool.get().threadPool.execute(runnable);
  }

  public static <T> Future<T> submit(Callable<T> call) {
    return ThreadPool.get().threadPool.submit(call);
  }

  /**
   * 关闭线程池,这将导致改线程池立即停止接受新的线程请求,但已经存在的任务仍然会执行,直到完成。
   */
  public synchronized void shutDown() {
    ThreadPool.get().threadPool.shutdownNow();
  }

}

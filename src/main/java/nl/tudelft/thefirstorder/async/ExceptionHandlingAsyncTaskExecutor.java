package nl.tudelft.thefirstorder.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Handles the exceptons.
 */
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor,
    InitializingBean, DisposableBean {

    private final Logger log = LoggerFactory.getLogger(ExceptionHandlingAsyncTaskExecutor.class);

    private final AsyncTaskExecutor executor;

    public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
        this.executor = executor;
    }

    /**
     * Execute a specific task.
     * @param task the task which has to be executed
     */
    @Override
    public void execute(Runnable task) {
        executor.execute(createWrappedRunnable(task));
    }

    /**
     * Execute a specific task with a specified time out.
     * @param task the task which has to be executed
     * @param startTimeout the specified time out
     */
    @Override
    public void execute(Runnable task, long startTimeout) {
        executor.execute(createWrappedRunnable(task), startTimeout);
    }

    /**
     * Call a specific task.
     * @param task the task which has to be called
     * @param <T> the types from the task
     * @return the callable object of the task
     */
    private <T> Callable<T> createCallable(final Callable<T> task) {
        return () -> {
            try {
                return task.call();
            } catch (Exception e) {
                handle(e);
                throw e;
            }
        };
    }

    /**
     * Run a specified task
     * @param task the task
     * @return a runnable object of the task
     */
    private Runnable createWrappedRunnable(final Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                handle(e);
            }
        };
    }

    /**
     * Handle an exception.
     * @param exception the exception to handle
     */
    protected void handle(Exception exception) {
        log.error("Caught async exception", exception);
    }

    /**
     * Submit a runnable task.
     * @param task the task
     * @return a future object
     */
    @Override
    public Future<?> submit(Runnable task) {
        return executor.submit(createWrappedRunnable(task));
    }

    /**
     * Submit a cllable task.
     * @param task the task
     * @param <T> the types of the task
     * @return a future object with the types specified
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(createCallable(task));
    }

    /**
     * Destroy the handler.
     * @throws Exception if this goes wrong
     */
    @Override
    public void destroy() throws Exception {
        if (executor instanceof DisposableBean) {
            DisposableBean bean = (DisposableBean) executor;
            bean.destroy();
        }
    }

    /**
     * Set the handles after some properties.
     * @throws Exception if this goes wrong
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (executor instanceof InitializingBean) {
            InitializingBean bean = (InitializingBean) executor;
            bean.afterPropertiesSet();
        }
    }
}

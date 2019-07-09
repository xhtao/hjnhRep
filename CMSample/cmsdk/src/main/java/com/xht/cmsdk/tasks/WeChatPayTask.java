package com.xht.cmsdk.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.xht.cmsdk.tasks.taskrely.TaskParams;

/**
 * Created by XIE on 2018/8/13.
 * AsyncTask　<Params, Progress, Result>
 *     Params:      这个泛型指定的是我们传递给异步任务执行时的参数的类型
 *     Progress:    这个泛型指定的是我们的异步任务在执行的时候将执行的进度返回给UI线程的参数的类型
 *     Result:      这个泛型指定的异步任务执行完后返回给UI线程的结果的类型
 */

public class WeChatPayTask extends AsyncTask<Object, Integer, String> {
    private Context mContext = null;
    private TaskParams taskParams = null;


    public WeChatPayTask(Context context) {
        mContext = context;
    }

    /**
     * 在执行异步任务之前的时候执行，并且是在UI Thread当中执行的，通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出要给ProgressDialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 在onPreExecute()方法执行完之后，会马上执行这个方法，这个方法就是来处理异步任务的方法，
     * Android操作系统会在后台的线程池当中开启一个worker thread来执行我们的这个方法，所以这个方法是在worker thread当中执行的，
     * 这个方法执行完之后就可以将我们的执行结果发送给我们的最后一个 onPostExecute 方法，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作
     * @param objects
     * @return
     */
    @Override
    protected String doInBackground(Object... objects) {
        //获取微信支付订单
        taskParams = (TaskParams) objects[0];
        return TasksLogic.getInstance().getWeChatPayOrder(taskParams);
    }

    /**
     * 这个方法也是在UI Thread当中执行的，我们在异步任务执行的时候，
     * 有时候需要将执行的进度返回给我们的UI界面，例如下载一张网络图片，
     * 我们需要时刻显示其下载的进度，就可以使用这个方法来更新我们的进度。
     * 这个方法在调用之前，我们需要在 doInBackground 方法中调用一个 publishProgress(Progress) 的方法来将我们的进度时时刻刻传递给 onProgressUpdate 方法来更新
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * 当我们的异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI Thread当中调用的，我们可以将返回的结果显示在UI控件上
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        if (taskParams == null){
            return;
        }
        //返回处理

        taskParams.setPrepayID("");
        TasksLogic.getInstance().startWeChatPay(taskParams);
    }
}

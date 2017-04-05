package com.daimajia.numberprogressbar;

/**
 * Created by lelexxx on 15-4-23.
 */
public interface OnProgressBarListener {

    /**
     * 进度改变时的监听
     * @param current 当前进度值
     * @param max 最大值
     */
    void onProgressChange(int current, int max);
}

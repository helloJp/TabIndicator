package me.jp.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JiangPing on 2015/7/8.
 */
public class PageFragment extends Fragment {
    public int mPosition;

    public static Fragment newInstance(int position) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.mPosition = position;
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(inflater.getContext());
        textView.setText("Page " + this.mPosition);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

}

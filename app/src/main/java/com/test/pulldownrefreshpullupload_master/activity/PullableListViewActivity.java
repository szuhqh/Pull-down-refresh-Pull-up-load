package com.test.pulldownrefreshpullupload_master.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.test.pulldownrefreshpullupload_master.enums.LoadStateEnum;
import com.test.pulldownrefreshpullupload_master.http.HttpClient;
import com.test.pulldownrefreshpullupload_master.listener.PullableListener;
import com.test.pulldownrefreshpullupload_master.R;
import com.test.pulldownrefreshpullupload_master.adapter.MusicListAdapter;
import com.test.pulldownrefreshpullupload_master.pulltorefresh.PullableListView;
import com.test.pulldownrefreshpullupload_master.ui.PullToRefreshLayout;
import com.test.pulldownrefreshpullupload_master.utils.GenerateDataUtil;
import com.test.pulldownrefreshpullupload_master.utils.ViewUtils;

import java.util.List;

public class PullableListViewActivity extends Activity implements PullableListView.OnLoadListener{

    private ListView mList;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true; //第一次进入时需要自动刷新
    private LinearLayout llLoading;
    private LinearLayout llLoadFail;
    private int mOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.pull_down_refresh_pull_up_load_layout));
        ptrl.setOnRefreshListener(new PullableListener());
        mList = (ListView) findViewById(R.id.content_view);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llLoadFail = (LinearLayout) findViewById(R.id.ll_load_fail);
        initListView();
    }

    private void initListView() {
        List<String> items = GenerateDataUtil.generateListItems(120, "音乐编号");
        MusicListAdapter adapter = new MusicListAdapter(items, this);
        mList.setAdapter(adapter);
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        PullableListViewActivity.this,
                        "LongClick on "
                                + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullableListViewActivity.this,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
        ViewUtils.changeViewState(mList, llLoading, llLoadFail, LoadStateEnum.LOADING);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onLoad() {
        getMusic(mOffset);
    }

    private void getMusic(final int offset) {

    }
}

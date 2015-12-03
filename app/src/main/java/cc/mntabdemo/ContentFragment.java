package cc.mntabdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * User: 山野书生(1203596603@qq.com)
 * Date: 2015-11-26
 * Time: 16:26
 * Version 1.0
 */

public class ContentFragment extends StateFragment {

    private TextView text;
    private Button btn;
    private EditText edit;

    private String mTitle;

    private String tag = ContentFragment.class.getSimpleName();

    public ContentFragment(){
        super();
    }

    public ContentFragment(String title){
        super();
        mTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText(edit.getText().toString());
            }
        });
    }

    private void initViews(View view){
        text = (TextView)view.findViewById(R.id.text);
        btn = (Button)view.findViewById(R.id.btn);
        edit = (EditText)view.findViewById(R.id.edit);
    }

    @Override
    public void onRestoreState(Bundle saveInstanceState) {
        edit.setText(saveInstanceState.getString("edit"));
        text.setText(saveInstanceState.getString("text"));
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putString("edit", edit.getText().toString());
        outState.putString("text", text.getText().toString());
    }
}

package com.example.huchuan.wordsbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huchuan.wordsbook.model.Words;
import com.example.huchuan.wordsbook.util.WordsAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class ResultFragment extends Fragment {

    public ResultFragment() {
    }
    private LinearLayout cover;
    private Button addToBook;
    private Words words;
    private TextView word;
    private ImageButton BrEmp3;
    private TextView BrE;
    private ImageButton AmEmp3;
    private TextView AmE;
    private TextView defs;
    private TextView sams;
    private String BrEmp3Url;
    private String AmEmp3Url;
    private LinearLayout searchWords_fatherLayout;
    private Activity myActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.myActivity=(Activity) context;
        Log.i("activity", "activity: "+this.myActivity);
    }

    public Activity getMyActivity(){
        if(getActivity()==null){
            return this.myActivity;
        }else {
            return getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        cover=(LinearLayout)view.findViewById(R.id.cover);
        addToBook=(Button) view.findViewById(R.id.addToBook);
        word=(TextView) view.findViewById(R.id.word);
        BrEmp3=(ImageButton)view.findViewById(R.id.BrEmp3);
        BrE=(TextView) view.findViewById(R.id.BrE);
        AmEmp3=(ImageButton) view.findViewById(R.id.AmEmp3);
        AmE=(TextView) view.findViewById(R.id.AmE);
        defs=(TextView) view.findViewById(R.id.defs);
        sams=(TextView) view.findViewById(R.id.sams);
        searchWords_fatherLayout = (LinearLayout) view.findViewById(R.id.searchWords_fatherLayout);
        addToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordsAction wordsAction=WordsAction.getInstance(getActivity());
                boolean saveStatus=wordsAction.saveWords(words);
                boolean addStatus=wordsAction.addWordsToBook(words.getWord(),getActivity());
                Log.i("save status", "save status: "+saveStatus);
                if(saveStatus&&addStatus){
                    Toast.makeText(getActivity(),"Word added success！",Toast.LENGTH_SHORT).show();
                    changeAddBtnStyle();
                }else {
                    Toast.makeText(getActivity(),"Word added fail！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchWords_fatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击输入框外实现软键盘隐藏
                Log.i("tap", "onClick: 收键盘");
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        return view;
    }

    public void changeAddBtnStyle(){
        addToBook.setBackgroundColor(getResources().getColor(R.color.success));
        addToBook.setText("√  Add");
        addToBook.setClickable(false);
    }

    public void setContent(final Words words, final Boolean InSQL) {
        this.words=words;
        if(words.getWord()!=null){

            final Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    word.setText(words.getWord());
                    BrE.setText("英:["+words.getBrE()+"]");
                    BrEmp3Url=words.getBrEmp3();
                    AmEmp3Url=words.getAmEmp3();
                    AmE.setText("美:["+words.getAmE()+"]");
                    try {
                        JSONArray defsA=words.getDefsList();
                        JSONArray samsA=words.getSamsList();
                        String defsStr="";
                        String samsStr="";
                        for (int i=0;i<defsA.length();i++){
                            JSONObject o=defsA.getJSONObject(i);
                            defsStr+=o.getString("pos")+"  "+o.getString("def")+"\n";
                        }
                        for (int i=0;i<samsA.length();i++){
                            JSONObject o=samsA.getJSONObject(i);
                            samsStr+=o.getString("eng")+"\n"+o.getString("chn")+"\n\n";
                        }
                        defs.setText(defsStr);
                        sams.setText(samsStr);
                        if(InSQL){
                            changeAddBtnStyle();
                        }else{
                            addToBook.setBackgroundColor(getResources().getColor(R.color.mainColor1));
                            addToBook.setText("+   Add");
                            addToBook.setClickable(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cover.setVisibility(View.INVISIBLE);
                }
            };
            Thread thread=new Thread(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("activity", "run: "+getMyActivity());
                    getMyActivity().runOnUiThread(runnable);
                }
            };
            thread.start();
        }
    }
}

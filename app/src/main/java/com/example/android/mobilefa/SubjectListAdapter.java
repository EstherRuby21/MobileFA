package com.example.android.mobilefa;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//For Filtering the Inputs in edit text..............
class InputFilterMinMax implements InputFilter {

    private int min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MyViewHolder> {

    List<Subjects> subjectList;
    static HashMap<String,Integer> hm = new HashMap<>();
    public SubjectListAdapter(Context applicationContext, int adapter_view_layout_subject_mark, ArrayList<Subjects> subjectsArrayList) {
        this.subjectList = subjectsArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectName;
        public EditText tvEdit;

        public MyViewHolder(View view) {
            super(view);
            subjectName = view.findViewById(R.id.text_view_subject_name_adapter);
            tvEdit = view.findViewById(R.id.edittext_mark_adapter);
        }
    }

    public SubjectListAdapter(List<Subjects> subjectList) {
        this.subjectList = subjectList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_view_layout_subject_mark, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Subjects sub = subjectList.get(position);
        holder.subjectName.setText(sub.getSubject());

        holder.tvEdit.setFilters(new InputFilter[]{new InputFilterMinMax(0, 50)});
        holder.tvEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String marks = holder.tvEdit.getText().toString();

                if (s.length() > 0) {
                    //Constants.subjectandmark.put(getItem(position).getSubject(),Integer.parseInt(s.toString()));
                    // Constants.subjectandmark.put(holder.subjectName.toString(), Integer.parseInt(s.toString()));

                    try {
                        hm.put(holder.subjectName.getText().toString(), Integer.parseInt(marks));
//                        Log.d("SuB&marks",hm.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Adapter Exception",e.toString());
                    }

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //subjectList.get(position).subject = s.toString();
            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    public static JSONObject getSubject(){
        JSONObject jsonObject =new JSONObject(hm);
        Log.d("HmToJson",jsonObject.toString());
        return jsonObject;
    }

}

    /*private  Context mContext;

    int mResource;
    public SubjectListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Subjects> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sub = getItem(position).getSubject();
        Subjects subject = new Subjects(sub);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView tvSubj = convertView.findViewById(R.id.text_view_subject_name_adapter);
        EditText tvEdit= convertView.findViewById(R.id.edittext_mark_adapter);

        tvEdit.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 50)});
        tvEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    Constants.subjectandmark.put(getItem(position).getSubject(),Integer.parseInt(s.toString()));
                    Toast.makeText(mContext, String.valueOf(position)+";;;", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvSubj.setText(sub);
        return  convertView;
    }*/


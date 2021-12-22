package kr.ac.mokwon.gongcafe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/***********************************************************************************************
 mtp1
 ItemAdapter		=> ToDoAdapter			어댑터이름
 ItemViewHolder		=> ToDoViewHolder		뷰홀더이름
 onItemListener	=> onToDoClickListener 	인터페이스이름
 onItemClicked 		=> onToDoClicked 		인터페이스 클릭메서드이름
 row_item		=> list_item 			아이템레이아웃이름
 ItemModel		=> Person 				모델이름

 **********************************************************************************************/

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CustomViewHolder> /*implements Filterable*/ {


    private ArrayList<CafeDTO> arrayList;
    private Context context;
    private Intent intent;

    public ItemAdapter(ArrayList<CafeDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    private onItemListener mListener;

    public void setOnClickListener(onItemListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ItemAdapter.CustomViewHolder holder = new ItemAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.CustomViewHolder holder, int position) {
        CafeDTO currentItem = arrayList.get(position);
        Glide.with(holder.itemView).load(currentItem.getImageUrl()).into(holder.search_image_view);
        holder.search_text_view1.setText(currentItem.getCafeName());
        holder.search_text_view2.setText(currentItem.getInfo());
        if (mListener != null) {
            final int pos = position;
            //final ItemModel item = mItems.get(viewHolder.getAdapterPosition());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(v.getContext(), SearchActivity_2.class);
                    intent.putExtra("images", arrayList.get(pos).getImageUrl());
                    intent.putExtra("title", arrayList.get(pos).getCafeName());
                    intent.putExtra("description", arrayList.get(pos).getInfo());
                    v.getContext().startActivity(intent);

                    //mListener.onItemClicked(item);
                }
            });
            //버튼등에도 동일하게 지정할 수 있음 holder.버튼이름.setOnClickListener..형식으로
        }
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public interface onItemListener {
        void onItemClicked(int position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView search_text_view1;
        TextView search_text_view2;
        ImageView search_image_view;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.search_text_view1 = itemView.findViewById(R.id.search_text_view1);
            this.search_text_view2 = itemView.findViewById(R.id.search_text_view2);
            this.search_image_view = itemView.findViewById(R.id.search_image_view);

        }

    }
}

//        void onItemClicked(int position);
//        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
//        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
//    }
    //public interface onItemListener {
    //    void onItemClicked(int position);
        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
    //}
//    private List<ItemModel> mDataList;
//    private List<ItemModel> mDataListAll;
//    private Context context;
//    private Intent intent;
//
//    //constructor
//    public ItemAdapter(List<ItemModel> items) {
//        mDataList = items;
//        mDataListAll = new ArrayList<>(items);
//    }
//
//    //interface - 클릭인터페이스
//    private onItemListener mListener;
//    public void setOnClickListener(onItemListener listener){
//        mListener = listener;
//    }
//
//    //data set changed
//    public void dataSetChanged(List<ItemModel> exampleList) {
//        mDataList = exampleList;
//        notifyDataSetChanged();
//    }
//
//    //1.onCreateViewHolder -------------------------------------------------------
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,
//                parent, false);
//        return new ItemViewHolder(v);
//    }
//
//    //2.onBindViewHolder  -------------------------------------------------------
//    @Override
//    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
//        ItemModel currentItem = mDataList.get(position);
//        // TODO : 데이터를 뷰홀더에 표시하시오
//        holder.imageView.setImageResource(currentItem.getImageResource());
//        holder.textView1.setText(currentItem.getText1());
//        holder.textView2.setText(currentItem.getText2());
//
//        // TODO : 리스너를 정의하시오.
//        if (mListener != null){
//            final int pos = position;
//            //final ItemModel item = mItems.get(viewHolder.getAdapterPosition());
//            holder.itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    intent = new Intent(v.getContext(), SearchActivity_2.class);
//                    intent.putExtra("images", mDataList.get(pos).getImageResource());
//                    intent.putExtra("title",mDataList.get(pos).getText1());
//                    intent.putExtra("description", mDataList.get(pos).getText2());
//                    v.getContext().startActivity(intent);
//
//                    //mListener.onItemClicked(item);
//                }
//            });
//            //버튼등에도 동일하게 지정할 수 있음 holder.버튼이름.setOnClickListener..형식으로
//        }
//    }
//
//    //3.getItemCount  -------------------------------------------------------
//    @Override
//    public int getItemCount() {
//        return mDataList.size();
//    }
//
//
//    // 데이터 필터 검색 Filterable implement ---------------------------------
//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//
//    private Filter exampleFilter = new Filter() {
//        //Automatic on background thread
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<ItemModel> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(mDataListAll);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (ItemModel item : mDataListAll) {
//                    //TODO filter 대상 setting
//                    if (item.getText1().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        //Automatic on UI thread
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mDataList.clear();
//            mDataList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };
//
//    // 뷰홀더 클래스  ---------------------------------
//    class ItemViewHolder extends RecyclerView.ViewHolder {
//        // TODO : 뷰홀더 완성하시오
//        ImageView imageView;
//        TextView textView1;
//        TextView textView2;
//
//        ItemViewHolder(View itemView) {
//            super(itemView);
//            // TODO : 뷰홀더 완성하시오
//            imageView = itemView.findViewById(R.id.search_image_view);
//            textView1 = itemView.findViewById(R.id.search_text_view1);
//            textView2 = itemView.findViewById(R.id.search_text_view2);
//        }
//    }
//
//    //onclick listener interface
//    //1. interface onItemListener 선언
//    //2. 내부에서 mListener 선언하고
//    // 외부에서 접근가능하도록 setOnClickListener작성
//    //3.onBindViewHolder에서 처리
//    public interface onItemListener {
//        void onItemClicked(int position);
//        //void onItemClicked(ItemModel model); 모델값을 넘길수 있음
//        //다른버튼도 정의할 수 있음 onShareButtonClicked(int position);
//    }
//}
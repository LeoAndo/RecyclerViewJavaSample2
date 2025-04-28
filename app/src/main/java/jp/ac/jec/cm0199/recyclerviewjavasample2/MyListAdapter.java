package jp.ac.jec.cm0199.recyclerviewjavasample2;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

final class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.VH> {
    @NonNull
    private final List<Item> items;
    @Nullable
    private OnItemClickListener listener;

    MyListAdapter(@NonNull final List<Item> items) {
        this.items = items;
    }

    interface OnItemClickListener {
        void onItemClick(@NonNull Item item, int position);
    }

    void setOnItemClickListener(@Nullable final OnItemClickListener l) {
        this.listener = l;
    }

    // 1行のViewを生成する
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var context = parent.getContext();
        var itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new VH(itemView);
    }

    // 1行のViewにデータをバインドする
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        var item = items.get(position);
        holder.bindTo(item);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * リストアイテムを更新する
     *
     * @param position 更新するリストアイテムの位置 0から始まるインデックス
     */
    void updateItem(final int position) {
        items.set(position, new Item(position, "ロボット" + SystemClock.elapsedRealtime() + "号")); // テストデータ
        notifyItemChanged(position);// 変更のあった特定の位置のみを効率的に更新する
        // notifyDataSetChanged(); // これだとリスト全体の更新が走る.
    }

    /**
     * リストアイテムを追加する
     *
     * @param position 追加するリストアイテムの位置 0から始まるインデックス
     */
    void insertItem(final int position) {
        var newItem = new Item(position, "ロボット" + SystemClock.elapsedRealtime() + "号"); // テストデータ
        items.add(position, newItem);
        notifyItemInserted(position);// 変更のあった特定の位置のみを効率的に更新する
    }

    /**
     * リストアイテムを削除する
     *
     * @param position 削除するリストアイテムの位置 0から始まるインデックス
     */
    void removeItem(final int position) {
        items.remove(position);
        notifyItemRemoved(position); // 変更のあった特定の位置のみを効率的に更新する
    }

    class VH extends RecyclerView.ViewHolder {
        private final View itemView;

        private VH(@NonNull final View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        private void bindTo(@NonNull final Item item) {
            var title = (TextView) itemView.findViewById(R.id.title);
            title.setText(item.title());
            itemView.setOnClickListener(v -> {
                var position = getAdapterPosition();
                Log.d("VH", "onClick position-> " + position);
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(items.get(position), position);
                }
            });
        }
    }
}

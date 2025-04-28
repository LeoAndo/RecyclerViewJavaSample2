package jp.ac.jec.cm0199.recyclerviewjavasample2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初期データを設定する (テストデータ)
        var items = new ArrayList<Item>();
        for (var i = 0; i < 10; i++) {
            items.add(new Item(i, "ロボット" + i + "号"));
        }

        // RecyclerViewの初期化を行う
        var recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        var adapter = new MyListAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 縦方向のリストとして表示する

        // リストアイテムをクリックしたときの処理を行う
        adapter.setOnItemClickListener((item, position) -> {
            Snackbar.make(getWindow().getDecorView(), item.toString(), Snackbar.LENGTH_LONG).show();
            // adapter.insertItem(position);// アイテムを挿入する (クリックしたリストアイテムの上に追加されるイメージ)
            adapter.removeItem(position); // アイテムを削除する
            // adapter.updateItem(position); // アイテムを更新する
        });

        // 右下のボタンをクリックしたときの処理を行う
        findViewById(R.id.update).setOnClickListener(v -> {
            var itemCount = adapter.getItemCount(); // リストのアイテム数を取得する
            Log.d("MainActivity", "itemCount: " + itemCount);
            for (var i = 0; i < itemCount; i++) {
                if (i % 2 == 0) {
                    // 一部のリスト要素(アイテム)を更新する
                    adapter.updateItem(i);
                }
            }
        });
    }
}
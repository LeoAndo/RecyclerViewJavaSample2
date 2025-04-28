package jp.ac.jec.cm0199.recyclerviewjavasample2;

import androidx.annotation.NonNull;

/**
 * リストに表示するアイテム
 *
 * @param internalId ID (他のアイテムと重複しないようにする)
 * @param title      タイトル
 */
record Item(int internalId, @NonNull String title) {
}
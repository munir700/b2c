package co.yap.widgets.searchwidget;

public interface SearchingListener {
    void onCancel();

    default void onTypingSearch(String search) {
    }

    default void onSearchKeyPressed(String search) {
    }
}

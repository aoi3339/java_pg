// Itemsという名前のクラス（プログラムをまとめる入れ物）を作ります。
public class Items {
    // mainメソッド（プログラムが最初に動き始める場所）を作ります。
    public static void main(String[] args) {
        // todosという配列（複数のデータを順番に入れる箱）に、3件のTodoを入れます。
        String[] todos = { "牛乳を買う", "", "パンを買う", "掃除をする" };
        // doneという配列に、各Todoが済んだかどうかを順番どおりに入れます。
        boolean[] done = { true, false, false, false };

        // iを0から1ずつ増やし、todosの件数より小さい間、処理を繰り返します。
        for (int i = 0; i < todos.length; i++) {
            // i番目のTodoが空文字でない場合だけ、<li>で囲んで表示します。
            if (!todos[i].isEmpty()) {
                // i番目のTodoが済んでいる場合は、[済]の印を付けて表示します。
                if (done[i]) {
                    System.out.println("<li>[済] " + todos[i] + "</li>");
                    // i番目のTodoがまだ済んでいない場合は、印を付けずに表示します。
                } else {
                    System.out.println("<li>" + todos[i] + "</li>");
                }
            }
            // for文（処理を繰り返す書き方）の範囲を閉じます。
        }
        // mainメソッドの範囲を閉じます。
    }
    // Itemsクラスの範囲を閉じます。
}

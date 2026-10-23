
// List（あとから件数を増減できる入れ物）を使えるようにします。
import java.util.List;
// ArrayList（Listの実物）を使えるようにします。
import java.util.ArrayList;

// Todoクラス（Todo 1件の形を決める設計図）を作ります。
class Todo {
    // titleフィールド（Todoが持つ値）にタイトルを保存します。
    String title;
    // doneフィールド（Todoが持つ値）に済んだかどうかを保存します。
    boolean done;

    // コンストラクタ（newでTodoを作るときに動く処理）で値を受け取ります。
    Todo(String title, boolean done) {
        // 受け取ったタイトルを、このTodoのtitleに入れます。
        this.title = title;
        // 受け取った済み状態を、このTodoのdoneに入れます。
        this.done = done;
    }

    // toItemメソッド（Todoを表示用の1行に変える処理）を作ります。
    String toItem() {
        // doneがtrueなら、[済]の印を付けた文字列を返します。
        if (done) {
            // return（結果を呼び出し元へ渡す命令）で済んだTodoの1行を返します。
            return "<li>[済] " + title + "</li>";
        }
        // まだ済んでいないTodoは、印を付けない1行を返します。
        return "<li>" + title + "</li>";
    }
}

// Mainクラス（プログラムを動かすための入れ物）を作ります。
public class Main {
    // mainメソッド（プログラムが最初に動き始める場所）を作ります。
    public static void main(String[] args) {
        // Todoだけを入れられるListを用意します。
        List<Todo> todos = new ArrayList<>();
        // 「牛乳を買う」という未完了のTodoを作り、Listに追加します。
        todos.add(new Todo("牛乳を買う", false));
        // 「ゴミを出す」という完了済みのTodoを作り、Listに追加します。
        todos.add(new Todo("ゴミを出す", true));

        // for文（繰り返す仕組み）でListからTodoを1件ずつ取り出します。
        for (Todo todo : todos) {
            // Todoを表示用の1行に変えて、ターミナルへ出力します。
            System.out.println(todo.toItem());
        }
    }
}

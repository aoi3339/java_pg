import com.sun.net.httpserver.HttpServer; // Webサーバーの機能を使えるようにします。
import java.net.InetSocketAddress; // 待ち受けるポート番号を指定できるようにします。
import java.util.ArrayList; // 追加：Todoを入れるリストを作れるようにします。
import java.util.List; // 追加：Todoを入れるリストの型（データの種類）を使えるようにします。

public class App { // Appという名前のクラス（プログラムのまとまり）を定義します。
    public static void main(String[] args) throws Exception { // プログラムが最初に実行する場所を定義します。
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0); // 8080番ポートで待ち受けるサーバーを用意します。
        server.createContext("/", exchange -> { // 「/」へのアクセスを処理します。
            String path = exchange.getRequestURI().getPath(); // アクセスされたパスを取り出します。
            String message;
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8"); // 移動：通常は文字をUTF-8のプレーンテキスト（飾りのない文字）として返します。
            if (path.equals("/hello")) { // パスが「/hello」と同じか比べます。
                message = "こんにちは！";
            } else if (path.equals("/todos")) { // 追加：パスが「/todos」と同じか比べます。
                List<String> todos = new ArrayList<>(); // 追加：Todoの文字列を入れるリストを作ります。
                todos.add("牛乳を買う"); // 追加：1件目のTodoを入れます。
                todos.add("卵を買う"); // 追加：2件目のTodoを入れます。
                todos.add("パンを買う"); // 追加：3件目のTodoを入れます。
                todos.add("自分の今日のTodo"); // 追加：4件目のTodoを入れます。
                String html = "<ul>"; // 追加：HTMLの箇条書きを始めます。
                for (String todo : todos) { // 追加：Todoを1件ずつ取り出します。
                    html += "<li>" + todo + "</li>"; // 追加：TodoをHTMLの項目として足します。
                } // 追加：繰り返しを終えます。
                html += "</ul>"; // 追加：HTMLの箇条書きを閉じます。
                message = html; // 追加：組み立てたHTMLを返す内容にします。
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8"); // 追加：/todosだけHTMLをUTF-8として返します。
            } else if (path.equals("/bye")) { // パスが「/bye」と同じか比べます。
                message = "さようなら！";
            } else if (path.equals("/menu")) { // パスが「/menu」と同じか比べます。
                message = "今日のランチはパスタ";
            } else {
                message = "ページが見つかりません";
            }
            byte[] body = message.getBytes("UTF-8"); // 文字を送信用のバイト列（データの並び）に変えます。
            exchange.sendResponseHeaders(200, body.length); // 成功を表す200とデータの長さを返します。
            exchange.getResponseBody().write(body); // 本文のデータをブラウザへ送ります。
            exchange.getResponseBody().close(); // 送信を終えて本文を閉じます。
        }); // 「/」への処理の登録を終えます。
        server.start(); // サーバーの待ち受けを始めます。
        System.out.println("サーバー起動: http://localhost:8080 （止めるときは Ctrl+C）"); // 起動したことと止め方を表示します。
    } // mainの処理を終えます。
} // Appクラスの定義を終えます。

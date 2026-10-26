import com.sun.net.httpserver.HttpServer; // Webサーバーの機能を使えるようにします。
import java.net.InetSocketAddress; // 待ち受けるポート番号を指定できるようにします。
import java.net.URLDecoder; // 追加：送られた日本語を元に戻せるようにします。
import java.nio.charset.StandardCharsets; // 追加：UTF-8という文字コード（文字の読み取り方式）を使えるようにします。
import java.util.ArrayList; // 追加：Todoを入れるリストを作れるようにします。
import java.util.List; // 追加：Todoを入れるリストの型（データの種類）を使えるようにします。

public class App { // Appという名前のクラス（プログラムのまとまり）を定義します。
    public static void main(String[] args) throws Exception { // プログラムが最初に実行する場所を定義します。
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0); // 8080番ポートで待ち受けるサーバーを用意します。
        List<String> todos = new ArrayList<>(); // 移動：Todoをためる空のリストを一度だけ作ります。
        server.createContext("/", exchange -> { // 「/」へのアクセスを処理します。
            String path = exchange.getRequestURI().getPath(); // アクセスされたパスを取り出します。
            String method = exchange.getRequestMethod(); // 追加：GETかPOSTかという送信方法を取り出します。
            String message;
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8"); // 移動：通常は文字をUTF-8のプレーンテキスト（飾りのない文字）として返します。
            if (path.equals("/add") && method.equals("POST")) { // 追加：/addへPOSTで送られたか確かめます。
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // 追加：送られた中身をUTF-8で受け取ります。
                String value = body.substring(5); // 追加：「todo=」の5文字を除きます。
                String todo = URLDecoder.decode(value, StandardCharsets.UTF_8); // 追加：日本語を元に戻します。
                if (!todo.isEmpty()) { // 追加：空の入力ではないか確かめます。
                    todos.add(todo); // 追加：入力されたTodoをリストに足します。
                } // 追加：空の入力を除く処理を終えます。
                exchange.getResponseHeaders().set("Location", "/"); // 追加：戻る場所を「/」に指定します。
                exchange.sendResponseHeaders(303, -1); // 追加：303で「/」へ移動するよう返します。
                exchange.close(); // 追加：今回の通信を閉じます。
                return; // 追加：この分岐の処理を終えます。
            } else if (path.equals("/")) { // 追加：「/」なら入力フォームとTodo一覧を表示します。
                String html = "<form method='post' action='/add'><input name='todo'><button>追加</button></form><ul>"; // 追加：入力フォームとHTMLの箇条書きを始めます。
                for (String todo : todos) { // 追加：Todoを1件ずつ取り出します。
                    html += "<li>" + todo + "</li>"; // 追加：TodoをHTMLの項目として足します。
                } // 追加：繰り返しを終えます。
                html += "</ul>"; // 追加：HTMLの箇条書きを閉じます。
                message = html; // 追加：組み立てたHTMLを返す内容にします。
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8"); // 移動：「/」だけHTMLをUTF-8として返します。
            } else if (path.equals("/hello")) { // パスが「/hello」と同じか比べます。
                message = "こんにちは！";
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

import com.sun.net.httpserver.HttpServer; // Webサーバーの機能を使えるようにします。
import java.net.InetSocketAddress; // 待ち受けるポート番号を指定できるようにします。

public class App { // Appという名前のクラス（プログラムのまとまり）を定義します。
    public static void main(String[] args) throws Exception { // プログラムが最初に実行する場所を定義します。
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0); // 8080番ポートで待ち受けるサーバーを用意します。
        server.createContext("/", exchange -> { // 「/」へのアクセスを処理します。
            String path = exchange.getRequestURI().getPath(); // アクセスされたパスを取り出します。
            String message;
            if (path.equals("/hello")) { // パスが「/hello」と同じか比べます。
                String query = exchange.getRequestURI().getRawQuery(); // URLの「?」より後ろの文字を取り出します。
                String name;
                if (query == null) { // クエリが無いときはゲストにします。
                    name = "ゲスト";
                } else {
                    name = query.substring(5); // 「name=」の後ろを名前として切り出します。
                }
                message = "こんにちは、" + name + "さん！"; // 名前をつないで応答の文字を作ります。
            } else if (path.equals("/bye")) { // パスが「/bye」と同じか比べます。
                message = "さようなら！";
            } else if (path.equals("/menu")) { // パスが「/menu」と同じか比べます。
                message = "今日のランチはパスタ";
            } else {
                message = "ページが見つかりません";
            }
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8"); // 文字をUTF-8として返すことを伝えます。
            byte[] body = message.getBytes("UTF-8"); // 文字を送信用のバイト列（データの並び）に変えます。
            exchange.sendResponseHeaders(200, body.length); // 成功を表す200とデータの長さを返します。
            exchange.getResponseBody().write(body); // 本文のデータをブラウザへ送ります。
            exchange.getResponseBody().close(); // 送信を終えて本文を閉じます。
        }); // 「/」への処理の登録を終えます。
        server.start(); // サーバーの待ち受けを始めます。
        System.out.println("サーバー起動: http://localhost:8080 （止めるときは Ctrl+C）"); // 起動したことと止め方を表示します。
    } // mainの処理を終えます。
} // Appクラスの定義を終えます。

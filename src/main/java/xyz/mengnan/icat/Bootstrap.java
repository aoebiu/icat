package xyz.mengnan.icat;

import cn.hutool.core.net.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Bootstrap {

  public static void main(String[] args) throws IOException {
    int port = 18080;

    if (!NetUtil.isUsableLocalPort(port)) {
      System.out.println("Icat connector configured to listen on port" + port
              + "failed to start.The port may already be in use or the " +
              "connector may be misconfigured.");
      return;
    }
    ServerSocket ss = new ServerSocket(port);

    while (true) {
      Socket s = ss.accept();
      InputStream is = s.getInputStream();
      int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      is.read(buffer);
      String requestString = new String(buffer, "utf-8");
      System.out.println("浏览器的输入信息： \r\n" + requestString);

      OutputStream os = s.getOutputStream();
      String response_head = "HTTP/1.1 500 OK\r\n" + "Content-Type: " +
              "text/html\r\n\r\n";
      String responseString = "Hello Icat from xyz.mengnan";
      responseString = response_head + responseString;
      os.write(responseString.getBytes());
      os.flush();
      s.close();
    }
  }

}

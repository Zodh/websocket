package com.example.demo;

import java.util.List;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class TutorialHandler implements WebSocketHandler {


  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    List<String> auth = session.getHandshakeHeaders().get("pass");
    if (!auth.get(0).equals("123")) {
      session.sendMessage(new TextMessage("Código inválido!"));
      session.close(CloseStatus.NOT_ACCEPTABLE);
    } else {
      System.out.println("Conexão estabelecida com sucesso - ID: " + session.getId());
    }
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    String msg = (String) message.getPayload();
    System.out.println("Mensagem: " + msg);
    Thread.sleep(2000);
    session.sendMessage(new TextMessage("Mensagem recebida e processada com sucesso!"));
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    System.out.println("Ops, erro: " + exception.getMessage() + " - na sessão: " + session.getId());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws Exception {
    System.out.println("Conexão encerrada - ID: " + session.getId() + " - Status: " + closeStatus.getCode());
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

}

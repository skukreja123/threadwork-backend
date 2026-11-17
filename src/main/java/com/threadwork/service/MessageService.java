package com.threadwork.service;
import com.threadwork.dto.MessageDtos.*;
import com.threadwork.model.Message;
import com.threadwork.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messages;
  @Transactional public void create(MessageRequest r) {
    Message m = new Message(); m.setName(r.name().trim()); m.setEmail(r.email().trim()); m.setBody(r.body().trim()); messages.save(m);
  }
  @Transactional(readOnly = true) public List<MessageView> list() { return messages.findAllByOrderByCreatedAtDesc().stream().map(MessageView::from).toList(); }
}

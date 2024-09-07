package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }


    public Message createMessage(Message message){
        if(message.message_text.length() > 0 && message.message_text.length() <= 255){
            Message createdMessage = messageDAO.createMessage(message);

            if(createdMessage != null){
                return createdMessage;
            }

        }
        return new Message();
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int id){
        return messageDAO.getOneMessage(id);
    }

    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(String text, int id){

        if(text.length() > 0 && text.length() <= 255){
            return messageDAO.updateMessage(text, id);
        }

       return new Message();
        
    }

    public List<Message> getAllMessagesByUser(int id){
        return messageDAO.getMessagesByUserId(id);
    }
    
}

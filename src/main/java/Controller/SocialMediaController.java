package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;


    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    public void registerUserHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);

        Account accountCreated = accountService.register(account);

        if(accountCreated.username == null){
            context.status(400);
        }else {
            context.json(accountCreated).status(200);
        }
    }

    public void loginUserHandler(Context context){
        Account account = context.bodyAsClass(Account.class);

        Account accountVerified = accountService.login(account);

        System.out.println("Acount created: " + accountVerified);

        if(accountVerified.username == null){
            context.status(401);
        
        }else {
            context.json(accountVerified).status(200);
        }

    }

    public void createMessageHandler(Context context){
        Message message = context.bodyAsClass(Message.class);

        Message messageCreated = messageService.createMessage(message);

        if(messageCreated.message_text != null){
            context.json(messageCreated).status(200);
        }else {
            context.status(400);
        }
    }

    public void getAllMessagesHandler(Context context){
        
        List<Message> messages = messageService.getAllMessages();

        context.json(messages).status(200);
    }

    public void getMessageHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
    
        Message message = messageService.getMessage(id);

        if(message.message_text != null){
            context.json(message).status(200);
        }

        
    }

    public void deleteMessageHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));

        Message message = messageService.deleteMessage(id);
        System.out.println("Delete : " + message);

        if(message.message_text != null){
            context.json(message).status(200);
        }

        
    }

    public void updateMessageHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));

        String messageText = context.bodyAsClass(Message.class).message_text;

        Message updatedMessage = messageService.updateMessage(messageText, id);

        System.out.println("Errors: " + updatedMessage);

        if(updatedMessage.message_text == null){
            context.status(400);
        }else {
            context.json(updatedMessage).status(200);
        }
    }

    public void getAllMessagesByUserHandler(Context context){
        int id = Integer.parseInt(context.pathParam("account_id"));

        List<Message> messages = messageService.getAllMessagesByUser(id);

        context.json(messages).status(200);
    }


}
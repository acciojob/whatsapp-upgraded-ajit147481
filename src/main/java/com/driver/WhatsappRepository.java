package com.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WhatsappRepository {
    private HashMap<String,User> userHashMap;
    private HashMap<String,Group> groupHashMap;
    private HashMap<Group,List<User>> groupUserHashMap;
    private HashMap<Integer,Message> messageHashMap;
    private HashMap<Group,List<Message>> groupMessageHashMap;

    public WhatsappRepository(){

    }
    public WhatsappRepository(HashMap<String,User> userHashMap,HashMap<String,Group> groupHashMap,
                                HashMap<Group,List<User>> groupUserHashMap,HashMap<Integer,Message> messageHashMap,
                                    HashMap<Group,List<Message>> groupMessageHashMap){
            this.userHashMap=userHashMap;
            this.groupHashMap=groupHashMap;
            this.groupUserHashMap=groupUserHashMap;
            this.messageHashMap=messageHashMap;
            this.groupMessageHashMap=groupMessageHashMap;
    }
    public String createUser(String name, String mobile)throws Exception {
        try{
             User user=new User(name,mobile);
             if(userHashMap.containsKey(mobile)){
                throw new Exception("User already exists");
             }
            userHashMap.put(mobile,user);
            return "SUCCESS";
        }
        catch (Exception e){
            return null;
        }
    }

    public Group createGroup(List<User> users) {
        Group group=new Group();
        group.setNumberOfParticipants(users.size());
        if(users.size()==2){
            group.setName(users.get(1).getName());
        }else{
            group.setName("Group "+users.size());
        }

        groupHashMap.put(group.getName(),group);

        groupUserHashMap.put(group,users);

        List<Message> messageList=new ArrayList<>();
        groupMessageHashMap.put(group,messageList);

        return group;
    }

    public int createMessage(String content) {
        Message message=new Message();
        message.setId(messageHashMap.size());
        message.setContent(content);
        messageHashMap.put(message.getId(),message);
        return message.getId();
    }

    public int sendMessage(Message message, User sender, Group group)throws Exception {
        if(!groupHashMap.containsKey(group.getName())){
            throw new Exception("Group does not exist");
        }
        List<User> userList=groupUserHashMap.get(group);

        boolean isUser=false;
        for(User user:userList){
            if(sender==user){
                isUser=true;
                break;
            }
        }
        if(!isUser){
            throw new Exception("You are not allowed to send message");
        }
        List<Message> messageList=groupMessageHashMap.get(group);
        messageList.add(message);

        groupMessageHashMap.put(group,messageList);
        return messageList.size()-1;
    }

    public String changeAdmin(User approver, User user, Group group)throws Exception {
        if(!groupHashMap.containsKey(group.getName())){
            throw new Exception("Group does not exist");
        }
        List<User> userList=groupUserHashMap.get(group);
        if(approver!=userList.get(0)){
            throw new Exception("Approver does not have rights");
        }
        boolean flag=false;
        for(User user1:userList){
            if(user1==user){
                flag=true;
                break;
            }
        }
        if(flag==false){
            throw new Exception("User is not a participant");
        }
        User user1=userList.get(0);
        userList.add(user1);
        userList.remove(0);
        return "SUCCESS";
    }

    public int removeUser(User user)throws Exception {
        boolean flag=false,isAdmin=false;
        Group group=new Group();
        for(Group group1:groupUserHashMap.keySet()){
            List<User> userList=groupUserHashMap.get(group1);
            for(User user1:userList){
                if(user1==user){
                    flag=true;
                    if(userList.get(0)==user){
                        isAdmin=true;
                    }
                    group=group1;
                }
            }
        }
        if(!flag){
            throw new Exception("User not found");
        }
        if(isAdmin){
            throw new Exception("Cannot remove admin");
        }
        if(!isAdmin){
            List<User> userList=groupUserHashMap.get(group);
            userList.remove(user);

            groupUserHashMap.put(group,userList);
            userHashMap.remove(group);
        }
        return groupUserHashMap.get(group).size();
    }

    public void setGroupHashMap(HashMap<String, Group> groupHashMap) {
        this.groupHashMap = groupHashMap;
    }

    public void setGroupMessageHashMap(HashMap<Group, List<Message>> groupMessageHashMap) {
        this.groupMessageHashMap = groupMessageHashMap;
    }

    public void setGroupUserHashMap(HashMap<Group, List<User>> groupUserHashMap) {
        this.groupUserHashMap = groupUserHashMap;
    }

    public void setMessageHashMap(HashMap<Integer, Message> messageHashMap) {
        this.messageHashMap = messageHashMap;
    }

    public void setUserHashMap(HashMap<String, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    public HashMap<Group, List<User>> getGroupUserHashMap() {
        return groupUserHashMap;
    }

    public HashMap<String, User> getUserHashMap() {
        return userHashMap;
    }

    public HashMap<Group, List<Message>> getGroupMessageHashMap() {
        return groupMessageHashMap;
    }

    public HashMap<Integer, Message> getMessageHashMap() {
        return messageHashMap;
    }

    public HashMap<String, Group> getGroupHashMap() {
        return groupHashMap;
    }
}

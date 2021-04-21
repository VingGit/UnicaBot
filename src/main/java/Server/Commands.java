package Server;


import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Config.EditConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.paulvogel.multicraftapi.MulticraftAPI;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class Commands {
    private static String FTPserver = getItemFromServerConfig("FTPserver");
    private static int FTPport = Integer.parseInt(getItemFromServerConfig("FTPport"));
    private static FTPClient ftpClient = new FTPClient();
    private static String ApiUrl = getItemFromServerConfig("ApiUrl");
    private static String ApiUsername = getItemFromServerConfig("ApiUsername");
    private static String ApiKey = getItemFromServerConfig("ApiKey");
    private static MulticraftAPI api = new MulticraftAPI(ApiUrl, ApiUsername, ApiKey);
    private static HashMap<String,String> apituloste=new HashMap<>();


    public static String getItemFromServerConfig(String avain){
        return EditConfig.readServerConfig().get(avain);
    }
    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);

            }
        }
    }
    public static boolean isOnline() throws IOException {
        //my user id 94568 and server id 184337
        apituloste.put("id","184337");
        apituloste.put("player_list","true");
       //https://www.multicraft.org/site/docs/api#3


        JsonNode parent= new ObjectMapper().readTree(api.call("getServerStatus",apituloste).toString());
        String content = parent.path("status").asText();

        if(content.equals("online")){
            System.out.println("Multicraftin api kutsu palautui onnistuneesti ja discord bottisi on "+ content);
            return true;
        }
        return false;
    }
    public static void yhdista(String email, String pw) {

        try {
            isOnline();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try {
            ftpClient.connect(FTPserver, FTPport);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(email, pw);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Kirjautuminen ei onnistunut. Tarkista käyttäjätunnus ja salasana.");
                return;
            } else {
                System.out.println("LOGGED IN SERVER");
            }
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
    }
    public static void laheta() {



        try {

            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File firstLocalFile = new File("src\\main\\resources\\ektiedosto.txt");

            String firstRemoteFile = "src/main/resources/tissikukula.txt";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The file was uploaded successfully.");
            }


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }

    }




}
